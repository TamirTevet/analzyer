package bank.poalim.analyzer.service.impl;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import bank.poalim.analyzer.data.Target;
import bank.poalim.analyzer.domain.TargetData;
import bank.poalim.analyzer.exception.ApplicationException;
import bank.poalim.analyzer.repository.TargetMongoRepository;
import bank.poalim.analyzer.service.AnalyzerService;
import bank.poalim.analyzer.util.WebUtils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Map;

@Component
@Slf4j
@Data
@RequiredArgsConstructor
public class AnalyzerServiceImpl implements AnalyzerService {
    @Autowired
    private final TargetMongoRepository mongoTemplate;
    final String validateJWTuri = "https://apim-team2.azure-api.net/v1/validate";
    final String currentAccountBalanceUri = "https://apim-team2.azure-api.net/ca/ca/balances/bank/"; //{bank_id}/account/{account_id}
    final String currentAccountLoansUri = "https://apim-team2.azure-api.net/ca/ca/loans/bank/"; //{bank_id}/account/{account_id}
    final String invalidToken = "invalid token";
    final Integer maxLoanMonth = 36;
    final Float interestLoan = 3.0F;

    @Override
    public JSONObject getRecommendationToApproveYourFinancial(HttpServletRequest request) throws Exception {
        JSONObject jsonObject = new JSONObject();

        if (!validateToken(request)){
            return jsonObject.appendField("[ERROR]" , invalidToken);
        }
        Object accountData = getAccountDataFromJWT(request);


        return jsonObject.appendField("[ADVICE]", "We see you have a lot of loans in your account in rent of 9%, we suggest to take one loan for all in 5% rent and only to two years");
    }

    @Override
    public JSONObject getYourOptionForGetTarget(HttpServletRequest request, Target target) throws ApplicationException, Exception {
        JSONObject jsonObject = new JSONObject();

        if (!validateToken(request)){
            return jsonObject.appendField("[ERROR]" , invalidToken);
        }
        Object accountData = getAccountDataFromJWT(request);
        ////////////////////////////////////////////////////////
        //todo request ask shoval for example request
        //static
        int totalSavings = ThreadLocalRandom.current().nextInt(0, 70000);
        int futureMonthlySavings = ThreadLocalRandom.current().nextInt(0, 500);
        int accountBalance = ThreadLocalRandom.current().nextInt(0, 10000);
        int loanMonthlyPayment = ThreadLocalRandom.current().nextInt(0, 1500);

        //1.monthly savings check
        LocalDateTime now = LocalDateTime.now();
        int timeToSave = 0;
        int monthOfTargetDate = target.getDate().getYear();
        int monthCurrent = now.getYear();
        if (target.getDate().getYear() > now.getYear()) {
            if (monthOfTargetDate > monthCurrent) {
                timeToSave = 12 + (monthOfTargetDate - monthCurrent);
            }else {
                timeToSave = 12 - (monthCurrent - monthOfTargetDate);
            }
        } else {
            timeToSave = monthOfTargetDate - monthCurrent;
        }
        if (target.getPrice() <= timeToSave*futureMonthlySavings){
            jsonObject.appendField("[Future Monthly Savings]" , "YES");
        }
        //2. total account balance

        if (accountBalance >= 1.5 * target.getPrice()){
            jsonObject.appendField("[Account Balance]" , "YES");
        }
        //3. loans
        if ((target.getPrice() * interestLoan) / maxLoanMonth <= loanMonthlyPayment){
            jsonObject.appendField("[Loan Possibility]" , "YES");
        }
        //4. savings
        if(target.getPrice() <= totalSavings){
            jsonObject.appendField("[Pay with savings]" , "YES");

        }
        //end static
       if (jsonObject.isEmpty()){
           return jsonObject.appendField("[SORRY]", "we not find good option for you according to your financial situation");
       }
       //if exists update if not create
        String accountId = request.getHeader("accountId"); //todo ask shimon to send is as a header? or change to path or w/e
        Optional<TargetData> result = mongoTemplate.findById(accountId);
        if (result.isEmpty()){
            mongoTemplate.save(new TargetData(accountId, accountId,jsonObject.toString()));
        } else {
            TargetData targetData = result.get();
            String achievementOptions = targetData.getAchievementOptions();
            achievementOptions += jsonObject;
            targetData.setAchievementOptions(achievementOptions);
            mongoTemplate.save(targetData);
        }
       return jsonObject;
    }

    @Override
    public JSONObject getYourTarget(HttpServletRequest request) throws Exception {
        ResponseEntity<String> balance = getBalanceFromCurrentAccount("5430670", "Latlux");
        getLoansFromCurrentAccount("5430670", "Latlux");

        JSONObject jsonObject = new JSONObject();

        String accountId = request.getHeader("accountId"); //todo ask shimon to send is as a header? or change to path or w/e
        Optional<TargetData> result = mongoTemplate.findById(accountId);
        if (result.isEmpty()){
            return jsonObject.appendField("[EMPTY]", "There are no target yet.");
        }
        return jsonObject.appendField("[TARGET]",  result.get().getAchievementOptions());
    }

    private boolean validateToken(HttpServletRequest request) throws Exception {
        Map<String, String> headers = WebUtils.getHeadersInfo(request);
        HttpResponse response = WebUtils.get(validateJWTuri , headers);
        if (response.statusCode() != 200) {
            return false;
        }
        return true;
    }

    private Object getAccountDataFromJWT(HttpServletRequest request){
        String token = request.getHeader("x-jwt-assertion");
        Base64.Decoder decoder = Base64.getDecoder();
        String[] chunks = token.split("\\.");
        String accountDataObject = new String(decoder.decode(chunks[1]));
        return accountDataObject;
    }

    private ResponseEntity<String> getBalanceFromCurrentAccount(String accountId, String bankId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = currentAccountBalanceUri + bankId + "/account/" + accountId;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Host", "apim-team2.azure-api.net");
        headers.add("Ocp-Apim-Subscription-Key", "f4c97a7149054869bced099b807d826f");
        headers.add("Ocp-Apim-Trace", "true");
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

    private ResponseEntity<String> getLoansFromCurrentAccount(String accountId, String bankId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = currentAccountLoansUri + bankId + "/account/" + accountId;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Host", "apim-team2.azure-api.net");
        headers.add("Ocp-Apim-Subscription-Key", "f4c97a7149054869bced099b807d826f");
        headers.add("Ocp-Apim-Trace", "true");
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

}
