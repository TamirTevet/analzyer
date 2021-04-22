package bank.poalim.analyzer.controller;

import bank.poalim.analyzer.exception.ApplicationException;
import bank.poalim.analyzer.model.Target;
import bank.poalim.analyzer.service.impl.AnalyzerServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("v1")
@RequiredArgsConstructor
public class AnalyzerController {

    private final AnalyzerServiceImpl analyzerService;

    @GetMapping(value = "/advice", consumes = {"application/json"})
    public ResponseEntity<String> getAdvice () throws ApplicationException {

        return null;
    }

    @GetMapping(value = "/target", consumes = {"application/json"})
    public ResponseEntity<Target> getOptionToGetTarget (@PathVariable String target, Date dateToGetTarget) throws ApplicationException {

        return null;
    }
}

