package com.example.android_sdk;

public class Kit {

    public interface onTokenGenerated {

        void onSuccess(String token);
        void onError(String errorMessage);
    }

    private Kit.onTokenGenerated mTokenListener;
    private String mKey;

    public Kit(String key) {
        this.mKey = key;
    }

    public void genarateToken() {
        mTokenListener.onSuccess("token");
    }

    public void setTokenListener(Kit.onTokenGenerated listener) {
        this.mTokenListener = listener;
    }
}
