package com.example.android_sdk.Utils;

public class BillingDetails {
    private String addressLine1;
    private String addressLine2;
    private String postcode;
    private String country;
    private String city;
    private String state;
    private PhoneDetails phone;

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPhone(PhoneDetails phone) {
        this.phone = phone;
    }
}
