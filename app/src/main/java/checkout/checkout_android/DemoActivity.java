package checkout.checkout_android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android_sdk.CheckoutKit;
import com.example.android_sdk.Response.CardTokenisationFail;
import com.example.android_sdk.Response.CardTokenisationResponse;

public class DemoActivity extends Activity {

    private CheckoutKit mPayment;

    private final CheckoutKit.OnTokenGenerated mTokenListener = new CheckoutKit.OnTokenGenerated() {

        @Override
        public void onTokenGenerated(CardTokenisationResponse token) {
            Toast.makeText(DemoActivity.this, token.getLast4(),
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(CardTokenisationFail error) {
            Toast.makeText(DemoActivity.this, error.getEventId(),
                    Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        mPayment = findViewById(R.id.checkout_card_form);

        mPayment
                .setEnvironment("sandbox")
                .setKey("pk_test_6e40a700-d563-43cd-89d0-f9bb17d35e73")
                .setTokenListener(mTokenListener)
                .includeBilling(true);

    }
}