package com.checkout.android_sdk.Models;

/**
 * Http request card details object model
 */
public class CardModel {

    public String expiryMonth;
    public String expiryYear;
    public BillingModel billingDetails;
    public String id;
    public String last4;
    public String bin;
    public String paymentMethod;
    public String name;

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
