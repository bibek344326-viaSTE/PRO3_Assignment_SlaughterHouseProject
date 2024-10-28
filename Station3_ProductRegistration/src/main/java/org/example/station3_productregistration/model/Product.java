package org.example.station3_productregistration.model;
import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
    private Integer productId;

    private String productType;

    // Getters and Setters
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
