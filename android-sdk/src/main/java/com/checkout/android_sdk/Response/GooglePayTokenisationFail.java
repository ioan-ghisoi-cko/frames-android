package com.checkout.android_sdk.Response;

/**
 * The response model object for the Google Pay tokenisation error
 */
public class GooglePayTokenisationFail {

    private String request_id;
    private String error_type;
    private String[] error_codes;

    public String getRequestId() {
        return request_id;
    }

    public GooglePayTokenisationFail setRequestId(String request_id) {
        this.request_id = request_id;
        return this;
    }

    public String getErrorType() {
        return error_type;
    }

    public GooglePayTokenisationFail setErrorType(String error_type) {
        this.error_type = error_type;
        return this;
    }

    public String[] getErrorCodes() {
        return error_codes;
    }

    public GooglePayTokenisationFail setErrorCodes(String[] error_codes) {
        this.error_codes = error_codes;
        return this;
    }
}
