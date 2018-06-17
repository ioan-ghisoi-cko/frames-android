package com.checkout.android_sdk.Request;

/**
 * The request model object for the Google Pay tokenisation request
 */
import com.checkout.android_sdk.Models.GooglePayModel;

public class GooglePayTokenisationRequest {

    private String type = "googlepay";
    private GooglePayModel token_data = new GooglePayModel();

    public GooglePayTokenisationRequest setSignature(String signature) {
        this.token_data.setSignature(signature);
        return this;
    }

    public GooglePayTokenisationRequest setProtocolVersion(String protocolVersion) {
        this.token_data.setProtocolVersion(protocolVersion);
        return this;
    }

    public GooglePayTokenisationRequest setSignedMessage(String signedMessage) {
        this.token_data.setSignedMessage(signedMessage);
        return this;
    }
}
