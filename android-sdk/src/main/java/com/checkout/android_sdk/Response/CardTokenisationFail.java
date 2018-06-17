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

    public CardTokenisationFail setEventId(String eventId) {
        this.eventId = eventId;
        return this;
    }

    public CardTokenisationFail setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public CardTokenisationFail setMessage(String message) {
        this.message = message;
        return this;
    }

    public CardTokenisationFail setErrorMessageCodes(String[] errorMessageCodes) {
        this.errorMessageCodes = errorMessageCodes;
        return this;
    }

    public CardTokenisationFail setErrors(String[] errors) {
        this.errors = errors;
        return this;
    }

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
