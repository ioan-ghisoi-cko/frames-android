# CheckoutSdkAndroid - Beta
[![CircleCI](https://circleci.com/gh/ioan-ghisoi-cko/frames-android/tree/master.svg?style=svg)](https://circleci.com/gh/ioan-ghisoi-cko/frames-android/tree/master)
[![](https://jitpack.io/v/ioan-ghisoi-cko/just-a-test.svg)](https://jitpack.io/#ioan-ghisoi-cko/just-a-test)
> Beta - Do not use before speaking to integration@checkout.com

## Requirements
- Android minimum SDK 16
## Demo
To simply see a demo of the module and interact with the features you can clone this repo and open the project in Android Studio. 

```sh
git clone https://github.com/ioan-ghisoi-cko/just-a-test.git
```

## Installation
> Please keep in mind that this installation might change in the stable release, and we highly advise in using the project from this repository to test the module.

```gradle
// project gradle file
allprojects {
 repositories {
  ...
  maven { url 'https://jitpack.io' }
 }
}
 ...
// module gradle file
dependencies {
 implementation 'com.android.support:design:27.1.1'
 implementation 'com.google.code.gson:gson:2.8.5'
 implementation 'com.android.volley:volley:1.0.0'
 implementation 'com.github.ioan-ghisoi-cko:frames-android:v0.1'
}
```

> You can find more about the installation [here](https://jitpack.io/#ioan-ghisoi-cko/frames-android/v0.1)

> Please keep in mind that the Jitpack repository should to be added to the project gradle file while the dependency should be added in the module gradle file. [(see more about gradle files)](https://developer.android.com/studio/build)

## Usage

### For using the module's UI you need to do the following:
<br/>

**Step1** Add the module to your XML layout.
```xml
   <com.checkout.android_sdk.PaymentForm
        android:id="@+id/checkout_card_form"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
    />
```

**Step2** Include the module in your class.
```java
   private PaymentForm mPaymentForm; // include the module 
```

**Step3** Create a callback.
```java
   CheckoutAPIClient.OnTokenGenerated mTokenListener = new CheckoutAPIClient.OnTokenGenerated() {
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
   mPaymentForm = findViewById(R.id.checkout_card_form);
   mPaymentForm
         .setEnvironment(Environment.SANDBOX)
         .setKey("pk_test_6e40a700-d563-43cd-89d0-f9bb17d35e73")
         .setTokenListener(mTokenListener); // pass the callback
```


<br/>

### For using the module's without the UI you need to do the following:
<br/>

**Step1** Include the module in your class.
```java
   private CheckoutAPIClient mCheckoutAPIClient; // include the module 
```

**Step2** Create a callback.
```java
   CheckoutAPIClient.OnTokenGenerated mTokenListener = new CheckoutAPIClient.OnTokenGenerated() {
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

**Step3** Initialise the module and pass the card details.
```java
   mCheckoutAPIClient = new CheckoutAPIClient(
           context,             // activity context
           "pk_XXXXX",          // your public key
           Environment.SANDBOX  // the environment
   );
   mCheckoutAPIClient.setTokenListener(mTokenListener); // pass the callback


   // Pass the paylod and generate the token
   mCheckoutAPIClient.generateToken(
     new CardTokenisationRequest(
          "4242424242424242",
          "name",
          "06",
          "18",
          "100",
          new BillingModel(
                  "address line 1",
                  "address line 2",
                  "postcode",
                  "country",
                  "city",
                  "state",
                  new PhoneModel(
                          "+44",
                          "07123456789"
                  )
          )
     )
   );

```

## Customisation Options
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
   <com.example.android_sdk.PaymentForm
     android:id="@+id/checkout_card_form"
     android:layout_width="match_parent"
     android:layout_height="match_parent"
     android:theme="@style/YourCustomTheme"/>
```

If you would like to allow users to input their billing details when completing the payment details you can simply use the folllowing method:
```java
   mPaymentForm.includeBilling(true); // false value will hide the option
```

If you want to display only certain accepted card types you can select then in the following way:
```java
   mPaymentForm.setAcceptedCard(new Cards[]{VISA, MASTERCARD});
```

## Handle 3D Secure

The module allows you to handle 3DSecure URLs within your mobile app. Here are the steps:

> When you send a 3D secure charge request from your server you will get back a 3D Secure URL. You can then pass the 3D Secure URL to the module, to handle the verification.

**Step1** Create a callback.
```java
    private final PaymentForm.on3DSFinished m3DSecureListener = 
           new PaymentForm.on3DSFinished() {

        @Override
        public void onSuccess(String token) {
            // success
        }

        @Override
        public void onError(String errorMessage) {
            // fail
        }
    };
```

**Step2** Pass the callback to the module and handle 3D Secure
```java
    // can be used without the Payment form UI -> mPaymentForm = new PaymentForm(context); 
    mPaymentForm
            .set3DSListener(m3DSecureListener);
    
    mPaymentForm.handle3DS(
            "https://sandbox.checkout.com/api2/v2/3ds/acs/687805", // the 3D Secure URL
            "http://example.com/success", // the Redirection URL
            "http://example.com/fail" // the Redirection Fail URL
    );
```
> Keep in mind that the Redirection and Redirection Fail URLs are set in the Checkout Hub, but they can be overwritten in the charge request sent from your server. Keep in mind to provide the correct URLs to ensure a successful interaction.

## Handle Google Pay

The module allows you to handle a Google Pay token payload and retrieve a token, that can be used to create a charge from your backend.

**Step1** Create a callback.
```java

     private final CheckoutAPIClient.OnGooglePayTokenGenerated mGooglePayListener =
        new CheckoutAPIClient.OnGooglePayTokenGenerated() {
    
            @Override
            public void onTokenGenerated(GooglePayTokenisationResponse response) {
                // success
            }
    
            @Override
            public void onError(GooglePayTokenisationFail error) {
                // fail
            }
        };
```
**Step2** Pass the callback to the module and generate the token
```java
   mCheckoutAPIClient = new CheckoutAPIClient(
        context,             // activity context
        "pk_XXXXX",          // your public key
        Environment.SANDBOX  // the environment
   );
   mCheckoutAPIClient.setGooglePayListener(mGooglePayListener); // pass the callback

   mCheckoutAPIClient.generateGooglePayToken(payload); // the payload is the JSONObject generated by GooglePay
```

## Objects found in callbacks
#### When deling with actions like generating a card token the callback will include the following objects.

**For success -> CardTokenisationResponse** 
<br/>
This has the following getters:
```java
   response.getId();               // the card token 
   response.getLiveMode();         // environment mode
   response.getCreated();          // timestamp of creation
   response.getUsed();             // show usage
   response.getCard();             // card object containing card information and billing details
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

## License

frames-android is released under the MIT license.
