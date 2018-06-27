package checkout.checkout_android;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.checkout.android_sdk.PaymentForm;
import com.checkout.android_sdk.Response.CardTokenisationFail;
import com.checkout.android_sdk.Response.CardTokenisationResponse;
import com.checkout.android_sdk.CheckoutAPIClient.OnTokenGenerated;

public class DemoActivity extends Activity {

    private PaymentForm mPaymentForm;

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
                .setKey("pk_test_6e40a700-d563-43cd-89d0-f9bb17d35e73")
                .setTokenListener(mTokenListener);

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