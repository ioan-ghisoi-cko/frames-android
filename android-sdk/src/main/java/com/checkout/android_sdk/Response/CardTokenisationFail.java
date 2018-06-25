package com.checkout.android_sdk.Response;

/**
 * The response model object for the card tokenisation error
 */
public class CardTokenisationFail {

    private String eventId;
    private String errorCode;
    private String message;
    private String[] errorMessageCodes;
    private String[] errors;

    public String getEventId() {
        return eventId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public String[] getErrorMessageCodes() {
        return errorMessageCodes;
    }

    public String[] getErrors() {
        return errors;
    }
}
