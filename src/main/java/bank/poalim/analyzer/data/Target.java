package bank.poalim.analyzer.data;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class Target {
    public LocalDate date;
    public Integer price;
    public String target;
}
