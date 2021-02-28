package com.pancaxrzcosola.cocommoncents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity{


    EditText customerId;
    EditText password;
    Button loginButton;
    Button createButton;
    //ImageView coolImageLogo;


    private String customerIdText="";

    private String practiceCustomer="603b43be4a4a8605712849ba";

    ServerCommunicator communicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customerId = (EditText)findViewById(R.id.passwordField);
        password = (EditText)findViewById(R.id.customerIDField);
        loginButton = (Button) findViewById(R.id.button_login);
        createButton = (Button) findViewById(R.id.registerButton);
       // coolImageLogo = (ImageView) findViewById(R.id.imageView);
       // coolImageLogo.setImageResource(R.drawable.logo_small);

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
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createActivity=new Intent(MainActivity.this, CreateActivity.class);
                startActivity(createActivity);
            }
        });
    }
}


