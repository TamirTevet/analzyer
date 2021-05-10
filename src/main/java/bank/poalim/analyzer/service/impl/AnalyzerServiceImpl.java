package bank.poalim.analyzer.service.impl;

import bank.poalim.analyzer.service.AnalyzerService;
import bank.poalim.analyzer.util.WebUtils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Component
@Slf4j
@Data
@RequiredArgsConstructor
public class AnalyzerServiceImpl implements AnalyzerService {

    final String validateJWTuri = "https://apim-team2.azure-api.net/v1/validate";
    final String invalidToken = "invalid token";

    public boolean validateToken(HttpServletRequest request) throws Exception {
        Map<String, String> headers = WebUtils.getHeadersInfo(request);
        HttpResponse response = WebUtils.get(validateJWTuri , headers);
        if (response.statusCode() != 200) {
            return false;
        }
        return true;
    }

    public JSONObject getRecommendationToApproveYourFinancial(HttpServletRequest request) throws Exception {
        JSONObject jsonObject = new JSONObject();

        if (!validateToken(request)){
            return jsonObject.appendField("[ERROR]" , invalidToken);
        }
        return jsonObject.appendField("[ADVICE]", "We see you have a lot of loans in your account in rent of 9%, we suggest to take one loan for all in 5% rent and only to two years");
    }


    @Override
    public JSONObject getYourOptionForGetTarget(HttpServletRequest request, String target, LocalDate dateToGetTarget) throws Exception {
        JSONObject jsonObject = new JSONObject();

        if (!validateToken(request)){
            return jsonObject.appendField("[ERROR]" , invalidToken);
        }
        String optionToAchieveTarget = "Your option for get your target: \n";
        // need to take from api
        int currentAccountAmount = 20;
        //create switch case that set the price according to target
        int price = 100;
        //create switch case that set the interest according to target
        int annualInterest = 10;

        if (currentAccountAmount * 1.5 > price) {
            optionToAchieveTarget += "because you have more money in the account then you need we suggest to buy from what you have";
        } else {
            LocalDateTime now = LocalDateTime.now();
            int timeToSave = 0;
            int monthOfTargetDate = dateToGetTarget.getYear();
            int monthCurrent = now.getYear();
            if (dateToGetTarget.getYear() > now.getYear()) {
                if (monthOfTargetDate > monthCurrent) {
                    timeToSave = 12 + (monthOfTargetDate - monthCurrent);
                }else {
                    timeToSave = 12 - (monthCurrent - monthOfTargetDate);
                }
            } else {
               timeToSave = monthOfTargetDate - monthCurrent;
            }
            optionToAchieveTarget += "you need to save: " + price/timeToSave + "every month to get your target on time\n";
            int monthToRepayment = (price * annualInterest) / (price/timeToSave);
            optionToAchieveTarget += "if you take loan with annual interest of your target type you need to pay for: " + monthToRepayment + "month";
        }
        return jsonObject.appendField("[TARGET]", optionToAchieveTarget);
    }
}
