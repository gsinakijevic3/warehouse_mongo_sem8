package warehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "warehouse.repository")
public class MongoDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongoDbApplication.class, args);
	}
}
