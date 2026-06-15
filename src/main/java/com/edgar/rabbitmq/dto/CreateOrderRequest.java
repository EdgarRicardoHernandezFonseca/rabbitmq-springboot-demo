package com.edgar.rabbitmq.dto;

public class CreateOrderRequest {

    private Long orderId;

    private String customerName;

    private Double totalAmount;

    public CreateOrderRequest() {
    }

    public CreateOrderRequest(
            Long orderId,
            String customerName,
            Double totalAmount) {

        this.orderId = orderId;
        this.customerName = customerName;
        this.totalAmount = totalAmount;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}