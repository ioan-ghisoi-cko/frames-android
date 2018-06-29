package checkout.com.saved_cards_example;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {

    // This is the URL of your backend endpoint that will perform the Get CardList request
    private final String GET_CARDS_URL = "https://frames-android-backend-example.herokuapp.com/cardlist";

    // Default value of the Alert, if the the user has not selected any card.
    private String selectedItem = "No Selection!";
    private Button mPay, mAdd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup group = findViewById(R.id.radio_group);

        mPay = findViewById(R.id.pay_button);
        mAdd = findViewById(R.id.add_button);

        // We populate the RadioGroup with the card linked to the customer
        callApiAndPopulateList(group);

        // When the used selects a card we store the card id
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                CustomRadioButton element = radioGroup.findViewById(i);
                selectedItem = element.getCardId();
            }
        });

        // When the user clicks the pay button, we display the card id linked to the
        // selected card. At this step you can send this card id to your server and
        // perform a charge.
        mPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // For the purpose of the demo, we are only displaying the card id.
                displayMessage("Card", selectedItem);
            }
        });

        // When the user clicks the add button, we redirect to the Payment Form.
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });
    }

    /**
     * This function will add an entry in a RadioGroup that will contain the name of the card,
     * the last 4 digits and the icon of the specific card type.
     *
     * @param group  the RadioGroup where the entry will be added
     * @param type  the type of card
     * @param last4  the last 4 digits of the card
     * @param cardId  the card id
     */
    private void addCardToList(RadioGroup group, String type, String last4, String cardId) {
        RadioGroup.LayoutParams rprms;
        CustomRadioButton radioButton = new CustomRadioButton(
                this,
                type,
                cardId
        );
        radioButton.setText(type + " ending " + last4);
        rprms = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        group.addView(radioButton, rprms);
    }

    /**
     * This function will call a backend server that will perform a "GetCardList request" to
     * checkout.com, and it will return a list of cards.
     *
     * @param group  the RadioGroup where the entry will be added
     */
    private void callApiAndPopulateList(final RadioGroup group) {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, GET_CARDS_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = (JSONArray) response.get("cardList");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonobject = array.getJSONObject(i);
                                addCardToList(
                                        group,
                                        jsonobject.getString("paymentMethod"),
                                        jsonobject.getString("last4"),
                                        jsonobject.getString("cardId")
                                );
                                String name = jsonobject.getString("paymentMethod");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse networkResponse = error.networkResponse;
                        if (networkResponse != null && networkResponse.data != null) {
                            // handle error
                        }
                    }
                }
        );
        queue.add(getRequest);
    }

    /**
     * This function will display an AlertDialog with the desired title and message.
     *
     * @param title  the title of the alert
     * @param message  the message of the alert
     */
    private void displayMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}