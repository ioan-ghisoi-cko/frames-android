package com.checkout.android_sdk.Response;

import com.checkout.android_sdk.Models.CardModel;

/**
 * The response model object for the card tokenisation response
 */
public class CardTokenisationResponse {

    private String id;
    private String liveMode;
    private String created;
    private String used;
    private CardModel card;

    public String getId() {
        return this.id;
    }

    public String getLiveMode() {
        return this.liveMode;
    }

    public String getCreated() {
        return this.created;
    }

    public String getUsed() {
        return this.used;
    }

    public String getExpiryMonth() {
        return this.card.getExpiryMonth();
    }

    public String getExpiryYear() {
        return this.card.getExpiryYear();
    }

    public String getCardId(String id) {
        return this.card.getId();
    }

    public String getLast4() {
        return this.card.getLast4();
    }

    public String getBin() {
        return this.card.getBin();
    }

    public String getPaymentMethod() {
        return this.card.getPaymentMethod();
    }

    public String getName() {
        return this.card.getName();
    }

    public String getAddressLine1() {
        return this.card.billingDetails.getAddressLine1();
    }

    public String gettAddressLine2() {
        return this.card.billingDetails.getAddressLine2();
    }

    public String getPostcode() {
        return this.card.billingDetails.getPostcode();
    }

    public String getCountry() {
        return this.card.billingDetails.getCountry();
    }

    public String getCity() {
        return this.card.billingDetails.getCity();
    }

    public String getState() {
        return this.card.billingDetails.getState();
    }

    public String getCountryCode() {
        return this.card.billingDetails.phone.getCountryCode();
    }

    public String getPhoneNumber() {
        return this.card.billingDetails.phone.getNumber();
    }

    public String getPhoneCountryCode() {
        return this.card.billingDetails.phone.getCountryCode();
    }
}
