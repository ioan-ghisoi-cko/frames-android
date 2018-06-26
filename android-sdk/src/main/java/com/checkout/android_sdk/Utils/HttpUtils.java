package com.checkout.android_sdk.Utils;

import android.content.Context;
import android.support.annotation.Nullable;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.checkout.android_sdk.CheckoutAPIClient;
import com.checkout.android_sdk.Response.CardTokenisationFail;
import com.checkout.android_sdk.Response.CardTokenisationResponse;
import com.checkout.android_sdk.Response.GooglePayTokenisationFail;
import com.checkout.android_sdk.Response.GooglePayTokenisationResponse;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper class used to create HTTP calls
 * <p>
 * This class handles interaction with the Checkout.com API
 */
public class HttpUtils {

    private @Nullable
    CheckoutAPIClient.OnTokenGenerated mTokenListener;
    private @Nullable
    CheckoutAPIClient.OnGooglePayTokenGenerated mGooglePayTokenListener;
    private Context mContext;

    public HttpUtils(Context context) {
        //empty constructor
        mContext = context;
    }

    /**
     * Used to do a HTTP call with the card details
     * <p>
     * This method will perform an HTTP POST request to the Checkout.com API.
     * The API call is async so it will us the callback to communicate the result
     * This method is used to generate a card token
     *
     * @param key  the public key of the customer
     * @param url  the request URL
     * @param body the body of the request as a JSON String
     */
    public void generateGooglePayToken(final String key, String url, String body) throws JSONException {

        RequestQueue queue = Volley.newRequestQueue(mContext);

        JsonObjectRequest portRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(body),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Create a response object and populate it
                        GooglePayTokenisationResponse responseBody = new Gson().fromJson(response.toString(), GooglePayTokenisationResponse.class);
                        // Use the callback to send the response
                        if (mGooglePayTokenListener != null) {
                            mGooglePayTokenListener.onTokenGenerated(responseBody);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse networkResponse = error.networkResponse;
                        if (networkResponse != null && networkResponse.data != null) {
                            try {
                                JSONObject jsonError = new JSONObject(new String(networkResponse.data));
                                GooglePayTokenisationFail errorBody = new Gson().fromJson(jsonError.toString(), GooglePayTokenisationFail.class);
                                if (mGooglePayTokenListener != null) {
                                    mGooglePayTokenListener.onError(errorBody);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                // Add the Authorisation headers
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", key);
                return params;
            }
        };
        // Enable retry policy since is not enabled by default in the Volley
        portRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 10, 1.0f));
        queue.add(portRequest);
    }

    /**
     * Used to do a HTTP call with the Google Pay's payload
     * <p>
     * This method will perform an HTTP POST request to the Checkout.com API.
     * The API call is async so it will us the callback to communicate the result.
     * This method is used to generate a token for Google Pay
     *
     * @param key  the public key of the customer
     * @param url  the request URL
     * @param body the body of the request as a JSON String
     */
    public void generateToken(final String key, String url, String body) throws JSONException {

        RequestQueue queue = Volley.newRequestQueue(mContext);

        JsonObjectRequest portRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(body),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (mTokenListener != null) {
                            CardTokenisationResponse responseBody = new Gson().fromJson(response.toString(), CardTokenisationResponse.class);
                            mTokenListener.onTokenGenerated(responseBody);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse networkResponse = error.networkResponse;
                        if (networkResponse != null && networkResponse.data != null) {
                            try {
                                JSONObject jsonError = new JSONObject(new String(networkResponse.data));
                                CardTokenisationFail cardTokenisationFail = new Gson().fromJson(jsonError.toString(), CardTokenisationFail.class);
                                if (mTokenListener != null) {
                                    mTokenListener.onError(cardTokenisationFail);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", key);
                return params;
            }
        };
        portRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 10, 1.0f));
        queue.add(portRequest);
    }

    /**
     * Used to set the callback listener for when the card token is generated
     */
    public void setTokenListener(CheckoutAPIClient.OnTokenGenerated listener) {
        mTokenListener = listener;
    }

    /**
     * Used to set the callback listener for when the token for Google Pay is generated
     */
    public void setGooglePayTokenListener(CheckoutAPIClient.OnGooglePayTokenGenerated listener) {
        mGooglePayTokenListener = listener;
    }
}
