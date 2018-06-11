package com.example.android_sdk;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private Kit kit;

    private final Kit.onTokenGenerated mTokenListener = new Kit.onTokenGenerated() {

        @Override
        public void onSuccess(String token) {
            assertEquals("token", token);
        }

        @Override
        public void onError(String errorMessage) {

        }
    };

    @Test
    public void addition_isCorrect() {

        kit = new Kit("sk");
        kit.setTokenListener(mTokenListener);
        kit.genarateToken();
    }
}