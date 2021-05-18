package bank.poalim.analyzer.repository;

import bank.poalim.analyzer.domain.TargetData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TargetMongoRepository extends MongoRepository<TargetData, String> {

//    @Query("{\"_id\" : ? 123}")
    String getByAccountId(String accountId);
}
