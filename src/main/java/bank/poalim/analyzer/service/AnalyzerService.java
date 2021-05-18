package bank.poalim.analyzer.service;

import bank.poalim.analyzer.data.Target;
import bank.poalim.analyzer.exception.ApplicationException;
import net.minidev.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

public interface AnalyzerService {

    JSONObject getRecommendationToApproveYourFinancial(HttpServletRequest request) throws ApplicationException, Exception;
    JSONObject getYourOptionForGetTarget(HttpServletRequest request, Target target) throws ApplicationException, Exception;
    JSONObject getYourTarget(HttpServletRequest request) throws ApplicationException, Exception;

}
