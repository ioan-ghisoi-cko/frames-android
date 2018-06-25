package com.checkout.android_sdk;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.checkout.android_sdk.Request.CardTokenisationRequest;
import com.checkout.android_sdk.Request.GooglePayTokenisationRequest;
import com.checkout.android_sdk.Response.CardTokenisationFail;
import com.checkout.android_sdk.Response.CardTokenisationResponse;
import com.checkout.android_sdk.Response.GooglePayTokenisationFail;
import com.checkout.android_sdk.Response.GooglePayTokenisationResponse;
import com.checkout.android_sdk.Store.DataStore;
import com.checkout.android_sdk.Utils.CardUtils;
import com.checkout.android_sdk.Utils.CustomAdapter;
import com.checkout.android_sdk.Utils.Environment;
import com.checkout.android_sdk.Utils.HttpUtils;
import com.checkout.android_sdk.View.BillingDetailsView;
import com.checkout.android_sdk.View.CardDetailsView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Contains helper methods dealing with the tokenisation or payment from customisation
 * <p>
 * Most of the methods that include interaction with the Checkout.com API rely on
 * callbacks to communicate outcomes. Please make sure you set the key/environment
 * and appropriate  callbacks to a ensure successful interaction
 */
public class PaymentForm extends FrameLayout {

    /**
     * This is interface used as a callback for when the 3D secure functionality is used
     */
    public interface on3DSFinished {
        void onSuccess(String token);
        void onError(String errorMessage);
    }

    // Indexes for the pages
    private static int CARD_DETAILS_PAGE_INDEX = 0;
    private static int BILLING_DETAILS_PAGE_INDEX = 1;

    /**
     * This is a callback used to use the generateToken functionality after the user completes
     * the details in the form and clicks the Pay button
     */
    private final CardDetailsView.DetailsCompleted mDetailsCompletedListener = new CardDetailsView.DetailsCompleted() {
        @Override
        public void onDetailsCompleted() {
            CheckoutAPIClient apiClient = new CheckoutAPIClient(mContext, KEY, ENVIRONMENT);
            apiClient.setTokenListener(mTokenListener);
            apiClient.generateToken(generateRequest());
        }
    };

    /**
     * This is a callback used to go back to the card details view from the billing page
     * and based on the action used decide is the billing spinner will be updated
     */
    private BillingDetailsView.Listener mBillingListener = new BillingDetailsView.Listener() {
        @Override
        public void onBillingCompleted() {
            customAdapter.updateBillingSpinner();
            mPager.setCurrentItem(CARD_DETAILS_PAGE_INDEX);
        }

        @Override
        public void onBillingCanceled() {
            customAdapter.clearBillingSpinner();
            mPager.setCurrentItem(CARD_DETAILS_PAGE_INDEX);
        }
    };

    /**
     * This is a callback used to navigate to the billing details page
     */
    private CardDetailsView.GoToBillingListener mCardListener = new CardDetailsView.GoToBillingListener() {
        @Override
        public void onGoToBillingPressed() {
            mPager.setCurrentItem(BILLING_DETAILS_PAGE_INDEX);
        }
    };


    private Context mContext;
    public PaymentForm.on3DSFinished m3DSecureListener;
    public CheckoutAPIClient.OnTokenGenerated mTokenListener;
    private Environment ENVIRONMENT = Environment.SANDBOX;
    private String KEY = "";

    private CustomAdapter customAdapter;
    private ViewPager mPager;
    private AttributeSet attrs;
    private DataStore mDataStore = DataStore.getInstance();

    /**
     * This is the constructor used when the module is used without the UI.
     */
    public PaymentForm(@NonNull Context context) {
            this(context, null);
        }

    public PaymentForm(@NonNull Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            this.mContext = context;
            this.attrs = attrs;
            initView();
    }

    /**
     * This method is used to initialise the UI of the module
     */
    private void initView() {
        // Set up the layout
        inflate(mContext, R.layout.payment_form, this);

        mPager = findViewById(R.id.view_pager);
        // Use a custom adapter for the viewpager
        customAdapter = new CustomAdapter(mContext);
        // Set up the callbacks
        customAdapter.setCardDetailsListener(mCardListener);
        customAdapter.setBillingListener(mBillingListener);
        customAdapter.setTokenDetailsCompletedListener(mDetailsCompletedListener);
        mPager.setAdapter(customAdapter);
        mPager.setEnabled(false);
    }


    /**
     * This method is used set the the environment for use in the card tokenisation requests
     *
     * @param environment this can be either live or sandbox
     */
    public PaymentForm setEnvironment(Environment environment) {
        this.ENVIRONMENT = environment;
        return this;
    }

    /**
     * This method is used set the the public key for use in the card tokenisation requests
     *
     * @param key the public key from the Checkout.com HUB
     */
    public PaymentForm setKey(String key) {
        this.KEY = key;
        return this;
    }

    /**
     * This method is used set the accepted card schemes
     *
     * @param cards array of accepted cards
     */
    public PaymentForm setAcceptedCard(CardUtils.Cards[] cards) {
        mDataStore.setAcceptedCards(cards);
        return this;
    }

    /**
     * This method is used to handle 3D Secure URLs.
     * <p>
     * It wil programmatically generate a WebView and listen for when the url changes
     * in either the success url or the fail url.
     *
     * @param url        the 3D Secure url
     * @param successUrl the Redirection url set up in the Checkout.com HUB
     * @param failsUrl   the Redirection Fail url set up in the Checkout.com HUB
     */
    public void handle3DS(String url, final String successUrl, final String failsUrl) {
        if (mPager != null) {
            mPager.setVisibility(GONE); // dismiss the card form UI
        }
        WebView web = new WebView(mContext);
        web.loadUrl(url);
        web.getSettings().setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient() {
            // Listen for when teh URL changes and match t with either the success of fail url
            @Override
            public void onPageFinished(WebView view, String url) {
                if (url.contains(successUrl)) {
                    Uri uri = Uri.parse(url);
                    String paymentToken = uri.getQueryParameter("cko-payment-token");
                    m3DSecureListener.onSuccess(paymentToken);
                } else if (url.contains(failsUrl)) {
                    Uri uri = Uri.parse(url);
                    String paymentToken = uri.getQueryParameter("cko-payment-token");
                    m3DSecureListener.onError(paymentToken);
                }
            }
        });
        // Make WebView fill the layout
        web.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        addView(web);
    }

    /**
     * This method used to generate a {@link CardTokenisationRequest} with the details
     * completed by the user in the payment from
     * displayed in the payment form.
     *
     * @return CardTokenisationRequest
     */
    private CardTokenisationRequest generateRequest() {

        CardTokenisationRequest request = new CardTokenisationRequest();

        request
                .setCardNumber(sanitizeEntry(mDataStore.getCardNumber()))
                .setExpiryMonth(mDataStore.getCardMonth())
                .setExpiryYear(mDataStore.getCardYear())
                .setCvv(mDataStore.getCardCvv());

        // Only populate billing details if the user has completed the full form
        if (mDataStore.isBillingCompleted()) {
            request
                    .setName(mDataStore.getCustomerName())
                    .setCountry(mDataStore.getCustomerCountry())
                    .setAddressLine1(mDataStore.getCustomerAddress1())
                    .setAddressLine2(mDataStore.getCustomerAddress2())
                    .setCity(mDataStore.getCustomerCity())
                    .setState(mDataStore.getCustomerState())
                    .setPostcode(mDataStore.getCustomerZipcode())
                    .setPhoneNumber(mDataStore.getCustomerPhonePrefix(), mDataStore.getCustomerPhone());
        }

        return request;
    }

    /**
     * This method used to decide if the billing details option will be
     * displayed in the payment form.
     *
     * @param include boolean showing if the billing should be used
     */
    public void includeBilling(Boolean include) {
        if (!include) {
            mDataStore.setShowBilling(false);
        } else {
            mDataStore.setShowBilling(true);
        }
    }

    /**
     * Returns a String without any spaces
     * <p>
     * This method used to take a card number input String and return a
     * String that simply removed all whitespace, keeping only digits.
     *
     * @param entry the String value of a card number
     */
    private String sanitizeEntry(String entry) {
        return entry.replaceAll("\\D", "");
    }

    /**
     * This method used to set a callback for when the 3D Secure handling.
     */
    public PaymentForm set3DSListener(PaymentForm.on3DSFinished listener) {
        this.m3DSecureListener = listener;
        return this;
    }

    public PaymentForm setTokenListener(CheckoutAPIClient.OnTokenGenerated listener) {
        this.mTokenListener = listener;
        return this;
    }

}
