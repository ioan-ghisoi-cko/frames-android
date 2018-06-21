# CheckoutSdkAndroid - Beta
> Beta - Do not use before speaking to integration@checkout.com

# Requirements
- Android minimum SDK 16
# Demo
To simply see a demo of the module and interact with the features you can clone this repo and open the project in Android Studio. 

```sh
git clone https://github.com/ioan-ghisoi-cko/just-a-test.git
```

# Installation(beta)
> Please keep in mind that this instalation migth change in the stable release, and we highly advise in using the project from this repository to test the module.

The module is currently available via jitpack and you can find the installation instructions [here](https://jitpack.io/#ioan-ghisoi-cko/just-a-test)

Moreover, our module has a few dependecies used for API comunication and UI. Please add the following dependencies to your Gradle file: 
- **implementation 'com.android.support:design:27.1.1'**
- **implementation 'com.google.code.gson:gson:2.8.5'**
- **implementation 'com.android.volley:volley:1.0.0'**

# Usage

### For using the module's UI you need to do the following:
<br/>

**Step1** Add the module to your XML layout.
```xml
   <com.checkout.android_sdk.CheckoutKit
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
         .setTokenListener(mTokenListener); // pass the callback
```


<br/>

### For using the module's without the UI you need to do the following:
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

**Step3** Initialise the module and pass the card details 
```java
   mPayment = new CheckoutKit(this);

   mPayment
         .setTokenListener(mTokenListener)
         .setEnvironment("sandbox")
         .setKey("pk_test_6e40a700-d563-43cd-89d0-f9bb17d35e73")
         .setTokenListener(mTokenListener); // pass the callback

   CardTokenisationRequest details = new CardTokenisationRequest();

   // Populate the request details
   details
      .setCardNumber("4242424242424242")
      .setExpiryMonth("06")
      .setExpiryYear("2018")
      .setCvv("10000")
      .setName("Johnny")
      .setCountry("US")
      .setAddressLine1("London 1")
      .setAddressLine2("London 2")
      .setCity("London")
      .setState("London")
      .setPostcode("w1w w1w")
      .setPhoneNumber("44", "07123456789");

   mPayment.generateToken(details); // pass the request details object
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
             "http://example.com/success", // the Redirection URL
             "http://example.com/fail" // the Redirection Fail URL
   );
```
> Keep in mind that the Redirection and Redirection Fail URLs are set in the Checkout Hub, but they can be overwritten in the charge request sent from your server. Keep in mind to provide the correct URLs to ensure a successful interaction.

# Handle Google Pay

The module allows you to handle a Google Pay token payload and retrive a token, that can be used to create a charge from your backend.

**Step1** Create a callback.
```java
   private final CheckoutKit.OnGooglePayTokenGenerated mGooglePayListener = 
          new CheckoutKit.OnGooglePayTokenGenerated() {
        @Override
        public void onTokenGenerated(GooglePayTokenisationResponse response) {
            // token
        }

        @Override
        public void onError(GooglePayTokenisationFail error) {
            // error
        }
   };
```
**Step2** Pass the callback to the module and generate the token
```java
   mPayment.setGooglePayListener(mGooglePayListener); // pass the callback

   mPayment.generateGooglePayToken(payload); // the payload is the JSONObject generated by GooglePay
```

# Objects found in callbacks
#### When deling with actions like generating a card token the callback will include the following objects.

**For success -> CardTokenisationResponse** 
<br/>
This has the following getters:
```java
   response.getId();               // the card token 
   response.getLiveMode();         // environment mode
   response.getCreated();          // timestamp of creation
   response.getUsed();             // show usage
   response.getExpiryMonth();      // the card expiry month
   response.getExpiryYear();       // the card expiry year
   response.getAddressLine1();     // the billing address line 1
   response.gettAddressLine2();    // the billing address line 1
   response.getPostcode();         // the billing postcode
   response.getCountry();          // the billing country
   response.getCity();             // the billing city
   response.getState();            // the billing state
   response.getPhoneNumber();      // the phone number
   response.getPhoneCountryCode(); // the phone number country code
```

**For error -> CardTokenisationResponse** 
<br/>
This has the following getters:
```java
   error.getEventId();           // a unique identifier for the event 
   error.getMessage();           // the error message
   error.getErrorCode();         // the error code
   error.getErrorMessageCodes(); // an array or strings with all error codes 
   error.getErrors();            // an array or strings with all error messages 
```

#### When deling with actions like generating a token for a Google Pay payload the callback will include the following objects.

**For success -> GooglePayTokenisationResponse** 
<br/>
This has the following getters:
```java
   response.getToken();      // the token
   response.getExpiration(); // the token exiration 
   response.getType();       // the token type
```

**For error -> GooglePayTokenisationFail** 
<br/>
This has the following getters:
```java
   error.getRequestId();  // a unique identifier for the request 
   error.getErrorType();  // the error type
   error.getErrorCodes(); // an array of strings with all the error codes
```
