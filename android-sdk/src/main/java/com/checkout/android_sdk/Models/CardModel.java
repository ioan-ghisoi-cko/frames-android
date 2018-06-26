package com.checkout.android_sdk.Models;

/**
 * Http request card details object model
 */
public class CardModel {

    private String expiryMonth;
    private String expiryYear;
    private BillingModel billingDetails;
    private String id;
    private String last4;
    private String bin;
    private String paymentMethod;
    private String name;

    public String getExpiryMonth() {
        return expiryMonth;
    }

    public String getExpiryYear() {
        return expiryYear;
    }

    public BillingModel getBillingDetails() {
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
