package com.checkout.android_sdk.Models;

/**
 * Http request Phone object model
 */
public class PhoneModel {

    private String countryCode;
    private String number;

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getNumber() {
        return number;
    }
}
