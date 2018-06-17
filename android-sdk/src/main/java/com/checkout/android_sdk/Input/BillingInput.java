package com.checkout.android_sdk.Input;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;

import com.checkout.android_sdk.Store.DataStore;

import java.util.ArrayList;
import java.util.List;

/**
 * A custom Spinner with handling of billing input
 */
public class BillingInput extends android.support.v7.widget.AppCompatSpinner {

    public interface BillingListener {
        void onGoToBilling();
    }

    private @Nullable
    BillingInput.BillingListener mBillingListener;
    private Context mContext;
    private DataStore mDatastore = DataStore.getInstance();

    public BillingInput(Context context) {
        this(context, null);
    }

    public BillingInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    /**
     * The UI initialisation
     * <p>
     * Used to initialise element as well as setting up appropriate listeners
     */
    private void init() {

        // Options needed for focus context switching
        setFocusable(true);
        setFocusableInTouchMode(true);

        // Populate the spinner values
        populateSpinner();

        setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    performClick();
                    @Nullable InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
            }
        });
    }

    /**
     * This method populates the spinner with some default values
     */
    private void populateSpinner() {
        List<String> billingElement = new ArrayList<>();

        billingElement.add("SELECT");
        billingElement.add("  + ADD");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(mContext,
                android.R.layout.simple_spinner_item, billingElement);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setAdapter(dataAdapter);
    }

    /**
     * This method is used to redirect the user tot he billing page is they chose the ADD option
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (mBillingListener != null && this.getSelectedItemPosition() == 1) {
            mBillingListener.onGoToBilling();
        }
        super.onLayout(changed, l, t, r, b);
    }

    /**
     * Used to set the callback listener for when the address input is completed
     */
    public void setBillingListener(BillingInput.BillingListener listener) {
        this.mBillingListener = listener;
    }
}
