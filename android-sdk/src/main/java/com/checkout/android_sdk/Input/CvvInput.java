package com.checkout.android_sdk.Input;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;

import com.checkout.android_sdk.Store.DataStore;

/**
 * A custom EdiText with validation and handling of cvv input
 */
public class CvvInput extends android.support.v7.widget.AppCompatEditText {

    public interface CvvListener {
        void onCvvInputFinish(String number);
        void onCvvError();
        void onClearCvvError();
    }

    private @Nullable
    CvvInput.CvvListener mCvvInputListener;
    private Context mContext;
    private DataStore mDataStore = DataStore.getInstance();

    public CvvInput(Context context) {
        this(context, null);
    }

    public CvvInput(Context context, AttributeSet attrs) {
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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // Save state
                mDataStore.setCardCvv(s.toString());
                if (mCvvInputListener != null && s.toString().length() == mDataStore.getCvvLength()) {
                    mCvvInputListener.onCvvInputFinish(s.toString());
                }
            }
        });

        setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (mCvvInputListener != null && hasFocus) {
                    setFilters(new InputFilter[]{new InputFilter.LengthFilter(mDataStore.getCvvLength())});
                    mCvvInputListener.onClearCvvError();
                } else {
                    if(mCvvInputListener != null && getText().length() != mDataStore.getCvvLength()) {
                        mCvvInputListener.onCvvError();
                    }
                }
            }
        });

    }

    /**
     * Used to set the callback listener for when the cvv input is completed
     */
    public void setCvvListener(CvvInput.CvvListener listener) {
        this.mCvvInputListener = listener;
    }
}
