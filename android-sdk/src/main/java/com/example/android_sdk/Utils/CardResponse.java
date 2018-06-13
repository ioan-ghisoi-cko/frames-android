package com.example.android_sdk.Utils;

public class CardResponse {
    private String expiryMonth;
    private String expiryYear;
    public BillingDetails billingDetails = new BillingDetails();
    private String id;
    private String last4;
    private String bin;
    private String paymentMethod;
    private String name;

    public void setExpiryMonth(String expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public void setExpiryYear(String expiryYear) {
        this.expiryYear = expiryYear;
    }

    public void setBillingDetails(BillingDetails billingDetails) {
        this.billingDetails = billingDetails;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLast4(String last4) {
        this.last4 = last4;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpiryMonth() {
        return expiryMonth;
    }

    public String getExpiryYear() {
        return expiryYear;
    }

    public BillingDetails getBillingDetails() {
        return billingDetails;
    }

    public String getId() {
        return id;
    }

    public String getLast4() {
        return last4;
    }

    public String getBin() {
        return bin;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getName() {
        return name;
    }
}
