package com.checkout.android_sdk.Input;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * A custom EdiText with validation and handling of address input
 */
public class AddressOneInput extends DefaultInput {

    public AddressOneInput(Context context) {
        this(context, null);
    }

    public AddressOneInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    performClick();
                    @Nullable InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
                    }
                }
            }
        });
    }
}
