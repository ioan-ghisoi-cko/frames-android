# CheckoutSdkAndroid - Beta
> Beta - Do not use before speaking to integration@checkout.com

# Usage

### For using the module's UI you need to do the following:
<br/>

**Step1** Add the module to your XML layout.
```xml
   <com.example.android_sdk.CheckoutKit
        android:id="@+id/checkout_card_form"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
    />
```

**Step2** Include the module in your class.
```java
    private CheckoutKit mPayment; // include the module 
```

**Step3** Create a callback.
```java
    CheckoutKit.OnTokenGenerated mTokenListener = new CheckoutKit.OnTokenGenerated() {
        @Override
        public void onTokenGenerated(CardTokenisationResponse token) {
            // your token
        }
        @Override
        public void onError(CardTokenisationFail error) {
            // your error
        }
    };
```

**Step4** Initialise the module
```java
    mPayment = findViewById(R.id.checkout_card_form);
    mPayment
            .setEnvironment("sandbox")
            .setKey("pk_test_6e40a700-d563-43cd-89d0-f9bb17d35e73")
            .setTokenListener(mTokenListener) // pass the callback
```


<br/>

### For using the module's without the UI you need to do the follwing:
<br/>

**Step1** Include the module in your class.
```java
    private CheckoutKit mPayment; // include the module 
```

**Step2** Create a callback.
```java
    CheckoutKit.OnTokenGenerated mTokenListener = new CheckoutKit.OnTokenGenerated() {
        @Override
        public void onTokenGenerated(CardTokenisationResponse token) {
            // your token
        }
        @Override
        public void onError(CardTokenisationFail error) {
            // your error
        }
    };
```

**Step3** Initialise the module
```java
    mPayment = new CheckoutKit(this);
        
    mPayment
            .setTokenListener(mTokenListener)
            .setEnvironment("sandbox")
            .setKey("pk_test_6e40a700-d563-43cd-89d0-f9bb17d35e73")
            .setTokenListener(mTokenListener); // pass the callback
        
    mPayment.generateToken(body);
```

# Customisation Options
The module extends a **Frame Layout** so you can use XML attributes like:
```java
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@colors/your_color"
```

Moreover, the module inherits the  **Theme.AppCompat.Light.DarkActionBar** style so if you want to customise the look of the payment form you can define you own style and theme the module with it:
```xml
    <style name="YourCustomTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <!-- TOOLBAR COLOR. -->
        <item name="colorPrimary">#000000</item>
        <!--FIELDS HIGHLIGHT COLOR-->
        <item name="colorAccent">#000000</item>
        <!--PAY/DONE BUTTON COLOR-->
        <item name="colorButtonNormal">#000000</item>
        <!--HELPER LABELS AND UNSELECTED FIELD COLOR-->
        <item name="colorControlNormal">#000000</item>
    </style>
    ...
       <com.example.android_sdk.CheckoutKit
        android:id="@+id/checkout_card_form"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:theme="@style/YourCustomTheme"/>
```

If you would like to allow users to input their billing details when completing the payment details you can simply use the folllowing method:
```java
    mPayment.includeBilling(true); // false value will hide the option
```

# Handle 3D Secure

The module allows you to handle 3DSecure URLs within your mobile app. Here are the steps:

> When you send a 3D secure charge request from your server you will get back a 3D Secure URL. You can then pass the 3D Secure URL to the module, to handle the verification.

**Step1** Create a callback.
```java
    private final CheckoutKit.on3DSFinished m3DSecureListener = new PaymentForm.on3DSFinished() {
        @Override
        public void onSuccess(String paymentToken) {
            // success
        }
        @Override
        public void onError(String paymentToken) {
            // error
        }
    };
```

**Step2** Pass the callback to the module and handle 3D Secure
```java
    mPayment.set3DSListener(m3DSecureListener); // pass the callback
    
    mPaymentForm.handle3DS(
                "https://sandbox.checkout.com/api2/v2/3ds/acs/687805", // the 3D Secure URL
                "http://example.com/success", // the Redirection URL set up in the Checkout.com HUB
                "http://example.com/fail" // the Redirection Fail URL set up in the Checkout.com HUB
    );
```
