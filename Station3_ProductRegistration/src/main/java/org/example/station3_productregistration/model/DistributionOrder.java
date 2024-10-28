package org.example.station3_productregistration.model;


import jakarta.persistence.*;


@Entity
@Table(name = "distribution_orders")
public class DistributionOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @Column(name = "customer_details", nullable = false)
    private String customerDetails;

    @Column(name = "shipping_date", nullable = false)
    private String shippingDate; // You might want to use java.time.LocalDate

    @Column(name = "recall_status", nullable = false)
    private boolean recallStatus;

    // Getters and Setters
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(String customerDetails) {
        this.customerDetails = customerDetails;
    }

    public String getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(String shippingDate) {
        this.shippingDate = shippingDate;
    }

    public boolean isRecallStatus() {
        return recallStatus;
    }

    public void setRecallStatus(boolean recallStatus) {
        this.recallStatus = recallStatus;
    }
}

