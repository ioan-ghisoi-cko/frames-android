package com.checkout.android_sdk.Input;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

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
