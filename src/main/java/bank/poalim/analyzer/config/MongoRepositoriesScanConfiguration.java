package bank.poalim.analyzer.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "bank.poalim.analyzer")
public class MongoRepositoriesScanConfiguration {

    @Bean("analyzerCollection")
    public String mongoCollectionName(@Value("{mongo.collection.name}") final String collectionName) {
        return collectionName;
    }
}
