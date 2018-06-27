package com.checkout.android_sdk.Input;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * A custom EdiText with validation and handling of address input
 */
public class AddressOneInput extends android.support.v7.widget.AppCompatEditText {

    public interface AddressOneListener {
        void onAddressOneInputFinish(String number);

        void clearAddressOneError();
    }

    private @Nullable
    AddressOneInput.AddressOneListener mAddressOneListener;
    private Context mContext;

    public AddressOneInput(Context context) {
        this(context, null);
    }

    public AddressOneInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Save state
                if (mAddressOneListener != null) {
                    mAddressOneListener.onAddressOneInputFinish(s.toString());
                }
            }
        });

        setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    performClick();
                    // Clear error if the user starts typing
                    if (mAddressOneListener != null) {
                        mAddressOneListener.clearAddressOneError();
                    }
                    @Nullable InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
                    }
                }
            }
        });
    }

    public void setAddressOneListener(AddressOneInput.AddressOneListener listener) {
        this.mAddressOneListener = listener;
    }
}
