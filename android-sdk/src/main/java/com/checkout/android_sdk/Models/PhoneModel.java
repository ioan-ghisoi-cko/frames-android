package com.checkout.android_sdk.Models;

/**
 * Http request Phone object model
 */
public class PhoneModel {

    public String countryCode;
    public String number;

    public PhoneModel(String countryCode, String number) {
        this.countryCode = countryCode;
        this.number = number;
    }

    public PhoneModel() {}

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
