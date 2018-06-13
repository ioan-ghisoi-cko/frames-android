package com.example.android_sdk.Input;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.android_sdk.Store.DataStore;

public class AddressOneInput  extends android.support.v7.widget.AppCompatEditText {

    public interface AddressOneListener {
        void onAddressOneInputFinish(String number);
        void clearAddressOneError();
    }

    private @Nullable
    AddressOneInput.AddressOneListener mAddressOneListener;
    private Context mContext;
    private DataStore mDataStore = DataStore.getInstance();

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
                // Clear error if the user starts typing
                if (mAddressOneListener != null) {
                    mAddressOneListener.clearAddressOneError();
                }
                // Save state
                if(mAddressOneListener != null) {
                    mAddressOneListener.onAddressOneInputFinish(s.toString());
                }
            }
        });

        setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    performClick();
                    @Nullable InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(imm != null) {
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
