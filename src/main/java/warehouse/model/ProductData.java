package warehouse.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "product")
public class ProductData {

    @Id
    private String id;
    private String productName;
    private int productQuantity;

    public ProductData() {
    }

    public ProductData(String productId, int productQuantity, String productName) {
        this.id = productId;
        this.productQuantity = productQuantity;
        this.productName = productName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    @Override
    public String toString() {
        return "ProductData{" +
                "productId='" + id + '\'' +
                ", productName='" + productName + '\'' +
                ", productQuantity=" + productQuantity +
                '}';
    }
}
