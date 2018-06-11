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
import android.widget.Toast;

import com.example.android_sdk.Store.DataStore;
import com.example.android_sdk.Utils.CustomAdapter;
import com.example.android_sdk.View.BillingDetailsView;
import com.example.android_sdk.View.CardDetailsView;

public class PaymentForm extends FrameLayout {

    private static int CARD_DETAILS_PAGE_INDEX = 0;
    private static int BILLING_DETAILS_PAGE_INDEX = 1;

    private Context mContext;
    private CustomAdapter customAdapter;
    private ViewPager mPager;
    private AttributeSet attrs;
    private String mBackgroundHex;
    private String mHighlightsHex;
    private DataStore mDataStore = DataStore.getInstance();

    private CardDetailsView.GoToBillingListener mCardListener = new CardDetailsView.GoToBillingListener() {
        @Override
        public void onGoToBilingPressed() {
            mPager.setCurrentItem(BILLING_DETAILS_PAGE_INDEX);
            customAdapter.updateBillingSpinner("");
        }
    };

    private BillingDetailsView.Listener mBillingListener = new BillingDetailsView.Listener() {
        @Override
        public void onBillingCompleted(String address) {
            customAdapter.updateBillingSpinner(address);
            mPager.setCurrentItem(CARD_DETAILS_PAGE_INDEX);
        }

        @Override
        public void onBillingCanceled() {
            mPager.setCurrentItem(CARD_DETAILS_PAGE_INDEX);
        }
    };

    public interface on3DSFinished {

        void onSuccess(String token);

        void onError(String errorMessage);
    }

    public PaymentForm.on3DSFinished m3DSecureListener;

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
        mBackgroundHex = arr.getString(R.styleable.PaymentForm_formBackground);
        mHighlightsHex = arr.getString(R.styleable.PaymentForm_highlight);
        arr.recycle();

        mPager = findViewById(R.id.view_pager);
        customAdapter = new CustomAdapter(mContext);
        customAdapter.setCardDetailsListener(mCardListener);
        customAdapter.setBillingListener(mBillingListener);
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

    public void set3DSListener (PaymentForm.on3DSFinished listener) {
        this.m3DSecureListener = listener;
    }
}
