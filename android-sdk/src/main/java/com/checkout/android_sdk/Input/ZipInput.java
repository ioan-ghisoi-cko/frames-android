package com.checkout.android_sdk.Input;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;

/**
 * A custom EdiText with validation and handling of postcode/zipcode input
 */
public class ZipInput extends android.support.v7.widget.AppCompatEditText {

    public interface ZipListener {
        void onZipInputFinish(String zip);
        void clearZipError();
    }

    private @Nullable
    ZipInput.ZipListener mZipListener;

    public ZipInput(Context context) {
        this(context, null);
    }

    public ZipInput(Context context, AttributeSet attrs) {
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
                if(mZipListener != null) {
                    mZipListener.onZipInputFinish(s.toString());
                }
            }
        });

        setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (mZipListener != null && hasFocus) {
                    mZipListener.clearZipError();
                }
            }
        });
    }

    /**
     * Used to set the callback listener for when the zip input is completed
     */
    public void setZipListener(ZipInput.ZipListener listener) {
        this.mZipListener = listener;
    }
}
