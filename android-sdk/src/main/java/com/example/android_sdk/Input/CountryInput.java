package com.example.android_sdk.Input;

import android.content.Context;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.android_sdk.Utils.PhoneUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CountryInput extends android.support.v7.widget.AppCompatSpinner {

    public interface CountryListener {
        void onCountryInputFinish(String country, String prefix);
    }

    private @Nullable
    CountryInput.CountryListener mCountryListener;
    private Context mContext;
    private PhoneUtils phoneUtils = new PhoneUtils();

    public CountryInput(Context context) {
        this(context, 0);
    }

    public CountryInput(Context context, int mode) {
        this(context, null);
    }

    public CountryInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {

        setFocusable(true);
        setFocusableInTouchMode(true);

        populateSpinner();

        setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mCountryListener != null && getSelectedItemPosition() > 0) {
                    Locale[] locale = Locale.getAvailableLocales();
                    String country;

                    for( Locale loc : locale ){
                        country = loc.getDisplayCountry();
                        if(country.equals(getSelectedItem().toString())){
                            mCountryListener.onCountryInputFinish(loc.getCountry(), phoneUtils.getPrefix(loc.getCountry()));
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

        // Remove extra padding left
        setPadding(0, this.getPaddingTop(), this.getPaddingRight(), this.getPaddingBottom());
    }

    private void populateSpinner() {
        Locale[] locale = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<>();
        String country;
        countries.add(" Select Country:");

        for( Locale loc : locale ){
            country = loc.getDisplayCountry();
            if( country.length() > 0 && !countries.contains(country) ){
                countries.add( country );
            }
        }
        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext,android.R.layout.simple_spinner_dropdown_item, countries);
        setAdapter(adapter);
    }

    public void setCountryListener(CountryInput.CountryListener listener) {
        this.mCountryListener = listener;
    }
}
