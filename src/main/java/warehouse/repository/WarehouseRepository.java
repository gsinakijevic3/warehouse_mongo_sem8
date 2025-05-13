package warehouse.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import warehouse.model.WarehouseData;

import java.util.List;

public interface WarehouseRepository extends MongoRepository<WarehouseData, String> {
    List<WarehouseData> findByProductsId(String productId);

}
