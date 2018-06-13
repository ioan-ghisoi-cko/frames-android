package com.example.android_sdk;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.example.android_sdk.Store.DataStore;
import com.example.android_sdk.Utils.CustomAdapter;
import com.example.android_sdk.Utils.HttpUtils;
import com.example.android_sdk.Utils.TokenisationRequest;
import com.example.android_sdk.View.BillingDetailsView;
import com.example.android_sdk.View.CardDetailsView;
import com.google.gson.Gson;

import org.json.JSONException;

public class PaymentForm extends FrameLayout {

    private final CardDetailsView.DetailsCompleted mDetailsCompletedListener = new CardDetailsView.DetailsCompleted() {
        @Override
        public void onDetailsCompleted() {
            TokenisationRequest request = new TokenisationRequest();

            request
                    .setCardNumber(sanitizeEntry(mDataStore.getCardNumber()))
                    .setExpiryMonth(mDataStore.getCardMonth())
                    .setExpiryYear(mDataStore.getCardYear())
                    .setCvv(mDataStore.getCardCvv());

            // Only populate billing details if the user has completed the full form
            if(mDataStore.isBillingCompleted()) {
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

            generateToken(request);
        }
    };

    public interface OnTokenGenerated {
        void onTokenGenerated(String token);
        void onError(String errorMessage);
    }

    private static int CARD_DETAILS_PAGE_INDEX = 0;
    private static int BILLING_DETAILS_PAGE_INDEX = 1;

    private Context mContext;
    private CustomAdapter customAdapter;
    private String ENVIRONMENT = "sandbox";
    private String KEY = "";
    private ViewPager mPager;
    private AttributeSet attrs;
    private String mBackgroundHex;
    private String mHighlightsHex;
    private DataStore mDataStore = DataStore.getInstance();
    private PaymentForm.OnTokenGenerated mTokenListener;

    private CardDetailsView.GoToBillingListener mCardListener = new CardDetailsView.GoToBillingListener() {
        @Override
        public void onGoToBilingPressed() {
            mPager.setCurrentItem(BILLING_DETAILS_PAGE_INDEX);
            customAdapter.updateBillingSpinner();
        }
    };

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

    public interface on3DSFinished {

        void onSuccess(String token);

        void onError(String errorMessage);
    }

    public PaymentForm.on3DSFinished m3DSecureListener;

    public PaymentForm(@NonNull Context context, String environemnt, String key) {
        super(context, null);
        mContext = context;
        this.ENVIRONMENT = environemnt;
        this.KEY = key;
    }

    public PaymentForm(@NonNull Context context) {
        this(context, null);
    }

    public PaymentForm(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.attrs = attrs;
        initView();
    }

    private void initView() {
        inflate(mContext, R.layout.payment_form, this);

        TypedArray arr = mContext.obtainStyledAttributes(attrs, R.styleable.PaymentForm);
        mDataStore.setFormBackground(arr.getString(R.styleable.PaymentForm_formBackground));
        mDataStore.setFieldHighlight(arr.getString(R.styleable.PaymentForm_highlight));
        arr.recycle();

        mPager = findViewById(R.id.view_pager);
        customAdapter = new CustomAdapter(mContext);
        customAdapter.setCardDetailsListener(mCardListener);
        customAdapter.setBillingListener(mBillingListener);
        customAdapter.setTokenDetailsCompletedListener(mDetailsCompletedListener);
        mPager.setAdapter(customAdapter);
        mPager.setEnabled(false);

    }

    public void handle3DS(String url, final String successUrl, final String failsUrl) {
        mPager.setVisibility(GONE); // dismiss the card form UI
        WebView web = new WebView(mContext);
        web.loadUrl(url);
        web.getSettings().setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (url.contains(successUrl)){
                    Uri uri = Uri.parse(url);
                    String paymentToken = uri.getQueryParameter("cko-payment-token");
                    m3DSecureListener.onSuccess(paymentToken);
                } else if(url.contains(failsUrl)){
                    Uri uri = Uri.parse(url);
                    String paymentToken = uri.getQueryParameter("cko-payment-token");
                    m3DSecureListener.onSuccess(paymentToken);
                }
            }
        });
        web.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));

        addView(web); // <--- Key line
    }

    public PaymentForm setEnvironment(String environment) {
        this.ENVIRONMENT = environment;
        return this;
    }

    public PaymentForm setKey(String key) {
        this.KEY = key;
        return this;
    }

    public void generateToken(TokenisationRequest request) {

        HttpUtils http = new HttpUtils(mContext);

        http.setTokenListener(mTokenListener);

        Gson gson = new Gson();
        String jsonBody = gson.toJson(request);

        try {
            http.generateToken(this.KEY, DataStore.getInstance().getCardEnvSandbox(), jsonBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void set3DSListener (PaymentForm.on3DSFinished listener) {
        this.m3DSecureListener = listener;
    }

    public PaymentForm setTokenListener(OnTokenGenerated listener) {
        this.mTokenListener = listener;
        return this;
    }

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
     * @return Cards object for the given type found
     */
    private String sanitizeEntry(String entry) {
        return entry.replaceAll("\\D", "");
    }
}
