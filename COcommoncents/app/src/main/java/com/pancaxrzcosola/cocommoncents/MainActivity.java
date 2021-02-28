package com.pancaxrzcosola.cocommoncents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.reimaginebanking.api.nessieandroidsdk.NessieError;
import com.reimaginebanking.api.nessieandroidsdk.NessieResultsListener;
import com.reimaginebanking.api.nessieandroidsdk.models.Customer;
import com.reimaginebanking.api.nessieandroidsdk.requestclients.NessieClient;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity{


    EditText customerId;
    EditText password;
    Button loginButton;

    private String customerIdText="";

    private String practiceCustomer="603b43be4a4a8605712849bc";

    private String URL ="api.nessieisreal.com/enterprise/customers/";
    ServerCommunicator communicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customerId = (EditText)findViewById(R.id.edittext_customerid);
        password = (EditText)findViewById(R.id.edittext_password);
        loginButton = (Button) findViewById(R.id.button_login);

        communicator = new ServerCommunicator(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginButton.setClickable(false);
                customerIdText = practiceCustomer;
                communicator.getCustomerForID(practiceCustomer, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("onSuccess", response.toString());
                        Intent homeActivity = new Intent(MainActivity.this, HomeActivity.class);
                        homeActivity.putExtra("customer_id",customerIdText);
                        startActivity(homeActivity);

                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("onfailure", error.toString());
                        loginButton.setClickable(true);
                    }
                });


            }
        });
    }
}


