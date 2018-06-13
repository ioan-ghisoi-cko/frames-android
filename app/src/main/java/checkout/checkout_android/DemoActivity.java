package checkout.checkout_android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android_sdk.Kit;
import com.example.android_sdk.PaymentForm;
import com.example.android_sdk.Utils.TokenisationRequest;

public class DemoActivity extends Activity {

    PaymentForm mPaymentForm;

//    private final PaymentForm.on3DSFinished m3DSecureListener = new PaymentForm.on3DSFinished() {
//        @Override
//        public void onSuccess(String paymentToken) {
//            String myPaymentToken = paymentToken;
//        }
//
//        @Override
//        public void onError(String paymentToken) {
//            String myPaymentToken = paymentToken;
//        }
//    };

    private final PaymentForm.OnTokenGenerated mTokenListener = new PaymentForm.OnTokenGenerated() {

        @Override
        public void onTokenGenerated(String token) {
            Toast.makeText(DemoActivity.this, token,
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(String errorMessage) {
            Toast.makeText(DemoActivity.this, errorMessage,
                    Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        mPaymentForm = findViewById(R.id.checkout_card_form);

        mPaymentForm
                .setEnvironment("sandbox")
                .setKey("pk_test_6e40a700-d563-43cd-89d0-f9bb17d35e73")
                .setTokenListener(mTokenListener)
                .includeBilling(true);


//        TokenisationRequest request = new TokenisationRequest();
//
//        request
//                .setCardNumber("4242424242424242")
//                .setExpiryMonth("06")
//                .setExpiryYear("2018")
//                .setCvv("100")
//                .setName("Johnny")
//                .setCountry("US")
//                .setAddressLine1("London 1")
//                .setAddressLine2("London 2")
//                .setCity("London")
//                .setState("London")
//                .setPostcode("w1w w1w")
//                .setPhoneNumber("44", "07123456789");
//
//        mPaymentForm = new PaymentForm(this, "sandbox", "pk_test_6e40a700-d563-43cd-89d0-f9bb17d35e73");
//        mPaymentForm.setTokenListener(mTokenListener);
//        mPaymentForm.generateToken(request);



//        mPaymentForm.set3DSListener(m3DSecureListener);
//        mPaymentForm.handle3DS("https://sandbox.checkout.com/api2/v2/3ds/acs/687805",
//                "http://google.com/success",
//                "http://google.com/fail");

    }
}
