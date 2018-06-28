package checkout.com.demo.Demos;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.checkout.android_sdk.CheckoutAPIClient;
import com.checkout.android_sdk.Models.BillingModel;
import com.checkout.android_sdk.Models.PhoneModel;
import com.checkout.android_sdk.Request.CardTokenisationRequest;
import com.checkout.android_sdk.Response.CardTokenisationFail;
import com.checkout.android_sdk.Response.CardTokenisationResponse;
import com.checkout.android_sdk.Utils.Environment;

import checkout.com.demo.R;

public class HeadlessDemo extends Activity {

    private CheckoutAPIClient mCheckoutAPIClient; // include the module

    private final CheckoutAPIClient.OnTokenGenerated mTokenListener = new CheckoutAPIClient.OnTokenGenerated() {

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
        setContentView(R.layout.headless_activity);


        mCheckoutAPIClient = new CheckoutAPIClient(
                this,
                "pk_test_6e40a700-d563-43cd-89d0-f9bb17d35e73",
                Environment.SANDBOX
        );
        mCheckoutAPIClient.setTokenListener(mTokenListener); // pass the callback


        // Pass the paylod and generate the token
        mCheckoutAPIClient.generateToken(
                new CardTokenisationRequest(
                        "4242424242424242",
                        "name",
                        "06",
                        "18",
                        "100",
                        new BillingModel(
                                "address line 1",
                                "address line 2",
                                "postcode",
                                "UK",
                                "city",
                                "state",
                                new PhoneModel(
                                        "+44",
                                        "07123456789"
                                )
                        )
                )
        );

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
