package checkout.checkout_android;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.checkout.android_sdk.CheckoutAPIClient;
import com.checkout.android_sdk.CheckoutAPIClient.OnTokenGenerated;
import com.checkout.android_sdk.PaymentForm;
import com.checkout.android_sdk.PaymentForm.OnSubmitForm;
import com.checkout.android_sdk.Request.CardTokenisationRequest;
import com.checkout.android_sdk.Response.CardTokenisationFail;
import com.checkout.android_sdk.Response.CardTokenisationResponse;
import com.checkout.android_sdk.Utils.CardUtils.Cards;
import com.checkout.android_sdk.Utils.Environment;

public class DemoActivity extends Activity {

    private PaymentForm mPaymentForm;
    private CheckoutAPIClient mCheckoutAPIClient;

    // Callback used when the user completed the form clicked the Pay button
    private final OnSubmitForm mSubmitListener = new OnSubmitForm() {
        @Override
        public void onSubmit(CardTokenisationRequest request) {
            mCheckoutAPIClient.generateToken(request);
        }
    };

    // Callback used for the outcome of the generating a token
    private final OnTokenGenerated mTokenListener = new OnTokenGenerated() {
        @Override
        public void onTokenGenerated(CardTokenisationResponse token) {
            displayMessage("Success!", token.getId());
        }

        @Override
        public void onError(CardTokenisationFail error) {
            displayMessage("Error!", error.getEventId());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        mPaymentForm = findViewById(R.id.checkout_card_form);
        mPaymentForm
                .setSubmitListener(mSubmitListener)
                .setAcceptedCard(new Cards[]{Cards.VISA, Cards.MASTERCARD});

        mCheckoutAPIClient = new CheckoutAPIClient(
                this,
                "pk_test_6e40a700-d563-43cd-89d0-f9bb17d35e73",
                Environment.SANDBOX
        );
        mCheckoutAPIClient.setTokenListener(mTokenListener);

    }

    private void displayMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}