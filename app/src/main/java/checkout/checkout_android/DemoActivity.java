package checkout.checkout_android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android_sdk.Kit;
import com.example.android_sdk.PaymentForm;

public class DemoActivity extends Activity {

    PaymentForm mPaymentForm;
    Kit kit;

//    private final Kit.onTokenGenerated mTokenListener = new Kit.onTokenGenerated() {
//
//        @Override
//        public void onSuccess(String token) {
//            Toast.makeText(DemoActivity.this, token, Toast.LENGTH_LONG).show();
//        }
//
//        @Override
//        public void onError(String errorMessage) {
//            Toast.makeText(DemoActivity.this, errorMessage, Toast.LENGTH_LONG).show();
//        }
//    };
//
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        mPaymentForm = findViewById(R.id.checkout_card_form);


//        mPaymentForm.set3DSListener(m3DSecureListener);
//        mPaymentForm.handle3DS("https://sandbox.checkout.com/api2/v2/3ds/acs/687805",
//                "http://google.com/success",
//                "http://google.com/fail");


//        kit = new Kit("sk");
//        kit.setTokenListener(mTokenListener);
//        kit.genarateToken();

    }
}
