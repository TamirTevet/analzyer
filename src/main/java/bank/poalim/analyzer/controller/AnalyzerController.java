package bank.poalim.analyzer.controller;

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

    @GetMapping(value = "/target/{target}/{dateToGetTarget}")
    public ResponseEntity<Object> getOptionToGetTarget (HttpServletRequest request ,@PathVariable String target, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateToGetTarget) throws Exception {
        Object getTarget = analyzerService.getYourOptionForGetTarget(request, target, dateToGetTarget);
        return new ResponseEntity<>(getTarget, HttpStatus.OK);
    }


//    @PostMapping(value = "/target/{target}/{dateToGetTarget}")
//    public ResponseEntity<Object> getOptionToGetTarget (HttpServletRequest request ,@PathVariable String target, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateToGetTarget) throws Exception {
//        Object getTarget = analyzerService.getYourOptionForGetTarget(request, target, dateToGetTarget);
//        return new ResponseEntity<>(getTarget, HttpStatus.OK);
//    }
}
