package com.checkout.android_sdk.Input;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;

import com.checkout.android_sdk.Store.DataStore;

/**
 * A custom EdiText with validation and handling of city input
 */
public class CityInput extends android.support.v7.widget.AppCompatEditText {

    public interface CityListener {
        void onCityInputFinish(String number);
        void clearCityError();
    }

    private @Nullable
    CityInput.CityListener mCityListener;
    private Context mContext;
    private DataStore mDataStore = DataStore.getInstance();

    public CityInput(Context context) {
        this(context, null);
    }

    public CityInput(Context context, AttributeSet attrs) {
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
                if(mCityListener != null) {
                    mCityListener.onCityInputFinish(s.toString());
                }
            }
        });

        setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (mCityListener != null && hasFocus) {
                    mCityListener.clearCityError();
                }
            }
        });
    }

    /**
     * Used to set the callback listener for when the city input is completed
     */
    public void setCityListener(CityInput.CityListener listener) {
        this.mCityListener = listener;
    }
}
