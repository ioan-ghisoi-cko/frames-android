package checkout.checkout_android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android_sdk.Kit;
import com.example.android_sdk.PaymentForm;

public class DemoActivity extends Activity {

    PaymentForm mPaymentForm;
    Kit kit;

    private final Kit.onTokenGenerated mTokenListener = new Kit.onTokenGenerated() {

        @Override
        public void onSuccess(String token) {
//            Toast.makeText(DemoActivity.this, token, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(String errorMessage) {
//            Toast.makeText(DemoActivity.this, errorMessage, Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        mPaymentForm = findViewById(R.id.checkout_card_form);


        kit = new Kit("sk");
        kit.setTokenListener(mTokenListener);
        kit.genarateToken();

    }
}
