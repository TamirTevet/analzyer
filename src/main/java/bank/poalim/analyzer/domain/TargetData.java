package bank.poalim.analyzer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@AllArgsConstructor
@Document(collection = "analyzer")
public class TargetData {

    @Id
    private String id;

    private String accountId;
    private String achievementOptions;
}
