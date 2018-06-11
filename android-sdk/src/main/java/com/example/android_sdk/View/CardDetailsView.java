package com.example.android_sdk.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatSpinner;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_sdk.Input.BillingInput;
import com.example.android_sdk.Input.CardInput;
import com.example.android_sdk.Input.CvvInput;
import com.example.android_sdk.Input.MonthInput;
import com.example.android_sdk.Input.YearInput;
import com.example.android_sdk.R;
import com.example.android_sdk.Store.DataStore;
import com.example.android_sdk.Utils.CardUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CardDetailsView extends LinearLayout {

    DataStore mDataStore = DataStore.getInstance();

    public interface GoToBillingListener {
        void onGoToBilingPressed();
    }

    private final CardInput.Listener mCardInputListener = new CardInput.Listener() {
        @Override
        public void onCardInputFinish(String number) {
            mDataStore.setValidCardNumber(true);
        }

        @Override
        public void onCardError() {
            mCardLayout.setError(getResources().getString(R.string.card_error));
            mDataStore.setValidCardNumber(false);
        }

        @Override
        public void onClearCardError() {
            mCardLayout.setError(null);
        }
    };

    private final MonthInput.MonthListener mMonthInputListener = new MonthInput.MonthListener() {

        @Override
        public void onMonthInputFinish(String month) {
            mDataStore.setValidCardMonth(true);
        }
    };

    private final YearInput.YearListener mYearInputListener = new YearInput.YearListener() {

        @Override
        public void onYearInputFinish(String year) {
            mDataStore.setValidCardYear(true);
        }
    };

    private final CvvInput.CvvListener mCvvInputListener = new CvvInput.CvvListener() {

        @Override
        public void onCvvInputFinish(String cvv) {
            mDataStore.setValidCardCvv(true);
        }
    };

    private final BillingInput.BillingListener mBillingInputListener = new BillingInput.BillingListener() {

        @Override
        public void onGoToBilling() {
            if(mGotoBillingListener != null) {
                mGotoBillingListener.onGoToBilingPressed();
            }
        }
    };

    private @Nullable
    CardDetailsView.GoToBillingListener mGotoBillingListener;
    private Context mContext;

    private CardInput mCardInput;
    private MonthInput mMonthInput;
    private YearInput mYearInput;
    private BillingInput mGoToBilling;
    private CvvInput mCvvInput;
    private TextInputLayout mCardLayout;
    final CardUtils mCardUtils = new CardUtils();
    private Button mPayButton;
    android.support.v7.widget.Toolbar mToolbar;

    public CardDetailsView(Context context) {
        this(context, null);
    }

    public CardDetailsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public void setGoToBillingListener(GoToBillingListener listener) {
        mGotoBillingListener = listener;
    }

    private void repopulateField() {

        // Repopulate card number
        if (DataStore.getInstance().getCardNumber() != null) {
            if (mDataStore.getCardNumber() != null) {
                // Get card type based on the saved card number
                CardUtils.Cards cardType = mCardUtils.getType(DataStore.getInstance().getCardNumber());
                // Set the CardInput maximum length based on the type of card
                mCardInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(cardType.maxCardLength)});
                // Set the CardInput icon based on the type of card
                mCardInput.setCardTypeIcon(cardType.name());
                // Check if the card is valid
                mCardInput.checkIfCardIsValid(mDataStore.getCardNumber(), cardType);
                // Update the card field with the last input value
                String formattedCard = mCardUtils.getFormattedCardNumber(mDataStore.getCardNumber());
                mCardInput.setText(formattedCard);
                // Set the cursor to the end of the input
                mCardInput.setSelection(formattedCard.length());
            }
        }

        //Repopulate card month
        if (DataStore.getInstance().getCardMonth() != null) {
            MonthInput.Months[] months = MonthInput.Months.values();
            mMonthInput.setSelection(months[Integer.parseInt(DataStore.getInstance().getCardMonth()) - 1].number - 1);
        }

        //Repopulate card year
        if (DataStore.getInstance().getCardYear() != null) {
            try {
                mYearInput.setSelection(((ArrayAdapter<String>)mYearInput.getAdapter()).getPosition(DataStore.getInstance().getCardYear()));
            } catch (Exception e) {
                //null
            }
        }

        //Repopulate card cvv
        if (DataStore.getInstance().getCardCvv() != null) {
            // Update the cvv field with the last input value
            mCvvInput.setText(mDataStore.getCardCvv());
        }
    }

    private void init() {
        inflate(mContext, R.layout.card_details, this);

        mToolbar = findViewById(R.id.my_toolbar);

        mCardInput = findViewById(R.id.card_input);
        mCardLayout = findViewById(R.id.card_input_layout);
        mCardInput.setCardListener(mCardInputListener);

        mMonthInput = findViewById(R.id.month_input);
        mMonthInput.setMonthListener(mMonthInputListener);

        mYearInput = findViewById(R.id.year_input);
        mYearInput.setYearListener(mYearInputListener);

        mCvvInput = findViewById(R.id.cvv_input);
        mCvvInput.setCvvListener(mCvvInputListener);

        mGoToBilling = findViewById(R.id.go_to_billing);
        mGoToBilling.setBillingListener(mBillingInputListener);


        mPayButton = findViewById(R.id.pay_button);

        mPayButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isValidDate();
                if( mDataStore.isValidCardNumber() &&
                        mDataStore.isValidCardMonth() &&
                        mDataStore.isValidCardYear() &&
                        mDataStore.isValidCardCvv()) {
                    Toast.makeText(mContext, "VALID",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        // Restore state in case the orientation changes
        repopulateField();
    }

    private boolean isValidDate() {

        Calendar calendar = Calendar.getInstance();
        int calendarYear = calendar.get(Calendar.YEAR);
        int calendarMonth = calendar.get(Calendar.MONTH);

        if(mDataStore.getCardYear()  != null && Integer.valueOf(mDataStore.getCardYear()) == calendarYear &&
                (mMonthInput.getSelectedItemPosition()) < calendarMonth) {
            mDataStore.setValidCardMonth(false);
            ((TextView)mMonthInput.getSelectedView()).setError("Error message");
            return false;
        }
        return true;
    }

    public void updateBillingSpinner(String address) {
        if(address.length() > 6) {
            List<String> billingElement = new ArrayList<>();

            billingElement.add(address);
            billingElement.add("Edit");

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(mContext,
                    android.R.layout.simple_spinner_item, billingElement);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mGoToBilling.setAdapter(dataAdapter);
            mGoToBilling.setSelection(0);
        } else {
            List<String> billingElement = new ArrayList<>();

            billingElement.add("SELECT");
            billingElement.add("  + ADD");

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(mContext,
                    android.R.layout.simple_spinner_item, billingElement);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mGoToBilling.setAdapter(dataAdapter);
            mGoToBilling.setSelection(0);
        }
    }



}
