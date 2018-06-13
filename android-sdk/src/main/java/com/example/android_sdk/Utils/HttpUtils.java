package com.example.android_sdk.Utils;

import android.content.Context;
import android.support.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android_sdk.Input.CityInput;
import com.example.android_sdk.PaymentForm;
import com.example.android_sdk.Store.DataStore;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HttpUtils {

//    private PaymentForm.OnTokenGenerated mCardListener = new PaymentForm.OnTokenGenerated() {
//        @Override
//        public void onTokenGenerated(String token) {
//
//        }
//
//        @Override
//        public void onError(String errorMessage) {
//
//        }
//
//    };

    private @Nullable
    PaymentForm.OnTokenGenerated mTokenListener;
    private Context  mContext;

    public HttpUtils(Context context) {
        //empty constructor
        mContext = context;
    }

    public void generateToken(String key, String url, String body) throws JSONException {


        performRequest(key, url, body);
    }

    private void performRequest(final String key, String url, String body) throws JSONException {

        RequestQueue queue = Volley.newRequestQueue(mContext);

        JsonObjectRequest portRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(body),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(mTokenListener != null) {
                                mTokenListener.onTokenGenerated(String.valueOf(response.get("id")));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse networkResponse = error.networkResponse;
                        if (networkResponse != null && networkResponse.data != null) {
                            try {
                                JSONObject jsonError = new JSONObject(new String(networkResponse.data));
                                if(mTokenListener != null) {
                                    mTokenListener.onError(jsonError.getString("message"));
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

    public void setTokenListener(PaymentForm.OnTokenGenerated listener) {
        mTokenListener = listener;
    }
}
