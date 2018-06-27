package com.checkout.android_sdk.Input;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;

public class DefaultInput extends android.support.v7.widget.AppCompatEditText {
    public interface Listener {
        void onInputFinish(String value);

        void clearInputError();
    }

    private @Nullable
    DefaultInput.Listener mListener;
    Context mContext;

    public DefaultInput(Context context) {
        this(context, 0);
    }

    public DefaultInput(Context context, int mode) {
        this(context, null);
    }


    public DefaultInput(Context context, AttributeSet attrs) {
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
                if (mListener != null) {
                    mListener.onInputFinish(s.toString());
                    mListener.clearInputError();
                }
            }
        });

        setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (mListener != null && hasFocus) {
                    mListener.clearInputError();
                }
            }
        });
    }

    /**
     * Used to set the callback listener for when the zip input is completed
     */
    public void setListener(DefaultInput.Listener listener) {
        this.mListener = listener;
    }
}
