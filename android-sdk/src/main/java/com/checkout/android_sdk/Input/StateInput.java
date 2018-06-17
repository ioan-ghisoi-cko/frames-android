package com.checkout.android_sdk.Input;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;

/**
 * A custom EdiText with validation and handling of state number input
 */
public class StateInput extends android.support.v7.widget.AppCompatEditText {

    public interface StateListener {
        void onStateInputFinish(String number);
        void clearCityError();
    }

    private @Nullable
    StateInput.StateListener mStateListener;

    public StateInput(Context context) {
        this(context, null);
    }

    public StateInput(Context context, AttributeSet attrs) {
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
                if(mStateListener != null) {
                    mStateListener.onStateInputFinish(s.toString());
                }
            }
        });

        setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (mStateListener != null && hasFocus) {
                    mStateListener.clearCityError();
                }
            }
        });
    }

    /**
     * Used to set the callback listener for when the state input is completed
     */
    public void setStateListener(StateInput.StateListener listener) {
        this.mStateListener = listener;
    }
}
