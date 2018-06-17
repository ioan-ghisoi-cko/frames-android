package com.checkout.android_sdk.Utils;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.checkout.android_sdk.R;

import java.util.Calendar;

/**
 * Provide information about different card types.
 */
public class CardUtils {

    /**
     * An enum that hold information about the different card types.
     * The sported card types are: VISA, AMEX, DISCOVER, UNIONPAY, JCB,
     * LASER, DINERSCLUB, MASTERCARD, MAESTRO and a DEFAULT abstract card.
     */
    public enum Cards {
        VISA("visa", R.drawable.visa, "^4\\d*$", "^4[0-9]{12}(?:[0-9]{3})?$", new int[]{13, 16}, 19, 3, new int[]{4, 9, 14}, true),
        AMEX("amex", R.drawable.amex, "^3[47]\\d*$", "/(\\d{1,4})(\\d{1,6})?(\\d{1,5})?/", new int[]{15}, 18, 4, new int[]{4, 6}, true),
        DISCOVER("discover", R.drawable.discover, "^(6011|65|64[4-9])\\d*$", "^6(?:011|5[0-9]{2})[0-9]{12}$", new int[]{16}, 23, 3, new int[]{4, 9, 14}, true),
        UNIONPAY("unionpay", R.drawable.unionpay, "^(((620|(621(?!83|88|98|99))|622(?!06|018)|62[3-6]|627[02,06,07]|628(?!0|1)|629[1,2]))\\d*|622018\\d{12})$", "^6(?:011|5[0-9]{2})[0-9]{12}$", new int[]{16, 17, 18, 19}, 23, 3, new int[]{4, 6, 14}, false),
        JCB("jcb", R.drawable.jcb, "^(2131|1800|35)\\d*$", "^(?:2131|1800|35[0-9]{3})[0-9]{11}$", new int[]{16}, 23, 3, new int[]{4, 9, 14}, true),
        DINERSCLUB("dinersclub", R.drawable.dinersclub, "^3(0[0-5]|[689])\\d*$", "^3(?:0[0-5]|[68][0-9])?[0-9]{11}$", new int[]{14}, 23, 3, new int[]{4, 6}, true),
        MASTERCARD("mastercard", R.drawable.mastercard, "^(5[1-5]|222[1-9]|22[3-9]|2[3-6]|27[0-1]|2720)\\d*$", "^5[1-5][0-9]{14}$", new int[]{16, 17}, 19, 3, new int[]{4, 9, 14}, true),
        MAESTRO("maestro", R.drawable.maestro, "^(?:5[06789]\\d\\d|(?!6011[0234])(?!60117[4789])(?!60118[6789])(?!60119)(?!64[456789])(?!65)6\\d{3})\\d{8,15}$", "^(5[06-9]|6[37])[0-9]{10,17}$", new int[]{12, 13, 14, 15, 16, 17, 18, 19}, 23, 3, new int[]{4, 9, 14}, true),
        DEFAULT("default", 0, "", "", new int[]{16}, 19, 3, new int[]{4, 9, 14}, false);

        public final String name;
        public final int resourceId;
        private final String pattern;
        public final String regex;
        public final int[] cardLength;
        public final int maxCardLength;
        public final int maxCvvLength;
        public final int[] gaps;
        private final boolean luhn;

        /**
         * The {@link Cards} constructor
         * <p>
         *
         * @param name          card name
         * @param pattern       pattern used to determine card type early
         * @param regex         full regex of a full card
         * @param maxCardLength the max length a card of a type can have
         * @param maxCvvLength  the max CVV a card of a type can have
         * @param gaps          the positions of the spaces spans ina formatted card
         * @see Cards
         */
        Cards(String name, int resourceId, String pattern, String regex, int[] cardLength, int maxCardLength, int maxCvvLength, int[] gaps, boolean luhn) {
            this.name = name;
            this.resourceId = resourceId;
            this.pattern = pattern;
            this.regex = regex;
            this.cardLength = cardLength;
            this.maxCardLength = maxCardLength;
            this.maxCvvLength = maxCvvLength;
            this.gaps = gaps;
            this.luhn = luhn;
        }
    }

    /**
     * Returns a Cards object can be used to identify the card type and
     * information about: regex, card/cvv maximum length, space separation
     * The number argument must specify as a String.
     * <p>
     * This method iterates a Cards enum, and determines if the the function
     * argument matches any pattern. Based on the verification, a Cards object
     * will be returned.
     *
     * @param number the String value of a card number
     * @return Cards object for the given type found
     * @see Cards
     */
    public static Cards getType(String number) {

        // Remove spaces from The number String
        number = sanitizeEntry(number);
        CardUtils.Cards[] cards = CardUtils.Cards.values();

        // Iterate over the card card types and check what pattern matches
        if (!number.equals("")) {
            for (Cards card : cards) {
                if (number.matches(card.pattern)) {
                    return card;
                }
            }
        }

        // Return a default card if no card type is matched
        return Cards.DEFAULT;
    }

    /**
     * Returns a boolean showing is the card String is a valid card number.
     * <p>
     * This method is using the regex in {@link Cards} as well as the Luhn algorithm to
     * the terms the validity of a card number
     *
     * @param number the String value of a card number
     * @return If the card number is valid or not
     */
    public static boolean isValidCard(@Nullable String number) {
        if (number == null || number.equals("")) {
            return false;
        } else {
            number = sanitizeEntry(number);
            CardUtils.Cards[] cards = CardUtils.Cards.values();
            for (int i = 0; i < getType(number).cardLength.length; i++) {
                if (number.length() == getType(number).cardLength[i] &&
                        checkLuhn(number) &&
                        getType(number) != Cards.DEFAULT) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns a boolean showing is the card String is a valid card number.
     * <p>
     * This is using Luhn validation to determine the card validity
     *
     * @param number the String value of a card number
     * @return If the card number passes Luhn validation
     */
    private static boolean checkLuhn(String number) {
        final String rev = new StringBuffer(number).reverse().toString();
        final int len = rev.length();
        int oddSum = 0;
        int evenSum = 0;
        for (int i = 0; i < len; i++) {
            final char c = rev.charAt(i);
            final int digit = Character.digit(c, 10);
            if (i % 2 == 0) {
                oddSum += digit;
            } else {
                evenSum += digit / 5 + (2 * digit) % 10;
            }
        }
        return (oddSum + evenSum) % 10 == 0;
    }

    /**
     * f
     * Returns a String without any spaces
     * <p>
     * This method used to take a card number input String and return a
     * String that simply removed all whitespace, keeping only digits.
     *
     * @param entry the String value of a card number
     * @return Cards object for the given type found
     */
    private static String sanitizeEntry(String entry) {
        return entry.replaceAll("\\D", "");
    }

    /**
     * The card formatting method
     * <p>
     * Used to take a card number String and provide formatting (span space characters)
     *
     * @param number card number in string format
     * @return processedCard
     */
    public static String getFormattedCardNumber(String number) {

        // Remove spaces form the card String
        String processedCard = sanitizeEntry(number);

        CardUtils.Cards cardType = getType(number);

        // If the card is an AMEX or DINERSCLUB we iterate and span spaces at specific positions
        if (cardType.name.equals("amex") || cardType.name.equals("dinersclub") || cardType.name.equals("unionpay")) {
            for (int i = 0; i < cardType.gaps.length; i++) {
                processedCard = processedCard.replaceFirst("(\\d{" + cardType.gaps[i] + "})(?=\\d)", "$1 ");
            }
            // If the card is on any other kind we span a space after every group of 4 digits
        } else {
            processedCard = processedCard.replaceAll("(\\d{4})(?=\\d)", "$1 ");
        }

        return processedCard;
    }

    /**
     * Returns a boolean showing is the date is valid
     * <p>
     * Used to take a card number String and provide formatting (span space characters)
     *
     * @param month the card expiration month as a string
     * @param year the card expiration year as a string
     * @return boolean representing validity
     */
    public static boolean isValidDate(String month, String year) {
        if (TextUtils.isDigitsOnly(sanitizeEntry(month)) &&
                TextUtils.isDigitsOnly(sanitizeEntry(year))) {

            if (Integer.valueOf(month) < 1 || Integer.valueOf(month) > 12)
                return false;

            // Get current year and month
            Calendar calendar = Calendar.getInstance();
            int calendarYear = calendar.get(Calendar.YEAR);
            int calendarMonth = calendar.get(Calendar.MONTH);

            if (Integer.valueOf(year) < calendarYear)
                return false;
            if (Integer.valueOf(year) == calendarYear &&
                    Integer.valueOf(month) < calendarMonth)
                return false;

            return true;
        }
        return false;
    }

    /**
     * Returns a boolean showing is the cvv is valid in relation to the card type
     * <p>
     * Used to take a card number String and provide formatting (span space characters)
     *
     * @param cvv the card cvv
     * @param card the card object
     * @return boolean representing validity
     */
    public static boolean isValidCvv(String cvv, Cards card) {
        if (TextUtils.isDigitsOnly(sanitizeEntry(cvv)) &&
                card!= null) {
            if(card.maxCvvLength == cvv.length())
                return true;
        }
        return false;
    }
}
