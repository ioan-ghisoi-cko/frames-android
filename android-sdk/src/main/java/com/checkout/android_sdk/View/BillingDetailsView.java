package com.checkout.android_sdk.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.checkout.android_sdk.Input.AddressOneInput;
import com.checkout.android_sdk.Input.AddressTwoInput;
import com.checkout.android_sdk.Input.CityInput;
import com.checkout.android_sdk.Input.CountryInput;
import com.checkout.android_sdk.Input.NameInput;
import com.checkout.android_sdk.Input.PhoneInput;
import com.checkout.android_sdk.Input.StateInput;
import com.checkout.android_sdk.Input.ZipInput;
import com.checkout.android_sdk.R;
import com.checkout.android_sdk.Store.DataStore;

import java.util.Locale;

/**
 * The controller of the billing details view page
 * <p>
 * This class handles interaction with the custom inputs in the billing details form.
 * The state of the view is handled here, so are action like focus changes, full form
 * validation, listeners, persistence over orientation.
 */
public class BillingDetailsView extends LinearLayout {
    /**
     * The callback used to indicate is the billing details were completed
     * <p>
     * After the user completes their details and the form is valid this callback will
     * be used to communicate to the parent that teh focus needs to change
     */
    public interface Listener {
        void onBillingCompleted();

        void onBillingCanceled();
    }

    /**
     * The callback is used to communicate with the name input
     * <p>
     * The custom {@link NameInput} takes care takes care of the validation and it uses a callback
     * to indicate this controller if there is any error or if the error state needs to
     * be cleared. State is also updates based on the outcome of the input.
     */
    private final NameInput.NameListener mNameListener = new NameInput.NameListener() {
        @Override
        public void onNameInputFinish(String number) {
            mDatastore.setCustomerName(number);
        }

        @Override
        public void clearNameError() {
            mNameLayout.setError(null);
            mNameLayout.setErrorEnabled(false);
        }

    };

    /**
     * The callback is used to communicate with the country input
     * <p>
     * The custom {@link CountryInput} takes care of populating the values in the spinner
     * and will trigger this callback when the user selects a new option. State is update
     * accordingly. Moreover, the phone prefix is added bade on the country selected.
     */
    private final CountryInput.CountryListener mCountryListener = new CountryInput.CountryListener() {
        @Override
        public void onCountryInputFinish(String country, String prefix) {
            mDatastore.setCustomerCountry(country);
            mDatastore.setCustomerPhonePrefix(prefix);
            mPhone.setText(prefix + " ");
            mAddressOne.requestFocus();
            mAddressOne.performClick();
        }
    };

    /**
     * The callback is used to communicate with the address one  input
     * <p>
     * The custom {@link AddressTwoInput} takes care takes care of the validation and it uses a callback
     * to indicate this controller if there is any error or if the error state needs to
     * be cleared. State is also updates based on the outcome of the input.
     */
    private final AddressOneInput.AddressOneListener mAddressOneListener = new AddressOneInput.AddressOneListener() {

        @Override
        public void onAddressOneInputFinish(String address) {
            mDatastore.setCustomerAddress1(address);
        }

        @Override
        public void clearAddressOneError() {
            mAddressOneLayout.setError(null);
            mAddressOneLayout.setErrorEnabled(false);
        }
    };

    /**
     * The callback is used to communicate with the address two input
     * <p>
     * The custom {@link AddressTwoInput} takes care takes care of the validation and it uses a callback
     * to indicate this controller if there is any error or if the error state needs to
     * be cleared. State is also updates based on the outcome of the input.
     */
    private final AddressTwoInput.AddressTwoListener mAddressTwoListener = new AddressTwoInput.AddressTwoListener() {
        @Override
        public void onAddressTwoInputFinish(String address) {
            mDatastore.setCustomerAddress2(address);
        }

        @Override
        public void clearAddressOneError() {
            mAddressTwoLayout.setError(null);
            mAddressTwoLayout.setErrorEnabled(false);
        }
    };

    /**
     * The callback is used to communicate with the city input
     * <p>
     * The custom {@link CityInput} takes care takes care of the validation and it uses a callback
     * to indicate this controller if there is any error or if the error state needs to
     * be cleared. State is also updates based on the outcome of the input.
     */
    private final CityInput.CityListener mCityListener = new CityInput.CityListener() {
        @Override
        public void onCityInputFinish(String city) {
            mDatastore.setCustomerCity(city);
        }

        @Override
        public void clearCityError() {
            mCityLayout.setError(null);
            mCityLayout.setErrorEnabled(false);
        }
    };

    /**
     * The callback is used to communicate with the state input
     * <p>
     * The custom {@link StateInput} takes care takes care of the validation and it uses a callback
     * to indicate this controller if there is any error or if the error state needs to
     * be cleared. State is also updates based on the outcome of the input.
     */
    private final StateInput.StateListener mStateListener = new StateInput.StateListener() {
        @Override
        public void onStateInputFinish(String state) {
            mDatastore.setCustomerState(state);
        }

        @Override
        public void clearCityError() {
            mStateLayout.setError(null);
            mStateLayout.setErrorEnabled(false);
        }
    };

    /**
     * The callback is used to communicate with the zip input
     * <p>
     * The custom {@link ZipInput} takes care takes care of the validation and it uses a callback
     * to indicate this controller if there is any error or if the error state needs to
     * be cleared. State is also updates based on the outcome of the input.
     */
    private final ZipInput.ZipListener mZipListener = new ZipInput.ZipListener() {
        @Override
        public void onZipInputFinish(String zip) {
            mDatastore.setCustomerZipcode(zip);
        }

        @Override
        public void clearZipError() {
            mZipLayout.setError(null);
            mZipLayout.setErrorEnabled(false);
        }
    };

    /**
     * The callback is used to communicate with the phone input
     * <p>
     * The custom {@link PhoneInput} takes care takes care of the validation and it uses a callback
     * to indicate this controller if there is any error or if the error state needs to
     * be cleared. State is also updates based on the outcome of the input.
     */
    private final PhoneInput.PhoneListener mPhoneListener = new PhoneInput.PhoneListener() {
        @Override
        public void onPhoneInputFinish(String phone) {
            mDatastore.setCustomerPhone(phone.replace(mDatastore.getCustomerPhonePrefix(), ""));
        }

        @Override
        public void clearPhoneError() {
            mPhoneLayout.setError(null);
            mPhoneLayout.setErrorEnabled(false);
        }
    };

    private @Nullable
    BillingDetailsView.Listener mListener;
    private @Nullable
    Context mContext;
    private Button mDone;
    private Button mClear;
    private android.support.v7.widget.Toolbar mToolbar;
    private NameInput mName;
    private TextInputLayout mNameLayout;
    private CountryInput mCountryInput;
    private AddressOneInput mAddressOne;
    private AddressTwoInput mAddressTwo;
    private CityInput mCity;
    private StateInput mState;
    private ZipInput mZip;
    private PhoneInput mPhone;
    private DataStore mDatastore = DataStore.getInstance();
    private TextInputLayout mAddressOneLayout;
    private TextInputLayout mAddressTwoLayout;
    private TextInputLayout mCityLayout;
    private TextInputLayout mStateLayout;
    private TextInputLayout mZipLayout;
    private TextInputLayout mPhoneLayout;

    public BillingDetailsView(Context context) {
        this(context, null);
    }

    public BillingDetailsView(@Nullable Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    /**
     * The UI initialisation
     * <p>
     * Used to initialise element and pass callbacks as well as setting up appropriate listeners
     */
    private void init() {
        inflate(mContext, R.layout.blling_details, this);
        mToolbar = findViewById(R.id.my_toolbar);

        setFocusableInTouchMode(true);

        mAddressOneLayout = findViewById(R.id.address_one_input_layout);
        mAddressTwoLayout = findViewById(R.id.address_two_input_layout);
        mCityLayout = findViewById(R.id.city_input_layout);
        mStateLayout = findViewById(R.id.state_input_layout);
        mZipLayout = findViewById(R.id.zipcode_input_layout);
        mPhoneLayout = findViewById(R.id.phone_input_layout);

        // trigger focus change to the card details view on the toolbar back button press
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onBillingCanceled();
                }
            }
        });

        mName = findViewById(R.id.name_input);
        mNameLayout = findViewById(R.id.name_input_layout);
        mName.setNameListener(mNameListener);

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

        mClear = findViewById(R.id.clear_button);

        mDone = findViewById(R.id.done_button);

        // Used to restore state on orientation changes
        repopulateFields();

        // Clear the state and the fields if the user chooses to press the clear button
        mClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
                mDatastore.cleanBillingData();
                if (mListener != null) {
                    mListener.onBillingCanceled();
                }
                mDatastore.setBillingCompleted(false);
            }
        });

        // Is the form is valid indicate the billing was completed using the callback
        // so the billing spinner can be updated adn teh focus can be changes
        mDone.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidForm()) {
                    if (mListener != null) {
                        mDatastore.setBillingCompleted(true);
                        mListener.onBillingCompleted();
                    }
                }
            }
        });

        requestFocus();
    }

    /**
     * Used to restore state on orientation changes
     * <p>
     * The method will repopulate all the card input fields with the latest state they were in
     * if the device orientation changes, and therefore avoiding the text inputs to be cleared.
     */
    private void repopulateFields() {
        // Repopulate name
        mName.setText(mDatastore.getCustomerName());

        // Repopulate country
        Locale[] locale = Locale.getAvailableLocales();
        String country;

        for (Locale loc : locale) {
            country = loc.getDisplayCountry();
            if (loc.getCountry().equals(mDatastore.getCustomerCountry())) {
                mCountryInput.setSelection(((ArrayAdapter<String>) mCountryInput.getAdapter())
                        .getPosition(country));
            }
        }

        // Repopulate address line 1
        mAddressOne.setText(mDatastore.getCustomerAddress1());

        // Repopulate address line 1
        mAddressTwo.setText(mDatastore.getCustomerAddress2());

        // Repopulate city
        mCity.setText(mDatastore.getCustomerCity());

        // Repopulate state
        mState.setText(mDatastore.getCustomerState());

        // Repopulate zip/post code
        mZip.setText(mDatastore.getCustomerZipcode());

        // Repopulate phone
        mPhone.setText(mDatastore.getCustomerPhone());
    }

    /**
     * Used to indicate the validity of the billing details from
     * <p>
     * The method will check if the inputs are valid.
     * This method will also populate the field error accordingly
     *
     * @return boolean abut form validity
     */
    private boolean isValidForm() {
        boolean result = true;

        if (mName.length() < 3) {
            mNameLayout.setError(getResources().getString(R.string.name_error));
            result = false;
        }

        if (mCountryInput.getSelectedItemPosition() == 0) {
            ((TextView) mCountryInput.getSelectedView()).setError(getResources().getString(R.string.country_error));
            result = false;
        }
        if (mAddressOne.length() < 3) {
            mAddressOneLayout.setError(getResources().getString(R.string.address_one_error));
            result = false;
        }

        if (mCity.length() < 2) {
            mCityLayout.setError(getResources().getString(R.string.city_error));
            result = false;
        }

        if (mState.length() < 3) {
            mStateLayout.setError(getResources().getString(R.string.state_error));
            result = false;
        }

        if (mZip.length() < 3) {
            mZipLayout.setError(getResources().getString(R.string.postcode_error));
            result = false;
        }

        if (mPhone.length() < 3) {
            mPhoneLayout.setError(getResources().getString(R.string.phone_error));
            result = false;
        }

        return result;
    }

    /**
     * Used to clear the text from teh fields
     */
    private void resetFields() {
        mName.setText("");
        mNameLayout.setError(null);
        mNameLayout.setErrorEnabled(false);
        mCountryInput.setSelection(0);
        ((TextView) mCountryInput.getSelectedView()).setError(null);
        mAddressOne.setText("");
        mAddressOneLayout.setError(null);
        mAddressOneLayout.setErrorEnabled(false);
        mAddressTwo.setText("");
        mAddressTwoLayout.setError(null);
        mAddressTwoLayout.setErrorEnabled(false);
        mCity.setText("");
        mCityLayout.setError(null);
        mCityLayout.setErrorEnabled(false);
        mState.setText("");
        mStateLayout.setError(null);
        mStateLayout.setErrorEnabled(false);
        mZip.setText("");
        mZipLayout.setError(null);
        mZipLayout.setErrorEnabled(false);
        mPhone.setText("");
        mPhoneLayout.setError(null);
        mPhoneLayout.setErrorEnabled(false);
    }

    // Move to previous view on back button pressed
    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        if(event.getAction() != KeyEvent.ACTION_DOWN) {
            return false;
        }
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            // Prevent back button to trigger the mListener is any is focused
            if (mListener != null &&
                    !mAddressOne.hasFocus() &&
                    !mName.hasFocus() &&
                    !mAddressTwo.hasFocus() &&
                    !mCity.hasFocus() &&
                    !mState.hasFocus() &&
                    !mZip.hasFocus() &&
                    !mPhone.hasFocus()) {
                mListener.onBillingCanceled();
                return true;
            } else {
                requestFocus();
                return false;
            }
        }

        return super.dispatchKeyEventPreIme(event);
    }



    /**
     * Used to set the callback listener for when the card details page is requested
     */
    public void setGoToCardDetailsListener(BillingDetailsView.Listener listener) {
        mListener = listener;
    }

}
