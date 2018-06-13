package com.example.android_sdk.Utils;

public class TokenisationRequest {

    private String number;
    private String name;
    private String expiryMonth;
    private String expiryYear;
    private String cvv;

    private BillingDetails billingDetails = new BillingDetails();

    public TokenisationRequest() {
        // Empty constructor
    }

    public TokenisationRequest(String number, String expiryMonth, String expiryYear, String cvv) {
        this.number = number;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.cvv = cvv;
    }

    public TokenisationRequest(String number, String expiryMonth, String expiryYear, String cvv, String name, String addressLine1, String addressLine2, String postcode, String country, String city, String state) {
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

    public TokenisationRequest(String number, String expiryMonth, String expiryYear, String cvv, String name, String addressLine1, String addressLine2, String postcode, String country, String city, String state, PhoneDetails phoneDetails, String countryCode, String phoneNumber) {
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
        PhoneDetails phone = new PhoneDetails();
        phone.setNumber(phoneNumber);
        phone.setCountryCode(countryCode);
        billingDetails.setPhone(phone);
    }

    public TokenisationRequest setCardNumber(String number) {
        this.number = number;
        return this;
    }

    public TokenisationRequest setExpiryMonth(String expiryMonth) {
        this.expiryMonth = expiryMonth;
        return this;
    }

    public TokenisationRequest setExpiryYear(String expiryYear) {
        this.expiryYear = expiryYear;
        return this;
    }

    public TokenisationRequest setCvv(String cvv) {
        this.cvv = cvv;
        return this;
    }

    public TokenisationRequest setName(String name) {
        this.name = name;
        return this;
    }

    public TokenisationRequest setAddressLine1(String addressLine1) {
        this.billingDetails.setAddressLine1(addressLine1);
        return this;
    }

    public TokenisationRequest setAddressLine2(String addressLine2) {
        this.billingDetails.setAddressLine2(addressLine2);
        return this;
    }

    public TokenisationRequest setPostcode(String postcode) {
        this.billingDetails.setPostcode(postcode);
        return this;
    }

    public TokenisationRequest setCountry(String country) {
        this.billingDetails.setCountry(country);
        return this;
    }

    public TokenisationRequest setCity(String city) {
        this.billingDetails.setCity(city);
        return this;
    }

    public TokenisationRequest setState(String state) {
        this.billingDetails.setState(state);
        return this;
    }

    public TokenisationRequest setPhoneNumber(String countryCode, String number) {
        PhoneDetails phone = new PhoneDetails();
        phone.setCountryCode(countryCode);
        phone.setNumber(number);
        this.billingDetails.setPhone(phone);
        return this;
    }
}

