package bank.poalim.analyzer.controller;

import bank.poalim.analyzer.exception.ApplicationException;
import bank.poalim.analyzer.service.impl.AnalyzerServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/analyzer/v1")
@RequiredArgsConstructor
public class AnalyzerController {

    private final AnalyzerServiceImpl analyzerService;

    @GetMapping(value = "/advice")
    public ResponseEntity<String> getAdvice () throws ApplicationException {
        return new ResponseEntity<>(analyzerService.getRecommendationToApproveYourFinancial(), HttpStatus.OK);
    }

    @GetMapping(value = "/target/{target}/{dateToGetTarget}")
    public ResponseEntity<String> getOptionToGetTarget (@PathVariable String target, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateToGetTarget) throws ApplicationException {
        String getTarget = analyzerService.getYourOptionForGetTarget(target, dateToGetTarget);
        return new ResponseEntity<>(getTarget, HttpStatus.OK);
    }
}
