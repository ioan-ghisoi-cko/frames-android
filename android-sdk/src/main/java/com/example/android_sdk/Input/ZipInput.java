package com.example.android_sdk.Input;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.example.android_sdk.Store.DataStore;

public class ZipInput extends android.support.v7.widget.AppCompatEditText {

    public interface ZipListener {
        void onZipInputFinish(String zip);
    }

    private @Nullable
    ZipInput.ZipListener mZipListener;
    private Context mContext;
    private DataStore mDataStore = DataStore.getInstance();

    public ZipInput(Context context) {
        this(context, null);
    }

    public ZipInput(Context context, AttributeSet attrs) {
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
                if(mZipListener != null) {
                    mZipListener.onZipInputFinish(s.toString());
                }
            }
        });
    }

    public void setZipListener(ZipInput.ZipListener listener) {
        this.mZipListener = listener;
    }
}
