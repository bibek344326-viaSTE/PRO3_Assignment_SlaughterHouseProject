package org.example.station3_productregistration.server;

import java.util.Date;

public class DistributionOrders {
    private int orderId;
    private String customerDetails;
    private Date shippingDate;
    private boolean recallStatus;

    public DistributionOrders(int orderId, String customerDetails, Date shippingDate, boolean recallStatus) {
        this.orderId = orderId;
        this.customerDetails = customerDetails;
        this.shippingDate = shippingDate;
        this.recallStatus = recallStatus;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(String customerDetails) {
        this.customerDetails = customerDetails;
    }

    public Date getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    public boolean isRecallStatus() {
        return recallStatus;
    }

    public void setRecallStatus(boolean recallStatus) {
        this.recallStatus = recallStatus;
    }
}