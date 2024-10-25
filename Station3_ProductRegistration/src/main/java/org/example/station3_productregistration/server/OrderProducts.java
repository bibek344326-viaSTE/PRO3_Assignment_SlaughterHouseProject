package org.example.station3_productregistration.server;

public class OrderProducts
{
    private int orderId;
    private int productId;

    public OrderProducts(int orderId, int productId) {
        this.orderId = orderId;
        this.productId = productId;
    }
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}