package com.pancaxrzcosola.cocommoncents;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

public class CreateActivity extends AppCompatActivity {

    //edittext buttons
    EditText firstNameET;
    EditText lastNameET;
    EditText streetAddrET;
    EditText stateET;
    EditText zipET;
    EditText cityET;
    EditText streetNameET;
    Button createButton;

    //
    ServerCommunicator communicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_layout);

        //instantation buttons and stuff
        firstNameET = (EditText)findViewById(R.id.edittest_firstname);
        lastNameET = (EditText)findViewById(R.id.edittext_lastname);
        streetAddrET = (EditText)findViewById(R.id.edittext_streetaddr);
        stateET = (EditText)findViewById(R.id.edittext_state);
        streetNameET = (EditText)findViewById(R.id.edittext_streetname);
        streetNameET = (EditText)findViewById(R.id.edittext_streetnumber);
        cityET = (EditText)findViewById(R.id.edittext_cityaddr);
        zipET = (EditText)findViewById(R.id.edittext_zip);
        createButton = (Button)findViewById(R.id.createButton);

        communicator = new ServerCommunicator(this);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = firstNameET.getText().toString().trim();
                String lastName = lastNameET.getText().toString().trim();
                JSONObject address = new JSONObject();
                try {
                    address.put("street_number", streetAddrET.getText().toString().trim());
                    address.put("street_name", streetNameET.getText().toString().trim());
                    address.put("city", cityET.getText().toString().trim());
                    address.put("state", stateET.getText().toString().trim());
                    address.put("zip", zipET.getText().toString().trim());
                }catch(Exception e){e.printStackTrace();}
                communicator.makeCustomerAcc(firstName, lastName, address, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //did our post work
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //if it didnt what went wrong
                    }
                });

            }
        });
    }



}
