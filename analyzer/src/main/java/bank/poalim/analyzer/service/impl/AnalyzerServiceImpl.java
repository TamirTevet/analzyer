package bank.poalim.analyzer.service.impl;

import bank.poalim.analyzer.exception.ApplicationException;
import bank.poalim.analyzer.model.Target;
import bank.poalim.analyzer.service.AnalyzerService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@RequiredArgsConstructor
public class AnalyzerServiceImpl implements AnalyzerService {

    @Override
    public String getRecommendationToApproveYourFinancial() throws ApplicationException {
        return null;
    }

    @Override
    public Target getYourOptionForGetTarget() throws ApplicationException {
        return null;
    }
}
