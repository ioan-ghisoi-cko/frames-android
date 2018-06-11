package com.example.android_sdk.View;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toolbar;

import com.example.android_sdk.Input.AddressOneInput;
import com.example.android_sdk.Input.CountryInput;
import com.example.android_sdk.R;
import com.example.android_sdk.Store.DataStore;

public class BillingDetailsView extends LinearLayout {

    public interface Listener {
        void onGoToCardDetailsPressed();
    }

    private final AddressOneInput.AddressOneListener mAddressOneListener = new AddressOneInput.AddressOneListener() {

        @Override
        public void onAddressOneInputFinish(String address) {
            mDatastore.setCustomerAddress1(address);
        }
    };


    private @Nullable
    BillingDetailsView.Listener mListener;
    Context mContext;
    Button mBack;
    android.support.v7.widget.Toolbar mToolbar;
    private CountryInput mCountryInput;
    private AddressOneInput mAddressOne;
    private DataStore mDatastore = DataStore.getInstance();


    public BillingDetailsView(Context context) {
        this(context, null);
    }

    public BillingDetailsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        inflate(mContext, R.layout.blling_details, this);

        mBack = findViewById(R.id.back);
        mToolbar = findViewById(R.id.my_toolbar);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null) {
                    mListener.onGoToCardDetailsPressed();
                }
            }
        });

        mCountryInput = findViewById(R.id.country_input);

        mAddressOne = findViewById(R.id.address_one_input);
        mAddressOne.setAddressOneListener(mAddressOneListener);

    }

    public void setGoToCardDetailsListener(BillingDetailsView.Listener listener) {
        mListener = listener;
    }
}
