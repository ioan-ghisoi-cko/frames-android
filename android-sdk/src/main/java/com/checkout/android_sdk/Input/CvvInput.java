package com.checkout.android_sdk.Input;

import android.content.Context;
import android.util.AttributeSet;

import com.checkout.android_sdk.Store.DataStore;

/**
 * A custom EdiText with validation and handling of cvv input
 */
public class CvvInput extends DefaultInput {

    private DataStore mDataStore = DataStore.getInstance();

    public CvvInput(Context context) {
        this(context, null);
    }

    public CvvInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {


    }
}
