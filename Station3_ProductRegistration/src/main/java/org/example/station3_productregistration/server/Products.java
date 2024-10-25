package org.example.station3_productregistration.server;

public class Products {
    public Products(String product_type, int product_id) {
        this.product_type = product_type;
        this.product_id = product_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    private int product_id;
    private String product_type;
}