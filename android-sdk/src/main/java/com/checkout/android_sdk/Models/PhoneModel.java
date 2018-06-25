package com.checkout.android_sdk.Models;

/**
 * Http request Phone object model
 */
public class PhoneModel {

    private String countryCode;
    private String number;

    public PhoneModel(String countryCode, String number) {
        this.countryCode = countryCode;
        this.number = number;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getNumber() {
        return number;
    }
}
