package com.example.android_sdk.Utils;

public class TokenSuccessResponse {

    private String id;
    private String liveMode;
    private String created;
    private String used;
    private CardResponse card = new CardResponse();


    public TokenSuccessResponse setId(String id) {
        this.id = id;
        return this;
    }

    public TokenSuccessResponse setLiveMode(String liveMode) {
        this.liveMode = liveMode;
        return this;
    }

    public TokenSuccessResponse setCreated(String created) {
        this.created = created;
        return this;
    }

    public TokenSuccessResponse setUsed(String used) {
        this.used = used;
        return this;
    }

    public TokenSuccessResponse setExpiryMonth(String expiryMonth) {
        this.card.setExpiryMonth(expiryMonth);
        return this;
    }

    public TokenSuccessResponse setExpiryYear(String expiryYear) {
        this.card.setExpiryYear(expiryYear);
        return this;
    }

    public TokenSuccessResponse setCardId(String id) {
        this.card.setId(id);
        return this;
    }

    public TokenSuccessResponse setCardLast4(String last4) {
        this.card.setLast4(last4);
        return this;
    }

    public TokenSuccessResponse setCardBin(String bin) {
        this.card.setBin(bin);
        return this;
    }

    public TokenSuccessResponse setCardPaymentMethod(String paymentMethod) {
        this.card.setPaymentMethod(paymentMethod);
        return this;
    }

    public TokenSuccessResponse setCardName(String name) {
        this.card.setName(name);
        return this;
    }

    public TokenSuccessResponse setAddressLine1(String addressLine1) {
        this.card.billingDetails.setAddressLine1(addressLine1);
        return this;
    }

    public TokenSuccessResponse setAddressLine2(String addressLine2) {
        this.card.billingDetails.setAddressLine2(addressLine2);
        return this;
    }

    public TokenSuccessResponse setPostoce(String postcode) {
        this.card.billingDetails.setPostcode(postcode);
        return this;
    }

    public TokenSuccessResponse setCountry(String country) {
        this.card.billingDetails.setCountry(country);
        return this;
    }

    public TokenSuccessResponse setCity(String city) {
        this.card.billingDetails.setCity(city);
        return this;
    }

    public TokenSuccessResponse setState(String state) {
        this.card.billingDetails.setState(state);
        return this;
    }

    public TokenSuccessResponse setPhoneCountryCode(String phoneCountryCode) {
        this.card.billingDetails.phone.setCountryCode(phoneCountryCode);
        return this;
    }

    public TokenSuccessResponse setPhoneNumber(String phoneNumber) {
        this.card.billingDetails.phone.setNumber(phoneNumber);
        return this;
    }

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

    public String getExpiryYear(String expiryYear) {
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

    public String getPostoce() {
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
}
