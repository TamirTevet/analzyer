package bank.poalim.analyzer.service.impl;

import bank.poalim.analyzer.exception.ApplicationException;
import bank.poalim.analyzer.service.AnalyzerService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Slf4j
@Data
@RequiredArgsConstructor
public class AnalyzerServiceImpl implements AnalyzerService {

    @Override
    public String getRecommendationToApproveYourFinancial() throws ApplicationException {
        return "We see you have a lot of loans in your account in rent of 9%, we suggest to take one loan for all in 5% rent and only to two years";
    }

    @Override
    public String getYourOptionForGetTarget(String target, LocalDate dateToGetTarget) throws ApplicationException {
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
        return optionToAchieveTarget;
    }
}
