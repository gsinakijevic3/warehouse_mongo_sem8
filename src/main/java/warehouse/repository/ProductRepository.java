package warehouse.repository;
import warehouse.model.*;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<ProductData, String>{

}
