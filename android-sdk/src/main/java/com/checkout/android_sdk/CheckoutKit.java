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
public class CheckoutKit extends FrameLayout {

    /**
     * This is interface used as a callback for when the card token is generated
     */
    public interface OnTokenGenerated {
        void onTokenGenerated(CardTokenisationResponse response);
        void onError(CardTokenisationFail error);
    }

    /**
     * This is interface used as a callback for when the 3D secure functionality is used
     */
    public interface on3DSFinished {
        void onSuccess(String token);
        void onError(String errorMessage);
    }

    /**
     * This is interface used as a callback for when the google pay token is generated
     */
    public interface OnGooglePayTokenGenerated {
        void onTokenGenerated(GooglePayTokenisationResponse response);
        void onError(GooglePayTokenisationFail error);
    }

    // Environments for card tokenisation
    public enum card {
        SANDBOX("https://sandbox.checkout.com/api2/v2/tokens/card/"),
        LIVE("https://api2.checkout.com/v2/tokens/card/");
        private String url;
        card(String url) {
            this.url = url;
        }
    }
    // Environments for Google Pay tokenisation
    public enum googlepay {
        SANDBOX("https://sandbox.checkout.com/api2/tokens"),
        LIVE("https://api2.checkout.com/tokens");
        private String url;
        googlepay(String url) {
            this.url = url;
        }
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
            generateToken(generateRequest());
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
    private CheckoutKit.OnTokenGenerated mTokenListener;
    public CheckoutKit.on3DSFinished m3DSecureListener;
    private CheckoutKit.OnGooglePayTokenGenerated mGooglePayTokenListener;

    private String ENVIRONMENT = "sandbox";
    private String KEY = "";

    private CustomAdapter customAdapter;
    private ViewPager mPager;
    private AttributeSet attrs;
    private DataStore mDataStore = DataStore.getInstance();

    /**
     * This is the constructor used when the module is used without the UI.
     */
    public CheckoutKit(@NonNull Context context) {
        this(context, null);
    }

    public CheckoutKit(@NonNull Context context, @Nullable AttributeSet attrs) {
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
     * This method is used to generate a card token.
     * <p>
     * It takes a {@link CardTokenisationRequest} as the argumnet and it will perform a
     * HTTP Post request to generate the token. it is important to you select an environment and
     * provide your public key before calling tis method. Moreover it is important to set a callback
     * {@link OnTokenGenerated} so you can receive the token back.
     * <p>
     * If you are using the UI of the SDK this method will be called automatically, but you still
     * need to provide the callback, key and environment when initialising this class
     *
     * @param request Custom request body to be used in the HTTP call.
     */
    public void generateToken(CardTokenisationRequest request) {

        // Initialise the HTTP utility class
        HttpUtils http = new HttpUtils(mContext);

        // Provide a callback for when the token request is completed
        http.setTokenListener(mTokenListener);

        // Using Gson to convert the custom request object into a JSON string for use in the HTTP call
        Gson gson = new Gson();
        String jsonBody = gson.toJson(request);

        try {
            // Remove any spaces or uppercase letters when defining the environment
            // Decide the environment and perform the request
            if (ENVIRONMENT.toLowerCase().replaceAll(" ", "").equals("live")) {
                http.generateToken(this.KEY, card.LIVE.url, jsonBody);
            } else {
                http.generateToken(this.KEY, card.SANDBOX.url, jsonBody);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used set the the environment for use in the card tokenisation requests
     *
     * @param environment this can be either live or sandbox
     */
    public CheckoutKit setEnvironment(String environment) {
        this.ENVIRONMENT = environment;
        return this;
    }

    /**
     * This method is used set the the public key for use in the card tokenisation requests
     *
     * @param key the public key from the Checkout.com HUB
     */
    public CheckoutKit setKey(String key) {
        this.KEY = key;
        return this;
    }

    /**
     * This method is used set the accepted card schemes
     *
     * @param cards array of accepted cards
     */
    public CheckoutKit setAcceptedCard(CardUtils.Cards[] cards) {
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
     * This method used to create a token that can be used in a server environment to create a
     * charge. It take a GooglePay payload in JSONObject. The payload is usually generated in
     * the handlePaymentSuccess method shown in the Google Pay example from Google (token.getToken())
     *
     * @param googlePayToken Google Pay Payload
     */
    public void generateGooglePayToken(JSONObject googlePayToken) throws JSONException {

        // Initialise the HTTP utility class
        HttpUtils http = new HttpUtils(mContext);

        // Provide a callback for when the token request is completed
        http.setGooglePayTokenListener(mGooglePayTokenListener);

        GooglePayTokenisationRequest gPay = new GooglePayTokenisationRequest();

        gPay
                .setSignature(googlePayToken.getString("signature"))
                .setProtocolVersion(googlePayToken.getString("protocolVersion"))
                .setSignedMessage(googlePayToken.getString("signedMessage"));

        // Using Gson to convert the custom request object into a JSON string for use in the HTTP call
        Gson gson = new Gson();
        String jsonBody = gson.toJson(gPay);

        try {
            // Remove any spaces or uppercase letters when defining the environment
            // Decide the environment and perform the request
            if (ENVIRONMENT.toLowerCase().replaceAll(" ", "").equals("live")) {
                http.generateGooglePayToken(this.KEY, googlepay.LIVE.url, jsonBody);
            } else {
                http.generateGooglePayToken(this.KEY, googlepay.SANDBOX.url, jsonBody);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
    public CheckoutKit set3DSListener(CheckoutKit.on3DSFinished listener) {
        this.m3DSecureListener = listener;
        return this;
    }

    /**
     * This method used to set a callback for 3D Secure handling.
     *
     * @return CheckoutKit to allow method chaining
     */
    public CheckoutKit setTokenListener(OnTokenGenerated listener) {
        this.mTokenListener = listener;
        return this;
    }

    /**
     * This method used to set a callback for Google Pay handling.
     *
     * @return CheckoutKit to allow method chaining
     */
    public CheckoutKit setGooglePayListener(OnGooglePayTokenGenerated listener) {
        this.mGooglePayTokenListener = listener;
        return this;
    }
}
