package com.checkout.android_sdk.Input;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;

/**
 * A custom EdiText with validation and handling of phone number input
 */
public class PhoneInput extends android.support.v7.widget.AppCompatEditText {

    public interface PhoneListener {
        void onPhoneInputFinish(String phone);
        void clearPhoneError();
    }

    private @Nullable
    PhoneInput.PhoneListener mPhoneListener;

    public PhoneInput(Context context) {
        this(context, null);
    }

    public PhoneInput(Context context, AttributeSet attrs) {
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
                if(mPhoneListener != null) {
                    mPhoneListener.onPhoneInputFinish(s.toString());
                }
            }
        });

        setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (mPhoneListener != null && hasFocus) {
                    setSelection(getText().toString().length());
                    mPhoneListener.clearPhoneError();
                }
            }
        });
    }

    /**
     * Used to set the callback listener for when the phone input is completed
     */
    public void setPhoneListener(PhoneInput.PhoneListener listener) {
        this.mPhoneListener = listener;
    }
}