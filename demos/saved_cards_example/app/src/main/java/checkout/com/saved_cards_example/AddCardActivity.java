package checkout.com.saved_cards_example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

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

public class AddCardActivity extends Activity {

    // This is the URL of your backend endpoint that will perform the Zero Dollar Transaction request
    private final String YOUR_API_ZERO_DOLLAR_URL = "https://frames-android-backend-example.herokuapp.com/zerodollar";
    private final String PUBLIC_KEY = "pk_test_6e40a700-d563-43cd-89d0-f9bb17d35e73";


    private PaymentForm mPaymentForm; // include the payment form
    private CheckoutAPIClient mCheckoutAPIClient; // include the API client

    // The callback used to communicate when the form was submitted
    PaymentForm.OnSubmitForm mSubmitListener = new PaymentForm.OnSubmitForm() {
        @Override
        public void onSubmit(CardTokenisationRequest request) {
            mCheckoutAPIClient.generateToken(request); // send the request to generate the token
        }
    };

    // The callback used to communicate when the card token was generated
    CheckoutAPIClient.OnTokenGenerated mTokenListener = new CheckoutAPIClient.OnTokenGenerated() {
        @Override
        public void onTokenGenerated(CardTokenisationResponse token) {
            try {
                callApiToSaveCard(token.getId());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onError(CardTokenisationFail error) {
            // your error
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_card_activity);

        // initialise the payment from
        mPaymentForm = findViewById(R.id.checkout_card_form);
        mPaymentForm
                .setAcceptedCard(new CardUtils.Cards[]{CardUtils.Cards.VISA, CardUtils.Cards.MASTERCARD})
                .setSubmitListener(mSubmitListener); // set the callback for the form submission

        // initialise the api client
        mCheckoutAPIClient = new CheckoutAPIClient(
                this, // context
                PUBLIC_KEY,  // your public key
                Environment.SANDBOX
        );
        mCheckoutAPIClient.setTokenListener(mTokenListener); // set the callback for tokenisation
    }

    /**
     * This function will call a backend server that will perform a Zero Dollar Transaction that
     * will create the customer if it is not already created and it will associate the specific
     * card with that customer.
     *
     * After the API call is completed the user will be redirected to the the Main Activity.
     *
     * @param token  the card token generated
     */
    private void callApiToSaveCard(String token) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject json = new JSONObject();
        json.put("token", token);

        JsonObjectRequest portRequest = new JsonObjectRequest(Request.Method.POST, YOUR_API_ZERO_DOLLAR_URL, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // redirect to the main activity
                        Intent myIntent = new Intent(AddCardActivity.this, MainActivity.class);
                        AddCardActivity.this.startActivity(myIntent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse networkResponse = error.networkResponse;
                        if (networkResponse != null && networkResponse.data != null) {
                            // handle error
                        }
                    }
                }
        );
        queue.add(portRequest);
    }
}
