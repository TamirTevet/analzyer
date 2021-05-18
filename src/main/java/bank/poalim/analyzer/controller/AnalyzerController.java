package bank.poalim.analyzer.controller;

import bank.poalim.analyzer.data.Target;
import bank.poalim.analyzer.exception.ApplicationException;
import bank.poalim.analyzer.service.impl.AnalyzerServiceImpl;
import bank.poalim.analyzer.util.WebUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/analyzer/v1")
@RequiredArgsConstructor
public class AnalyzerController {

    private final AnalyzerServiceImpl analyzerService;

    @GetMapping(value = "/advice")
    public ResponseEntity<Object> getAdvice (HttpServletRequest request) throws Exception {

        return new ResponseEntity<>(analyzerService.getRecommendationToApproveYourFinancial(request), HttpStatus.OK);
    }

    @GetMapping(value = "/all-target")
    public ResponseEntity<Object> getOptionToGetTarget (HttpServletRequest request) throws Exception {
        Object getTarget = analyzerService.getYourTarget(request);
        return new ResponseEntity<>(getTarget, HttpStatus.OK);
    }


    @PostMapping(value = "/target")
    public ResponseEntity<Object> getOptionToGetTarget (HttpServletRequest request , @RequestBody Target target) throws Exception {
        Object getTarget = analyzerService.getYourOptionForGetTarget(request, target);
        return new ResponseEntity<>(getTarget, HttpStatus.OK);
    }
}
