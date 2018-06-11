package com.example.android_sdk.Store;

/**
 * The DataStore
 * <p>
 * Used to contain state within the SDK for easy communication between custom components.
 * It is also used preserve and restore state when in case the device orientation changes.
 *
 */
public class DataStore {

    private static final String CARD_ENV_SANDBOX = "https://sandbox.checkout.com/api2/v2/tokens/card/";
    private static final String CARD_ENV_LIVE = "https://api2.checkout.com/v2/tokens/card/";
    private static final String GOOGLE_ENV_SANDBOX = "https://sandbox.checkout.com/api2/v2/tokens/card/";
    private static final String GOOGLE_ENV_LIVE = "https://api2.checkout.com/v2/tokens/card/";
    private String mSuccessUrl;
    private String mFailUrl;


    private static DataStore INSTANCE = null;
    private String mCardNumber;
    private String mCardMonth;
    private String mCardYear;
    private String mCardCvv;
    private int mCvvLength = 4;

    private boolean IsValidCardNumber = false;
    private boolean IsValidCardMonth = false;
    private boolean IsValidCardYear = false;
    private boolean IsValidCardCvv = false;

    private String mCustomerCountry = "";
    private String mCustomerAddress1 = "";
    private String mCustomerAddress2 = "";
    private String mCustomerCity = "";
    private String mCustomerState = "";
    private String mCustomerZipcode = "";
    private String mCustomerPhonePrefix = "";
    private String mCustomerPhone = "";

    protected DataStore() {
    }

    public static DataStore getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataStore();
        }
        return (INSTANCE);
    }

    public static String getCardEnvSandbox() {
        return CARD_ENV_SANDBOX;
    }

    public static String getCardEnvLive() {
        return CARD_ENV_LIVE;
    }

    public static String getGoogleEnvSandbox() {
        return GOOGLE_ENV_SANDBOX;
    }

    public static String getGoogleEnvLive() {
        return GOOGLE_ENV_LIVE;
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

    public void cleanBillingData() {
        DataStore.getInstance().setCustomerCountry("");
        DataStore.getInstance().setCustomerAddress1("");
        DataStore.getInstance().setCustomerAddress2("");
        DataStore.getInstance().setCustomerCity("");
        DataStore.getInstance().setCustomerState("");
        DataStore.getInstance().setCustomerZipcode("");
        DataStore.getInstance().setCustomerPhone("");
    }
}