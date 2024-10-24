package org.example.station3_productregistration.server;

public class ProductTrays {
    public ProductTrays(int product_id, int tray_id) {
        this.product_id = product_id;
        this.tray_id = tray_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getTray_id() {
        return tray_id;
    }

    public void setTray_id(int tray_id) {
        this.tray_id = tray_id;
    }

    private int product_id;
    private int tray_id;
}
