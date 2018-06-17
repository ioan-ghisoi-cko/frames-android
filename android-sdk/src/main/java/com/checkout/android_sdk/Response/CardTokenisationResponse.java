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
    private CardModel card = new CardModel();


    public CardTokenisationResponse setId(String id) {
        this.id = id;
        return this;
    }

    public CardTokenisationResponse setLiveMode(String liveMode) {
        this.liveMode = liveMode;
        return this;
    }

    public CardTokenisationResponse setCreated(String created) {
        this.created = created;
        return this;
    }

    public CardTokenisationResponse setUsed(String used) {
        this.used = used;
        return this;
    }

    public CardTokenisationResponse setExpiryMonth(String expiryMonth) {
        this.card.setExpiryMonth(expiryMonth);
        return this;
    }

    public CardTokenisationResponse setExpiryYear(String expiryYear) {
        this.card.setExpiryYear(expiryYear);
        return this;
    }

    public CardTokenisationResponse setCardId(String id) {
        this.card.setId(id);
        return this;
    }

    public CardTokenisationResponse setCardLast4(String last4) {
        this.card.setLast4(last4);
        return this;
    }

    public CardTokenisationResponse setCardBin(String bin) {
        this.card.setBin(bin);
        return this;
    }

    public CardTokenisationResponse setCardPaymentMethod(String paymentMethod) {
        this.card.setPaymentMethod(paymentMethod);
        return this;
    }

    public CardTokenisationResponse setCardName(String name) {
        this.card.setName(name);
        return this;
    }

    public CardTokenisationResponse setAddressLine1(String addressLine1) {
        this.card.billingDetails.setAddressLine1(addressLine1);
        return this;
    }

    public CardTokenisationResponse setAddressLine2(String addressLine2) {
        this.card.billingDetails.setAddressLine2(addressLine2);
        return this;
    }

    public CardTokenisationResponse setPostoce(String postcode) {
        this.card.billingDetails.setPostcode(postcode);
        return this;
    }

    public CardTokenisationResponse setCountry(String country) {
        this.card.billingDetails.setCountry(country);
        return this;
    }

    public CardTokenisationResponse setCity(String city) {
        this.card.billingDetails.setCity(city);
        return this;
    }

    public CardTokenisationResponse setState(String state) {
        this.card.billingDetails.setState(state);
        return this;
    }

    public CardTokenisationResponse setPhoneCountryCode(String phoneCountryCode) {
        this.card.billingDetails.phone.setCountryCode(phoneCountryCode);
        return this;
    }

    public CardTokenisationResponse setPhoneNumber(String phoneNumber) {
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
