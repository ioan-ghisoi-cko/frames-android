package com.checkout.android_sdk.Response;

/**
 * The response model object for the Google Pay tokenisation response
 */
public class GooglePayTokenisationResponse {

    private String type;
    private String token;
    private String expires_on;

    public GooglePayTokenisationResponse setType(String type) {
        this.type = type;
        return this;
    }

    public GooglePayTokenisationResponse setToken(String token) {
        this.token = token;
        return this;
    }

    public GooglePayTokenisationResponse setExpiration(String expires_on) {
        this.expires_on = expires_on;
        return this;
    }

    public String getType() {
        return type;
    }

    public String getToken() {
        return token;
    }

    public String getExpiration() {
        return expires_on;
    }
}
