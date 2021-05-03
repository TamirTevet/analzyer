package bank.poalim.analyzer.service;

import bank.poalim.analyzer.exception.ApplicationException;

import java.time.LocalDateTime;

public interface AnalyzerService {

    String getRecommendationToApproveYourFinancial() throws ApplicationException;
    String getYourOptionForGetTarget(String target, LocalDateTime dateToGetTarget) throws ApplicationException;
}
