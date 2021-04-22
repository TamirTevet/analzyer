package bank.poalim.analyzer.model;

import lombok.Data;

import java.util.Date;

@Data
public class Target {

    private Thrift thrift;
    private Loan loan;

    @Data
    public class Thrift {
        private String target;
        private float monthlyDeposit;
        private Date startDate;
        private Date targetDate;
    }

    @Data
    public class Loan {
        private String target;
        private float monthlyPayment;
        private Date startDate;
        private Date targetDate;
    }
}


