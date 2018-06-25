package com.checkout.android_sdk.Utils;

public enum Environment {
    SANDBOX("https://sandbox.checkout.com/api2/v2/tokens/card/", "https://sandbox.checkout.com/api2/tokens"),
    LIVE("https://api2.checkout.com/v2/tokens/card/", "https://api2.checkout.com/tokens");

    public final String token;
    public final String googlePay;

    Environment(String token, String googlePay) {
        this.token = token;
        this.googlePay = googlePay;
    }
}