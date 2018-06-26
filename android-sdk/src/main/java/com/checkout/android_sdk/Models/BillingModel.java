package com.checkout.android_sdk.Models;

/**
 * Http request billing details object model
 */
public class BillingModel {

    private String addressLine1;
    private String addressLine2;
    private String postcode;
    private String country;
    private String city;
    private String state;
    private PhoneModel phone;

    public BillingModel(String addressLine1, String addressLine2, String postcode, String country,
                        String city, String state, PhoneModel phone) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.postcode = postcode;
        this.country = country;
        this.city = city;
        this.state = state;
        this.phone = phone;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public PhoneModel getPhone() {
        return phone;
    }
}