package com.checkout.android_sdk;

import com.checkout.android_sdk.Store.DataStore;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DataStoreTests {

    private DataStore mDataStore = DataStore.getInstance();

    @Test
    public void get_instance() {
        mDataStore = DataStore.getInstance();
        assertEquals(mDataStore, mDataStore);
    }

    @Test
    public void get_set_card_number() {
        mDataStore.setCardNumber("4242");
        assertEquals("4242", mDataStore.getCardNumber());
    }

    @Test
    public void get_set_card_month() {
        mDataStore.setCardMonth("06");
        assertEquals("06", mDataStore.getCardMonth());
    }

    @Test
    public void get_set_card_year() {
        mDataStore.setCardYear("2018");
        assertEquals("2018", mDataStore.getCardYear());
    }

    @Test
    public void get_set_card_cvv() {
        mDataStore.setCardCvv("2018");
        assertEquals("2018", mDataStore.getCardCvv());
    }

    @Test
    public void get_set_card_cvv_length() {
        mDataStore.setCvvLength(2);
        assertEquals(2, mDataStore.getCvvLength());
    }

    @Test
    public void get_set_card_number_validity() {
        mDataStore.setValidCardNumber(false);
        assertEquals(false, mDataStore.isValidCardNumber());
        mDataStore.setValidCardNumber(true);
        assertEquals(true, mDataStore.isValidCardNumber());
    }

    @Test
    public void get_set_card_month_validity() {
        mDataStore.setValidCardMonth(false);
        assertEquals(false, mDataStore.isValidCardMonth());
        mDataStore.setValidCardMonth(true);
        assertEquals(true, mDataStore.isValidCardMonth());
    }

    @Test
    public void get_set_card_year_validity() {
        mDataStore.setValidCardYear(false);
        assertEquals(false, mDataStore.isValidCardYear());
        mDataStore.setValidCardYear(true);
        assertEquals(true, mDataStore.isValidCardYear());
    }

    @Test
    public void get_set_card_cvv_validity() {
        mDataStore.setValidCardCvv(false);
        assertEquals(false, mDataStore.isValidCardCvv());
        mDataStore.setValidCardCvv(true);
        assertEquals(true, mDataStore.isValidCardCvv());
    }

    private static final String CARD_ENV_SANDBOX = "https://sandbox.checkout.com/api2/v2/tokens/card/";
    private static final String CARD_ENV_LIVE = "https://api2.checkout.com/v2/tokens/card/";
    private static final String GOOGLE_ENV_SANDBOX = "https://sandbox.checkout.com/api2/v2/tokens/card/";
    private static final String GOOGLE_ENV_LIVE = "https://api2.checkout.com/v2/tokens/card/";


    @Test
    public void get_customer_billing() {
        mDataStore.setCustomerAddress1("London Address 1");
        mDataStore.setCustomerAddress2("London Address 2");
        mDataStore.setCustomerCountry("United Kingdom");
        mDataStore.setCustomerCity("London");
        mDataStore.setCustomerZipcode("w1w w1w");
        mDataStore.setCustomerState("London");
        mDataStore.setCustomerPhone("071234567891");
        mDataStore.setCustomerPhonePrefix("44");

        assertEquals("London Address 1", mDataStore.getCustomerAddress1());
        assertEquals("London Address 2", mDataStore.getCustomerAddress2());
        assertEquals("United Kingdom", mDataStore.getCustomerCountry());
        assertEquals("London", mDataStore.getCustomerCity());
        assertEquals("w1w w1w", mDataStore.getCustomerZipcode());
        assertEquals("London", mDataStore.getCustomerState());
        assertEquals("071234567891", mDataStore.getCustomerPhone());
        assertEquals("44", mDataStore.getCustomerPhonePrefix());
    }

    @Test
    public void get_set_outcome_urls() {
        mDataStore.setSuccessUrl("checkout.com/success");
        mDataStore.setFailUrl("checkout.com/fail");
        assertEquals("checkout.com/success", mDataStore.getSuccessUrl());
        assertEquals("checkout.com/fail", mDataStore.getFailUrl());
    }


}
