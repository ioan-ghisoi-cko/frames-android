package checkout.com.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import checkout.com.demo.Demos.CustomFieldsDemo;
import checkout.com.demo.Demos.CustomisationDemo;
import checkout.com.demo.Demos.HeadlessDemo;
import checkout.com.demo.Demos.ThreeDSecureDemo;

public class MainActivity extends Activity {

    private Button mCustomisationDemo;
    private Button mCustomFieldsDemo;
    private Button m3DSecureDemo;
    private Button mHeadlessDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCustomisationDemo = findViewById(R.id.customisation_demo);
        mCustomFieldsDemo = findViewById(R.id.custom_fields_demo);
        m3DSecureDemo = findViewById(R.id.three_d_secure_demo);
        mHeadlessDemo = findViewById(R.id.headless_demo);

        mCustomisationDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, CustomisationDemo.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        mCustomFieldsDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, CustomFieldsDemo.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        m3DSecureDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, ThreeDSecureDemo.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        mHeadlessDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, HeadlessDemo.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

    }
}
