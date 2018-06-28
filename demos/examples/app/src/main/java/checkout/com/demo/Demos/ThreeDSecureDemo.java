package checkout.com.demo.Demos;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.checkout.android_sdk.CheckoutAPIClient;
import com.checkout.android_sdk.PaymentForm;
import com.checkout.android_sdk.Request.CardTokenisationRequest;
import com.checkout.android_sdk.Response.CardTokenisationFail;
import com.checkout.android_sdk.Response.CardTokenisationResponse;
import com.checkout.android_sdk.Utils.CardUtils;
import com.checkout.android_sdk.Utils.Environment;

import org.json.JSONException;
import org.json.JSONObject;

import checkout.com.demo.R;

public class ThreeDSecureDemo extends Activity {

    private final String YOU_PUBLIC_KEY = "pk_test_6e40a700-d563-43cd-89d0-f9bb17d35e73";

    private final String YOUR_API_CHARGE_3D_URL = "https://backend-demo-frames.herokuapp.com/charge3D";
    private final String YOUR_REDIRECTION_URL = "http://google.com/success";
    private final String YOUR_REDIRECTION_FAIL_URL = "http://google.com/fail";

    private PaymentForm mPaymentForm;
    private CheckoutAPIClient mCheckoutAPIClient;
    private ProgressDialog progress = null;

    PaymentForm.OnSubmitForm mSubmitListener = new PaymentForm.OnSubmitForm() {
        @Override
        public void onSubmit(CardTokenisationRequest request) {
            // Display a loader until the card is tokenised
            progress = new ProgressDialog(ThreeDSecureDemo.this);
            progress.setTitle("Loading");
            progress.setMessage("loading...");
            progress.setCancelable(false);
            progress.show();
            mCheckoutAPIClient.generateToken(request); // send the request to generate the token
        }
    };

    CheckoutAPIClient.OnTokenGenerated mTokenListener = new CheckoutAPIClient.OnTokenGenerated() {
        @Override
        public void onTokenGenerated(CardTokenisationResponse token) {
            progress.dismiss();
            try {
                callApiToCharge(token.getId());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(CardTokenisationFail error) {
            progress.dismiss();
        }
    };

    PaymentForm.On3DSFinished m3DSecureListener =
            new PaymentForm.On3DSFinished() {
                @Override
                public void onSuccess(String token) {
                    displayMessage("Success!", token);
                    mPaymentForm.setVisibility(View.GONE);
                }

                @Override
                public void onError(String errorMessage) {
                    displayMessage("Error!", errorMessage);
                    mPaymentForm.setVisibility(View.GONE);
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.three_d_activity);

        // initialise the payment from
        mPaymentForm = findViewById(R.id.checkout_card_form);
        mPaymentForm
                .setSubmitListener(mSubmitListener) // set the callback for the form submission
                .set3DSListener(m3DSecureListener)  // set the callback for 3D Secure
                .setAcceptedCard(new CardUtils.Cards[]{CardUtils.Cards.VISA, CardUtils.Cards.MASTERCARD})
                .includeBilling(false);

        // initialise the api client
        mCheckoutAPIClient = new CheckoutAPIClient(
                this, // context
                YOU_PUBLIC_KEY, // your public key
                Environment.SANDBOX
        );
        mCheckoutAPIClient.setTokenListener(mTokenListener); // set the callback for tokenisation
    }

    /**
     * This Function perform a request to your backend with the card token generated. You backend
     * will perform a charge request with charge = 2 and it will return a 3D Secure URL. We will
     * then use the URL and your Redirection URLs to handle the flow.
     * <p>
     * It is important to understand that the Redirection URL can also be set dynamically in the
     * charge request so please make sure you are handling this situation.
     */
    private void callApiToCharge(String token) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject json = new JSONObject();
        json.put("token", token);

        JsonObjectRequest portRequest = new JsonObjectRequest(Request.Method.POST, YOUR_API_CHARGE_3D_URL, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            mPaymentForm.handle3DS(
                                    // Get teh 3D Secure URL from your API
                                    response.getString("url"),
                                    YOUR_REDIRECTION_URL,     // the Redirection URL
                                    YOUR_REDIRECTION_FAIL_URL // the Redirection Fail URL
                            );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse networkResponse = error.networkResponse;
                        if (networkResponse != null && networkResponse.data != null) {

                        }
                    }
                }
        );
        queue.add(portRequest);
    }

    /**
     * This Function is used to display progress dialog
     */
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
