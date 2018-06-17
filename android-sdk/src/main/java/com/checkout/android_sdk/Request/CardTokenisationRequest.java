package com.checkout.android_sdk.Request;

import com.checkout.android_sdk.Models.BillingModel;
import com.checkout.android_sdk.Models.PhoneModel;

/**
 * The request model object for the card tokenisation request
 */
public class CardTokenisationRequest {

    private String number;
    private String name;
    private String expiryMonth;
    private String expiryYear;
    private String cvv;

    private BillingModel billingDetails = new BillingModel();

    public CardTokenisationRequest() {
        // Empty constructor
    }

    public CardTokenisationRequest(String number, String expiryMonth, String expiryYear,
                                   String cvv) {
        this.number = number;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.cvv = cvv;
    }

    public CardTokenisationRequest(String number, String expiryMonth, String expiryYear,
                                   String cvv, String name, String addressLine1,
                                   String addressLine2, String postcode, String country,
                                   String city, String state) {
        this.number = number;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.cvv = cvv;
        this.name = name;
        billingDetails.setAddressLine1(addressLine1);
        billingDetails.setAddressLine2(addressLine2);
        billingDetails.setPostcode(postcode);
        billingDetails.setCountry(country);
        billingDetails.setCity(city);
        billingDetails.setState(state);
    }

    public CardTokenisationRequest(String number, String expiryMonth, String expiryYear,
                                   String cvv, String name, String addressLine1,
                                   String addressLine2, String postcode, String country,
                                   String city, String state, PhoneModel phoneDetails,
                                   String countryCode, String phoneNumber) {
        this.number = number;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.cvv = cvv;
        this.name = name;
        billingDetails.setAddressLine1(addressLine1);
        billingDetails.setAddressLine2(addressLine2);
        billingDetails.setPostcode(postcode);
        billingDetails.setCountry(country);
        billingDetails.setCity(city);
        billingDetails.setState(state);
        PhoneModel phone = new PhoneModel();
        phone.setNumber(phoneNumber);
        phone.setCountryCode(countryCode);
        billingDetails.setPhone(phone);
    }

    public CardTokenisationRequest setCardNumber(String number) {
        this.number = number;
        return this;
    }

    public CardTokenisationRequest setExpiryMonth(String expiryMonth) {
        this.expiryMonth = expiryMonth;
        return this;
    }

    public CardTokenisationRequest setExpiryYear(String expiryYear) {
        this.expiryYear = expiryYear;
        return this;
    }

    public CardTokenisationRequest setCvv(String cvv) {
        this.cvv = cvv;
        return this;
    }

    public CardTokenisationRequest setName(String name) {
        this.name = name;
        return this;
    }

    public CardTokenisationRequest setAddressLine1(String addressLine1) {
        this.billingDetails.setAddressLine1(addressLine1);
        return this;
    }

    public CardTokenisationRequest setAddressLine2(String addressLine2) {
        this.billingDetails.setAddressLine2(addressLine2);
        return this;
    }

    public CardTokenisationRequest setPostcode(String postcode) {
        this.billingDetails.setPostcode(postcode);
        return this;
    }

    public CardTokenisationRequest setCountry(String country) {
        this.billingDetails.setCountry(country);
        return this;
    }

    public CardTokenisationRequest setCity(String city) {
        this.billingDetails.setCity(city);
        return this;
    }

    public CardTokenisationRequest setState(String state) {
        this.billingDetails.setState(state);
        return this;
    }

    public CardTokenisationRequest setPhoneNumber(String countryCode, String number) {
        PhoneModel phone = new PhoneModel();
        phone.setCountryCode(countryCode);
        phone.setNumber(number);
        this.billingDetails.setPhone(phone);
        return this;
    }
}