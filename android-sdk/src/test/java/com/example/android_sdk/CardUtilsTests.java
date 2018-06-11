package com.example.android_sdk;

import com.example.android_sdk.Utils.CardUtils;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CardUtilsTests {

    private CardUtils utils = new CardUtils();

    // VISA

    @Test
    public void visa_short_isFound() {
        assertEquals("visa", utils.getType("4").name);
    }

    @Test
    public void visa_full_isFound() {
        assertEquals("visa", utils.getType("4242424242424242").name);
    }

    @Test
    public void visa_short_isNotValid() {
        assertEquals(false, utils.isValidCard("42"));
    }

    @Test
    public void visa_short_isValid() {
        assertEquals(true, utils.isValidCard("4242424242424242"));
    }

    @Test
    public void visa_short_isFormatted() {
        assertEquals("4242 4242", utils.getFormattedCardNumber("42424242"));
    }

    @Test
    public void visa_full_isFormatted() {
        assertEquals("4242 4242 4242 4242", utils.getFormattedCardNumber("4242424242424242"));
    }

    // MASTERCARD

    @Test
    public void mastercard_short_isFound() {
        assertEquals("mastercard", utils.getType("5436").name);
    }

    @Test
    public void mastercard_full_isFound() {
        assertEquals("mastercard", utils.getType("5436031030606378").name);
    }

    @Test
    public void mastercard_short_isNotValid() {
        assertEquals(false, utils.isValidCard("5436"));
    }

    @Test
    public void mastercard_short_isValid() {
        assertEquals(true, utils.isValidCard("5436031030606378"));
    }

    @Test
    public void mastercard_short_isFormatted() {
        assertEquals("5436 0310", utils.getFormattedCardNumber("54360310"));
    }

    @Test
    public void mastercard_full_isFormatted() {
        assertEquals("5436 0310 3060 6378", utils.getFormattedCardNumber("5436031030606378"));
    }

    // AMEX

    @Test
    public void amex_short_isFound() {
        assertEquals("amex", utils.getType("3456").name);
    }

    @Test
    public void amex_full_isFound() {
        assertEquals("amex", utils.getType("345678901234564").name);
    }

    @Test
    public void amex_short_isNotValid() {
        assertEquals(false, utils.isValidCard("3456"));
    }

    @Test
    public void amex_short_isValid() {
        assertEquals(true, utils.isValidCard("345678901234564"));
    }

    @Test
    public void amex_short_isFormatted() {
        assertEquals("3782 822463", utils.getFormattedCardNumber("3782822463"));
    }

    @Test
    public void amex_full_isFormatted() {
        assertEquals("3782 822463 10005", utils.getFormattedCardNumber("378282246310005"));
    }

    // DINERSCLUB

    @Test
    public void diners_short_isFound() {
        assertEquals("dinersclub", utils.getType("301234").name);
    }

    @Test
    public void diners_full_isFound() {
        assertEquals("dinersclub", utils.getType("30123456789019").name);
    }

    @Test
    public void diners_short_isNotValid() {
        assertEquals(false, utils.isValidCard("301234"));
    }

    @Test
    public void diners_short_isValid() {
        assertEquals(true, utils.isValidCard("30123456789019"));
    }

    @Test
    public void diners_short_isFormatted() {
        assertEquals("3012 345678", utils.getFormattedCardNumber("3012345678"));
    }

    @Test
    public void diners_full_isFormatted() {
        assertEquals("3012 345678 9019", utils.getFormattedCardNumber("30123456789019"));
    }

    // DISCOVER

    @Test
    public void discover_short_isFound() {
        assertEquals("discover", utils.getType("60111111").name);
    }

    @Test
    public void discover_full_isFound() {
        assertEquals("discover", utils.getType("6011111111111117").name);
    }

    @Test
    public void discover_short_isNotValid() {
        assertEquals(false, utils.isValidCard("60111111"));
    }

    @Test
    public void discover_short_isValid() {
        assertEquals(true, utils.isValidCard("6011111111111117"));
    }

    @Test
    public void discover_short_isFormatted() {
        assertEquals("6011 1111", utils.getFormattedCardNumber("60111111"));
    }

    @Test
    public void discover_full_isFormatted() {
        assertEquals("6011 1111 1111 1117", utils.getFormattedCardNumber("6011111111111117"));
    }

    // JCB

    @Test
    public void jcb_short_isFound() {
        assertEquals("jcb", utils.getType("35301113").name);
    }

    @Test
    public void jcb_full_isFound() {
        assertEquals("jcb", utils.getType("3530111333300000").name);
    }

    @Test
    public void jcb_short_isNotValid() {
        assertEquals(false, utils.isValidCard("35301113"));
    }

    @Test
    public void jcb_short_isValid() {
        assertEquals(true, utils.isValidCard("3530111333300000"));
    }

    @Test
    public void jcb_short_isFormatted() {
        assertEquals("3530 1113", utils.getFormattedCardNumber("35301113"));
    }

    @Test
    public void jcb_full_isFormatted() {
        assertEquals("3530 1113 3330 0000", utils.getFormattedCardNumber("3530111333300000"));
    }

    // UNIONPAY

    @Test
    public void unionpay_short_isFound() {
        assertEquals("unionpay", utils.getType("621234").name);
    }

    @Test
    public void unionpay_full_isFound() {
        assertEquals("unionpay", utils.getType("6212345678901265").name);
    }

    @Test
    public void unionpay_short_isNotValid() {
        assertEquals(false, utils.isValidCard("621234"));
    }

    @Test
    public void unionpay_short_isValid() {
        assertEquals(true, utils.isValidCard("6212345678901265"));
    }

    @Test
    public void unionpay_short_isFormatted() {
        assertEquals("6212 3456", utils.getFormattedCardNumber("62123456"));
    }

    @Test
    public void unionpay_full_isFormatted() {
        assertEquals("6212 3456 7890 1265", utils.getFormattedCardNumber("6212345678901265"));
    }

    // MAESTRO

    @Test
    public void maestro_short_isFound() {
        assertEquals("maestro", utils.getType("6759649").name);
    }

    @Test
    public void maestro_full_isFound() {
        assertEquals("maestro", utils.getType("6759649826438453").name);
    }

    @Test
    public void maestro_short_isNotValid() {
        assertEquals(false, utils.isValidCard("6759649"));
    }

    @Test
    public void maestro_short_isValid() {
        assertEquals(true, utils.isValidCard("6759649826438453"));
    }

    @Test
    public void maestro_short_isFormatted() {
        assertEquals("6759 6498", utils.getFormattedCardNumber("67596498"));
    }

    @Test
    public void maestro_full_isFormatted() {
        assertEquals("6759 6498 2643 8453", utils.getFormattedCardNumber("6759649826438453"));
    }


}
