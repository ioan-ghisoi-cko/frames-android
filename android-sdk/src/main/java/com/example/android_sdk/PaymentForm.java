package com.example.android_sdk;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
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
    private ViewPager mPager;
    private AttributeSet attrs;
    private String mBackgroundHex;
    private String mHighlightsHex;
    private DataStore mDataStore = DataStore.getInstance();

    private CardDetailsView.GoToBillingListener mCardListener = new CardDetailsView.GoToBillingListener() {
        @Override
        public void onGoToBilingPressed() {
            mPager.setCurrentItem(BILLING_DETAILS_PAGE_INDEX);
        }
    };

    private BillingDetailsView.Listener mBillingListener = new BillingDetailsView.Listener() {
        @Override
        public void onGoToCardDetailsPressed() {
            mPager.setCurrentItem(CARD_DETAILS_PAGE_INDEX);
        }
    };

    public PaymentForm(@NonNull Context context) {
        this(context, null);
    }

    public PaymentForm(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
            this.attrs=attrs;
        initView();
    }

    private void initView() {
        inflate(mContext, R.layout.payment_form,this);

        TypedArray arr = mContext.obtainStyledAttributes(attrs, R.styleable.PaymentForm);
        mBackgroundHex = arr.getString(R.styleable.PaymentForm_formBackground);
        mHighlightsHex = arr.getString(R.styleable.PaymentForm_highlight);
        arr.recycle();

        mPager = findViewById(R.id.view_pager);
        CustomAdapter customAdapter = new CustomAdapter(mContext);
        customAdapter.setCardDetailsListener(mCardListener);
        customAdapter.setBillingListener(mBillingListener);
        mPager.setAdapter(customAdapter);
        mPager.setEnabled(false);

    }
}
