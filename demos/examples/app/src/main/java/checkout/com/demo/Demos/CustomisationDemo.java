package checkout.com.demo.Demos;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.checkout.android_sdk.CheckoutAPIClient;
import com.checkout.android_sdk.PaymentForm;
import com.checkout.android_sdk.Request.CardTokenisationRequest;
import com.checkout.android_sdk.Response.CardTokenisationFail;
import com.checkout.android_sdk.Response.CardTokenisationResponse;
import com.checkout.android_sdk.Utils.CardUtils.Cards;
import com.checkout.android_sdk.Utils.Environment;

import checkout.com.demo.R;

public class CustomisationDemo extends Activity {

    private PaymentForm mPaymentForm; // include the payment form
    private CheckoutAPIClient mCheckoutAPIClient; // include the API client
    private ProgressDialog progress = null;

    PaymentForm.OnSubmitForm mSubmitListener = new PaymentForm.OnSubmitForm() {
        @Override
        public void onSubmit(CardTokenisationRequest request) {
            // Display a loader until the card is tokenised
            progress = new ProgressDialog(CustomisationDemo.this);
            progress.setTitle("Loading");
            progress.setMessage("loading...");
            progress.setCancelable(false);
            progress.show();
            mCheckoutAPIClient.generateToken(request); // send the request to generate the token
        }
    };

    private final CheckoutAPIClient.OnTokenGenerated mTokenListener = new CheckoutAPIClient.OnTokenGenerated() {

        @Override
        public void onTokenGenerated(CardTokenisationResponse token) {
            progress.dismiss();
            displayMessage("Success!", token.getId());
        }

        @Override
        public void onError(CardTokenisationFail error) {
            progress.dismiss();
            displayMessage("Error!", error.getEventId());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customisation_activity);

        // initialise the payment from
        mPaymentForm = findViewById(R.id.checkout_card_form);
        mPaymentForm
                .setSubmitListener(mSubmitListener) // set the callback for the form submission
                .setAcceptedCard(new Cards[]{Cards.VISA, Cards.MASTERCARD})
                .includeBilling(false);

        // initialise the api client
        mCheckoutAPIClient = new CheckoutAPIClient(
                this, // context
                "pk_test_6e40a700-d563-43cd-89d0-f9bb17d35e73", // your public key
                Environment.SANDBOX
        );
        mCheckoutAPIClient.setTokenListener(mTokenListener); // set the callback for tokenisation
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