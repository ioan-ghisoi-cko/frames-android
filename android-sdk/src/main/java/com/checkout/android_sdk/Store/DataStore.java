package com.checkout.android_sdk.Store;

import com.checkout.android_sdk.Utils.CardUtils;

/**
 * The DataStore
 * <p>
 * Used to contain state within the SDK for easy communication between custom components.
 * It is also used preserve and restore state when in case the device orientation changes.
 */
public class DataStore {

    private static DataStore INSTANCE = null;
    private String mCardNumber;
    private String mCardMonth;
    private String mCardYear;
    private String mCardCvv;
    private int mCvvLength = 4;

    private CardUtils.Cards[] acceptedCards;

    private String mSuccessUrl;
    private String mFailUrl;

    private boolean IsValidCardNumber = false;
    private boolean IsValidCardMonth = false;
    private boolean IsValidCardYear = false;
    private boolean IsValidCardCvv = false;

    private String mCustomerName = "";
    private String mCustomerCountry = "";
    private String mCustomerAddress1 = "";
    private String mCustomerAddress2 = "";
    private String mCustomerCity = "";
    private String mCustomerState = "";
    private String mCustomerZipcode = "";
    private String mCustomerPhonePrefix = "";
    private String mCustomerPhone = "";

    private boolean showBilling = true;
    private boolean billingCompleted = false;

    protected DataStore() {
    }

    public static DataStore getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataStore();
        }
        return (INSTANCE);
    }

    public String getSuccessUrl() {
        return mSuccessUrl;
    }

    public void setSuccessUrl(String successUrl) {
        mSuccessUrl = successUrl;
    }

    public String getFailUrl() {
        return mFailUrl;
    }

    public void setFailUrl(String failUrl) {
        mFailUrl = failUrl;
    }

    public String getCardNumber() {
        return mCardNumber;
    }

    public void setCardNumber(String cardNumber) {
        mCardNumber = cardNumber;
    }

    public String getCardMonth() {
        return mCardMonth;
    }

    public void setCardMonth(String mCardMonth) {
        this.mCardMonth = mCardMonth;
    }

    public String getCardYear() {
        return mCardYear;
    }

    public void setCardYear(String cardYear) {
        mCardYear = cardYear;
    }

    public String getCardCvv() {
        return mCardCvv;
    }

    public void setCardCvv(String cardCvv) {
        mCardCvv = cardCvv;
    }

    public int getCvvLength() {
        return mCvvLength;
    }

    public void setCvvLength(int cvvLength) {
        mCvvLength = cvvLength;
    }

    public boolean isValidCardNumber() {
        return IsValidCardNumber;
    }

    public void setValidCardNumber(boolean validCardNumber) {
        IsValidCardNumber = validCardNumber;
    }

    public boolean isValidCardMonth() {
        return IsValidCardMonth;
    }

    public void setValidCardMonth(boolean validCardMonth) {
        IsValidCardMonth = validCardMonth;
    }

    public boolean isValidCardYear() {
        return IsValidCardYear;
    }

    public void setValidCardYear(boolean validCardYear) {
        IsValidCardYear = validCardYear;
    }

    public boolean isValidCardCvv() {
        return IsValidCardCvv;
    }

    public void setValidCardCvv(boolean validCardCvv) {
        IsValidCardCvv = validCardCvv;
    }

    public String getCustomerCountry() {
        return mCustomerCountry;
    }

    public void setCustomerCountry(String customerCountry) {
        mCustomerCountry = customerCountry;
    }

    public String getCustomerPhonePrefix() {
        return mCustomerPhonePrefix;
    }

    public void setCustomerPhonePrefix(String customerPhonePrefix) {
        mCustomerPhonePrefix = customerPhonePrefix;
    }

    public String getCustomerAddress1() {
        return mCustomerAddress1;
    }

    public void setCustomerAddress1(String customerAddress1) {
        mCustomerAddress1 = customerAddress1;
    }

    public String getCustomerAddress2() {
        return mCustomerAddress2;
    }

    public void setCustomerAddress2(String customerAddress2) {
        mCustomerAddress2 = customerAddress2;
    }

    public String getCustomerCity() {
        return mCustomerCity;
    }

    public void setCustomerCity(String customerCity) {
        mCustomerCity = customerCity;
    }

    public String getCustomerState() {
        return mCustomerState;
    }

    public void setCustomerState(String customerState) {
        mCustomerState = customerState;
    }

    public String getCustomerZipcode() {
        return mCustomerZipcode;
    }

    public void setCustomerZipcode(String customerZipcode) {
        mCustomerZipcode = customerZipcode;
    }

    public String getCustomerPhone() {
        return mCustomerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        mCustomerPhone = customerPhone;
    }

    public boolean getBillingVisibility() {
        return showBilling;
    }

    public void setShowBilling(boolean showBilling) {
        this.showBilling = showBilling;
    }

    public String getCustomerName() {
        return mCustomerName;
    }

    public void setCustomerName(String customerName) {
        mCustomerName = customerName;
    }

    public boolean isBillingCompleted() {
        return billingCompleted;
    }

    public void setBillingCompleted(boolean billingCompleted) {
        this.billingCompleted = billingCompleted;
    }

    public void cleanBillingData() {
        DataStore.getInstance().setCustomerCountry("");
        DataStore.getInstance().setCustomerAddress1("");
        DataStore.getInstance().setCustomerAddress2("");
        DataStore.getInstance().setCustomerCity("");
        DataStore.getInstance().setCustomerState("");
        DataStore.getInstance().setCustomerZipcode("");
        DataStore.getInstance().setCustomerPhone("");
    }

    public CardUtils.Cards[] getAcceptedCards() {
        return acceptedCards;
    }

    public void setAcceptedCards(CardUtils.Cards[] acceptedCards) {
        this.acceptedCards = acceptedCards;
    }
}