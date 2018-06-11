package com.example.android_sdk.Input;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.android_sdk.Store.DataStore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class YearInput extends android.support.v7.widget.AppCompatSpinner {

    public interface YearListener {
        void onYearInputFinish(String month);
    }

    private @Nullable
    YearInput.YearListener mYearInputListener;
    Context mContext;
    private DataStore mDatastore = DataStore.getInstance();

    public YearInput(Context context) {
        this(context, 0);
    }

    public YearInput(Context context, int mode) {
        this(context, null);
    }


    public YearInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        // Options needed for focus context switching
        setFocusable(true);
        setFocusableInTouchMode(true);

        populateYears();

        setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    performClick();
                    @Nullable InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
            }
        });

        setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(mYearInputListener != null) {
                    mYearInputListener.onYearInputFinish(getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void populateYears() {

        List<String> yearElements = new ArrayList<>();

        for(int i = Calendar.getInstance().get(Calendar.YEAR); i < Calendar.getInstance().get(Calendar.YEAR) + 15; i++) {
            yearElements.add(String.valueOf(i));
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(mContext,
                android.R.layout.simple_spinner_item, yearElements);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setAdapter(dataAdapter);
    }

    public void setYearListener(YearInput.YearListener listener) {
        this.mYearInputListener = listener;
    }
}
