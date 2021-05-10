package bank.poalim.analyzer.service;

import bank.poalim.analyzer.exception.ApplicationException;
import net.minidev.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

public interface AnalyzerService {

    JSONObject getRecommendationToApproveYourFinancial(HttpServletRequest request) throws ApplicationException, Exception;
    JSONObject getYourOptionForGetTarget(HttpServletRequest request, String target, LocalDate dateToGetTarget) throws ApplicationException, Exception;
}
