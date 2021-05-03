package bank.poalim.analyzer.service;

import bank.poalim.analyzer.exception.ApplicationException;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface AnalyzerService {

    String getRecommendationToApproveYourFinancial() throws ApplicationException;
    String getYourOptionForGetTarget(String target, LocalDate dateToGetTarget) throws ApplicationException;
}
