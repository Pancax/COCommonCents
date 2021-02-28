package com.pancaxrzcosola.cocommoncents;

import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {

    private TextView mTextView;
    private final String keyAccountNickname = "COCCUTDHKTN2021";
    ServerCommunicator communicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        mTextView = (TextView) findViewById(R.id.text);

        //Data initialization and creation
        final Customer cust = new Customer();
        final Account acc = new Account();
        communicator = new ServerCommunicator(this);
        String savingsvalue = "";
        communicator.getCustomerForID(savedInstanceState.get("customer_id").toString(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    cust.set_id(response.getString("_id"));
                    cust.setFirst_name(response.getString("first_name"));
                    cust.setLast_name(response.getString("last_name"));
                    cust.setAccounts(response.getJSONArray("account_ids"));
                    cust.setAddress(response.getJSONObject("address"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("wrong1","Unable to Obtain Customer!");
            }
        });


        //Check if Savings and Checking account exists
        boolean savingsExists = false, checkingExists = false;
        for(int i = 0;i<cust.getAccounts().length();i++){
            try {
                communicator.getAccountfromCustomerID(cust.getAccounts().get(i).toString(),new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            acc.set_id(response.getString("_id"));
                            acc.setType(response.getString("type"));
                            acc.setBalance(response.getDouble("balance"));
                            acc.setCustomer_id(response.getString("customer_id"));
                            acc.setNickname(response.getString("nickname"));
                            acc.setRewards(response.getInt("rewards"));
                            acc.setBill_ids(response.getJSONArray("bill_ids"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("wrong2","Unable to Obtain Account Info!");
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(acc.getNickname().compareToIgnoreCase(keyAccountNickname)==0){
                savingsExists=true;
            }
            if(acc.getType().compareToIgnoreCase("Checking")==0){
                checkingExists=true;
            }
        }

        //Does Check exist?
        if(!checkingExists){
            savingsvalue = "Checking Account Not Found!";
        }
        //Generate Savings if false
        else{
            if(!savingsExists){

            }
        }

    }

}