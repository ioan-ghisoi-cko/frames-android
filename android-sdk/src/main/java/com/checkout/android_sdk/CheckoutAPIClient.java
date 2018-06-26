package com.checkout.android_sdk;

import android.content.Context;

import com.checkout.android_sdk.Request.CardTokenisationRequest;
import com.checkout.android_sdk.Request.GooglePayTokenisationRequest;
import com.checkout.android_sdk.Response.CardTokenisationFail;
import com.checkout.android_sdk.Response.CardTokenisationResponse;
import com.checkout.android_sdk.Response.GooglePayTokenisationFail;
import com.checkout.android_sdk.Response.GooglePayTokenisationResponse;
import com.checkout.android_sdk.Utils.Environment;
import com.checkout.android_sdk.Utils.HttpUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class CheckoutAPIClient {

    public String key;

    /**
     * This is interface used as a callback for when the card token is generated
     */
    public interface OnTokenGenerated {
        void onTokenGenerated(CardTokenisationResponse response);

        void onError(CardTokenisationFail error);
    }

    /**
     * This is interface used as a callback for when the google pay token is generated
     */
    public interface OnGooglePayTokenGenerated {
        void onTokenGenerated(GooglePayTokenisationResponse response);

        void onError(GooglePayTokenisationFail error);
    }

    private Context mContext;
    private Environment mEnvironment = Environment.SANDBOX;
    private CheckoutAPIClient.OnTokenGenerated mTokenListener;
    private CheckoutAPIClient.OnGooglePayTokenGenerated mGooglePayTokenListener;


    public CheckoutAPIClient(Context context, String key, Environment environment) {
        this.mContext = context;
        this.key = key;
        this.mEnvironment = environment;
    }

    public CheckoutAPIClient(Context context, String key) {
        this.mContext = context;
        this.key = key;
    }

    /**
     * This method is used to generate a card token.
     * <p>
     * It takes a {@link CardTokenisationRequest} as the argument and it will perform a
     * HTTP Post request to generate the token. it is important to you select an environment and
     * provide your public key before calling tis method. Moreover it is important to set a callback
     * {@link CheckoutAPIClient.OnTokenGenerated} so you can receive the token back.
     * <p>
     * If you are using the UI of the SDK this method will be called automatically, but you still
     * need to provide the callback, key and environment when initialising this class
     *
     * @param request Custom request body to be used in the HTTP call.
     */
    public void generateToken(CardTokenisationRequest request) {

        // Initialise the HTTP utility class
        HttpUtils http = new HttpUtils(mContext);

        // Provide a callback for when the token request is completed
        http.setTokenListener(mTokenListener);

        // Using Gson to convert the custom request object into a JSON string for use in the HTTP call
        Gson gson = new Gson();
        String jsonBody = gson.toJson(request);

        try {
            http.generateToken(key, mEnvironment.token, jsonBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method used to create a token that can be used in a server environment to create a
     * charge. It take a GooglePay payload in JSONObject. The payload is usually generated in
     * the handlePaymentSuccess method shown in the Google Pay example from Google (token.getToken())
     *
     * @param googlePayToken Google Pay Payload
     */
    public void generateGooglePayToken(JSONObject googlePayToken) throws JSONException {

        // Initialise the HTTP utility class
        HttpUtils http = new HttpUtils(mContext);

        // Provide a callback for when the token request is completed
        http.setGooglePayTokenListener(mGooglePayTokenListener);

        GooglePayTokenisationRequest gPay = new GooglePayTokenisationRequest();

        gPay
                .setSignature(googlePayToken.getString("signature"))
                .setProtocolVersion(googlePayToken.getString("protocolVersion"))
                .setSignedMessage(googlePayToken.getString("signedMessage"));

        // Using Gson to convert the custom request object into a JSON string for use in the HTTP call
        Gson gson = new Gson();
        String jsonBody = gson.toJson(gPay);

        try {
            http.generateToken(mEnvironment.googlePay, key, jsonBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method used to set a callback for 3D Secure handling.
     *
     * @return CheckoutAPIClient to allow method chaining
     */
    public CheckoutAPIClient setTokenListener(OnTokenGenerated listener) {
        this.mTokenListener = listener;
        return this;
    }

    /**
     * This method used to set a callback for Google Pay handling.
     *
     * @return CheckoutAPIClient to allow method chaining
     */
    public CheckoutAPIClient setGooglePayListener(OnGooglePayTokenGenerated listener) {
        this.mGooglePayTokenListener = listener;
        return this;
    }
}
