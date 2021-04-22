package bank.poalim.analyzer.service;

import bank.poalim.analyzer.exception.ApplicationException;
import bank.poalim.analyzer.model.Target;

public interface AnalyzerService {

    String getRecommendationToApproveYourFinancial() throws ApplicationException;
    Target getYourOptionForGetTarget() throws ApplicationException;
}
