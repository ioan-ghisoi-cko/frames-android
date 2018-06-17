package com.checkout.android_sdk.Input;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;

/**
 * A custom EdiText with validation and handling of address input
 */
public class AddressTwoInput extends android.support.v7.widget.AppCompatEditText {

    public interface AddressTwoListener {
        void onAddressTwoInputFinish(String number);
        void clearAddressOneError();
    }

    private @Nullable
    AddressTwoInput.AddressTwoListener mAddressTwoListener;

    public AddressTwoInput(Context context) {
        this(context, null);
    }

    public AddressTwoInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * The UI initialisation
     * <p>
     * Used to initialise element as well as setting up appropriate listeners
     */
    private void init() {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // Save state
                if(mAddressTwoListener != null) {
                    mAddressTwoListener.onAddressTwoInputFinish(s.toString());
                }
            }
        });

        // Clear error on focus gain
        setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (mAddressTwoListener != null && hasFocus) {
                    mAddressTwoListener.clearAddressOneError();
                }
            }
        });
    }

    /**
     * Used to set the callback listener for when the address input is completed
     */
    public void setAddressTwoListenerListener(AddressTwoInput.AddressTwoListener listener) {
        this.mAddressTwoListener = listener;
    }
}