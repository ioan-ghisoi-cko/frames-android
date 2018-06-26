package com.checkout.android_sdk.Models;

/**
 * Http request Google Pay object model
 */
public class GooglePayModel {

    private String signature;
    private String protocolVersion;
    private String signedMessage;

    public String getSignature() {
        return signature;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public String getSignedMessage() {
        return signedMessage;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public void setSignedMessage(String signedMessage) {
        this.signedMessage = signedMessage;
    }
}
