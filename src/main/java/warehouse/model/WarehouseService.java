package warehouse.model;

import org.springframework.stereotype.Service;

@Service
public class WarehouseService {

    private final String[] cities = {"Vienna", "NYC", "Berlin", "Paris", "London", "Tokyo", "Moscow", "Beijing", "Sydney", "Rio de Janeiro"};
    private final String[] countries = {"Austria", "USA", "Germany", "France", "UK", "Japan", "Russia", "China", "Australia", "Brazil"};
    private final String[] products = {"Apple", "Banana", "Carrot", "Donut", "Egg", "Fries", "Grapes", "Honey", "Ice cream", "Jam"};

    public WarehouseData createWarehouse() {
        WarehouseData warehouse = new WarehouseData();
        warehouse.setId(java.util.UUID.randomUUID().toString());
        warehouse.setWarehouseName("Warehouse " + warehouse.getId().substring(0, 4));
        warehouse.setWarehouseCity(cities[(int) (Math.random() * cities.length)]);
        warehouse.setWarehousePostalCode((int) (Math.random() * 100000));
        warehouse.setWarehouseCountry(countries[(int) (Math.random() * countries.length)]);
        warehouse.setTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));
        return warehouse;
    }

    public ProductData createProduct(){
        ProductData product = new ProductData();
        product.setId(java.util.UUID.randomUUID().toString());
        product.setProductName(products[(int) (Math.random() * products.length)]);
        product.setProductQuantity(0);
        return product;
    }
}
