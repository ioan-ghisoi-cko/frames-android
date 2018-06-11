package com.example.android_sdk.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.android_sdk.Input.AddressOneInput;
import com.example.android_sdk.Input.AddressTwoInput;
import com.example.android_sdk.Input.CityInput;
import com.example.android_sdk.Input.CountryInput;
import com.example.android_sdk.Input.PhoneInput;
import com.example.android_sdk.Input.StateInput;
import com.example.android_sdk.Input.ZipInput;
import com.example.android_sdk.R;
import com.example.android_sdk.Store.DataStore;

public class BillingDetailsView extends LinearLayout {

    public interface Listener {
        void onBillingCompleted(String address);
        void onBillingCanceled();
    }

    private final CountryInput.CountryListener mCountryListener = new CountryInput.CountryListener() {

        @Override
        public void onCountryInputFinish(String country) {
            mDatastore.setCustomerCountry(country);
            mAddressOne.requestFocus();
            mAddressOne.performClick();
        }
    };

    private final AddressOneInput.AddressOneListener mAddressOneListener = new AddressOneInput.AddressOneListener() {

        @Override
        public void onAddressOneInputFinish(String address) {
            mDatastore.setCustomerAddress1(address);
        }
    };

    private final AddressTwoInput.AddressTwoListener mAddressTwoListener = new AddressTwoInput.AddressTwoListener() {
        @Override
        public void onAddressTwoInputFinish(String address) {
            mDatastore.setCustomerAddress2(address);
        }
    };

    private final CityInput.CityListener mCityListener = new CityInput.CityListener() {
        @Override
        public void onCityInputFinish(String city) {
            mDatastore.setCustomerCity(city);
        }
    };

    private final StateInput.StateListener mStateListener = new StateInput.StateListener() {
        @Override
        public void onStateInputFinish(String state) {
            mDatastore.setCustomerState(state);
        }
    };

    private final ZipInput.ZipListener mZipListener = new ZipInput.ZipListener() {
        @Override
        public void onZipInputFinish(String zip) {
            mDatastore.setCustomerZipcode(zip);
        }
    };

    private final PhoneInput.PhoneListener mPhoneListener = new PhoneInput.PhoneListener() {
        @Override
        public void onPhoneInputFinish(String phone) {
            mDatastore.setCustomerPhone(phone);
        }
    };

    private @Nullable
    BillingDetailsView.Listener mListener;
    private @Nullable
    Context mContext;
    Button mDone;
    android.support.v7.widget.Toolbar mToolbar;
    private CountryInput mCountryInput;
    private AddressOneInput mAddressOne;
    private AddressTwoInput mAddressTwo;
    private CityInput mCity;
    private StateInput mState;
    private ZipInput mZip;
    private PhoneInput mPhone;
    private DataStore mDatastore = DataStore.getInstance();


    public BillingDetailsView(Context context) {
        this(context, null);
    }

    public BillingDetailsView(@Nullable Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        inflate(mContext, R.layout.blling_details, this);
        mToolbar = findViewById(R.id.my_toolbar);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null) {
                    mDatastore.cleanBillingData();
                    mListener.onBillingCanceled();
                }
            }
        });

        mCountryInput = findViewById(R.id.country_input);
        mCountryInput.setCountryListener(mCountryListener);

        mAddressOne = findViewById(R.id.address_one_input);
        mAddressOne.setAddressOneListener(mAddressOneListener);

        mAddressTwo = findViewById(R.id.address_two_input);
        mAddressTwo.setAddressTwoListenerListener(mAddressTwoListener);

        mCity = findViewById(R.id.city_input);
        mCity.setCityListener(mCityListener);

        mState = findViewById(R.id.state_input);
        mState.setStateListener(mStateListener);

        mZip = findViewById(R.id.zipcode_input);
        mZip.setZipListener(mZipListener);

        mPhone = findViewById(R.id.phone_input);
        mPhone.setPhoneListener(mPhoneListener);

        mDone = findViewById(R.id.done_button);

        mDone.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null) {
                    mListener.onBillingCompleted(mDatastore.getCustomerAddress1() +
                            ", " + mDatastore.getCustomerCity());
                }
            }
        });

    }

    public void setGoToCardDetailsListener(BillingDetailsView.Listener listener) {
        mListener = listener;
    }

}
