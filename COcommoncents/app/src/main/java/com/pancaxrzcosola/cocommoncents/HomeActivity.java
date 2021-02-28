package com.pancaxrzcosola.cocommoncents;

import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.pancaxrzcosola.cocommoncents.sqlitestuff.AppDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {

    private TextView mTextView;
    public static final String keyAccountNickname = "COCCUTDHKTN2021";
    ServerCommunicator communicator;
    RecyclerView rView;
    AdapterArray adar;
    private boolean savingsExists = false;
    private boolean checkingExists = false;
    private Customer cust = new Customer();

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setUpDataBase();


        mTextView = (TextView) findViewById(R.id.text);

        //Data initialization and creation


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

        for(int i = 0;i<cust.getAccounts().length();i++){
            try {
                communicator.getAccountfromAccountID(cust.getAccounts().get(i).toString(),new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Account acc = new Account();
                            acc.set_id(response.getString("_id"));
                            acc.setType(response.getString("type"));
                            acc.setBalance(response.getDouble("balance"));
                            acc.setCustomer_id(response.getString("customer_id"));
                            acc.setNickname(response.getString("nickname"));
                            acc.setRewards(response.getInt("rewards"));
                            acc.setBill_ids(response.getJSONArray("bill_ids"));
                            cust.addAccount(acc);
                            if(acc.getNickname().equals(keyAccountNickname)){
                                savingsExists=true;
                            }
                            if(acc.getType().equals("checking")){
                                checkingExists=true;
                            }
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


        }


        //Scroll through accounts

        adar = new AdapterArray(cust.getAccounts());
        rView = findViewById(R.id.rViewID);
        rView.setAdapter(adar);
        //Does Check exist?
        if(!checkingExists){
            //print out that a checking acc does not exist for this
            //finish this activity
            finish();
        }
        //Generate Savings if false
        else{
            //if savings dont exist we need to create a new account for this customer and call it the name
            if(!savingsExists){
                communicator.makeCustomerSavingsAcc(cust.get_id(), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
            }
            //if savings do exist we need to do something

        }

        updateTransfersAndPurchases();
        //this starts the thread which updates the view everyonce in a while
        Thread updateThread = new Thread(){
            @Override
            public void run(){
                while(this.isAlive()){
                    try {
                        this.sleep(30000);
                        updateTransfersAndPurchases();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        updateThread.start();
    }


    //this method gets the transfers and purchases every once in a while
    private void updateTransfersAndPurchases() {
        //this gets the transfers purchases


        //TODO:: get transfers and purchases from each account checking that is also in the database
        //TODO:: before we do that, we need to have settings set up so we know which account we want to pull purchases and transfers from
        //add to big list
        //just do all the checking accounts FOR NOW
        for(Account x:cust.getAccountList()){
            communicator.getPurchasesForAccount(x.get_id(), new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            communicator.getTransfersForAccount(x.get_id(), new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
        //cleanup big list
        //give to brayden

    }


    private void setUpDataBase() {
        db = Room.databaseBuilder(this, AppDatabase.class, "app-database").build();


    }



    //remember to add the accounts to the database and store them also

}