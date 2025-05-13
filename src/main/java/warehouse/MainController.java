package warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import warehouse.repository.*;
import warehouse.model.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/demo")
public class MainController {
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    WarehouseService warehouseService;

    @PostMapping(path="/warehouse")
    public void addWarehouse(@RequestParam String name, @RequestParam String city, @RequestParam int postalCode, @RequestParam String country) {
        WarehouseData warehouse = new WarehouseData();
        warehouse.setWarehouseName(name);
        warehouse.setWarehouseCity(city);
        warehouse.setWarehousePostalCode(postalCode);
        warehouse.setWarehouseCountry(country);
        warehouse.setTimestamp(Timestamp.from(Instant.now()));
        warehouseRepository.save(warehouse);
    }

    @GetMapping(path="/warehouse")
    public @ResponseBody Iterable<WarehouseData> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    @PostMapping(path="/warehouse/add")
    public void addToWarehouse(@RequestParam String warehouseId, @RequestParam String productId, @RequestParam int productQuantity) {
        WarehouseData warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found with id: " + warehouseId));
        ProductData product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found with id: " + productId));
        ProductData existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found with id: " + productId));

        if (existingProduct != null) {
            existingProduct.setProductQuantity(existingProduct.getProductQuantity() + productQuantity);
            productRepository.save(existingProduct); // Speichert die Änderung in der DB
        } else {
            throw new IllegalArgumentException("Produkt mit ID " + productId + " nicht gefunden!");
        }

        product.setProductQuantity(productQuantity);
        warehouse.addProduct(product);
        warehouseRepository.save(warehouse);
    }

    @GetMapping(path="/warehouse/get/{warehouseId}")
    public @ResponseBody WarehouseData getWarehouse(@PathVariable String warehouseId) {
        return warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found with id: " + warehouseId));
    }

    @DeleteMapping(path="/warehouse/del/{warehouseId}")
    public void removeWarehouse(@PathVariable String warehouseId) {
        warehouseRepository.deleteById(warehouseId);
    }

    @GetMapping(path="/count")
    public @ResponseBody long countWarehouses() {
        return warehouseRepository.count();
    }

    @PostMapping(path="/product")
    public void addProduct(@RequestParam String productName, @RequestParam int quantity) {
        ProductData product = new ProductData();
        product.setId(UUID.randomUUID().toString());
        product.setProductName(productName);
        product.setProductQuantity(quantity);
        productRepository.save(product);
    }

    @GetMapping(path="/product")
    public @ResponseBody Iterable<ProductData> getProducts(){
        return productRepository.findAll();
    }

    @GetMapping("/product/get/{productId}")
    public @ResponseBody List<WarehouseData> getProductLocations(@PathVariable String productId) {
        return warehouseRepository.findAll()
                .stream()
                .filter(w -> w.getProducts().stream()
                        .anyMatch(p -> p.getId().equals(productId)))
                .collect(Collectors.toList());
    }

    @DeleteMapping(path="/product/{productId}")
    public void removeProduct(@PathVariable String productId) {
        List<WarehouseData> warehouses= warehouseRepository.findAll();
        for(WarehouseData warehouse: warehouses){
            List<ProductData> products = warehouse.getProducts();
            for(ProductData product: products){
                if(product.getId().equals(productId)){
                    products.remove(product);
                    warehouseRepository.save(warehouse);
                    break;
                }
            }
        }
        productRepository.deleteById(productId);
    }
    @GetMapping("/stock/{productId}")
    public int getStock(@PathVariable String productId) {


        List<WarehouseData> warehouses = warehouseRepository.findByProductsId(productId);
        int totalQuantity = 0;
        for (WarehouseData warehouse : warehouses) {
            for (ProductData product : warehouse.getProducts()) {
                if (product.getId().equals(productId)) {
                    totalQuantity += product.getProductQuantity();
                }
            }
        }
        return totalQuantity;
    }

    @DeleteMapping("/warehouse/all")
    public void removeAllWarehouses() {
        warehouseRepository.deleteAll();
    }

    @DeleteMapping("/product/all")
    public void removeAllProducts() {
        productRepository.deleteAll();
    }

    @PostMapping("/warehouse/random")
    public @ResponseBody WarehouseData randomWarehouse(){
        WarehouseData data = warehouseService.createWarehouse();
        warehouseRepository.save(data);
        return data;
    }

    @PostMapping("/product/random")
    public @ResponseBody ProductData randomProduct(){
        ProductData data = warehouseService.createProduct();
        productRepository.save(data);
        return data;
    }
}
