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
import com.pancaxrzcosola.cocommoncents.sqlitestuff.TransferDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


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
                    // we are done getting customer so now get the accounts
                    getAccountsForCustomer();
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

    }

    private void getAccountsForCustomer() {
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
                            if(cust.getAccounts().length()==cust.getAccountList().size()){
                                //we are done instantiating everything SO NOW DO EVERYTHING ELSE
                                continueOnCreate();
                            }
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
    }

    private void continueOnCreate() {
        adar = new AdapterArray(cust.getAccounts());
        rView = findViewById(R.id.rViewID);
        rView.setAdapter(adar);
        //Does Check exist?
        if(!checkingExists){
            //print out that a checking acc does not exist for this
            //finish this activity
            finish();
        }
        else{
            //if savings dont exist we need to create a new account for this customer and call it the name
            if(!savingsExists){
                communicator.makeCustomerSavingsAcc(cust.get_id(), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        savingsExist();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
            }
            else
                savingsExist();
            //if savings do exist we need to do something
            //continue with our other things

        }

    }
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
    private void savingsExist() {
        updateTransfersAndPurchases();
        //this starts the thread which updates the view everyonce in a while
        updateThread.start();
    }


    //this method gets the transfers and purchases every once in a while

    ArrayList<JSONObject> transferObjects=new ArrayList<>();
    ArrayList<JSONObject> purchaseObjects=new ArrayList<>();
    private void updateTransfersAndPurchases() {
        //this gets the transfers purchases


        //TODO:: get transfers and purchases from each account checking that is also in the database
        //TODO:: before we do that, we need to have settings set up so we know which account we want to pull purchases and transfers from
        //add to big list
        //just do all the checking accounts FOR NOW
        for(int i=0;i<cust.getAccountList().size();i++){
            Account x= cust.getAccountList().get(i);

            communicator.getPurchasesForAccount(x.get_id(), new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for(int i=0;i<response.length();i++){
                        try {
                            purchaseObjects.add(response.getJSONObject(i));
                            if(i==cust.getAccountList().size()){
                                doneWithPurchases=true;
                                doneWithBoth();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            communicator.getTransfersForAccount(x.get_id(), new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for(int i=0;i<response.length();i++){
                        try {
                            transferObjects.add(response.getJSONObject(i));
                            if(i==cust.getAccountList().size()){
                                doneWithTransfers=true;
                                doneWithBoth();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
        //cleanup big list

        //just grab 25 at random who cares
        //TODO:: fix it later

        ArrayList<JSONObject> passToBrayPurch = new ArrayList<>();
        for(int i=0;i<25&&i<purchaseObjects.size();i++){
            passToBrayPurch.add(purchaseObjects.get(i));

        }
        //give to brayden

    }

    boolean doneWithPurchases=false;
    boolean doneWithTransfers=false;
    private void doneWithBoth(){
        if(doneWithPurchases&&doneWithTransfers)
            compareDataBaseToInformation();
    }


    //
    //
    //
    //<purchase, transfer> or purchase transfer
    final double ROUNDUP_BOUND=5;
    private void compareDataBaseToInformation(){
        ArrayList<TransferDB> ourList = new ArrayList<>(db.transferDao().getAll());
        HashSet<String> idSet = new HashSet<>();
        for(TransferDB x: ourList) {
            idSet.add(x.purchaseID);
            //make the purchase/transfer objects we already have in the database
            //TODO::
        }
        for(JSONObject x: purchaseObjects){
            try {
                String purchaseDate = x.getString("purchase_date");
                String purchaseID = x.getString("_id");
                Integer amount = x.getInt("amount");
                String status = x.getString("status");
                String medium = x.getString("medium");
                if(!idSet.contains(purchaseID)){
                    //IF THIS CALL RETURNS TRUE THEN WE ADDED TO A SET
                    processPurchase(purchaseDate,purchaseID,amount,status,medium);
                }

            }catch(Exception e){e.printStackTrace();}
        }
    }

    // WE ONLY WANT PURCHASES MADE FROM LAST WEEK THAT ARE IN BOUND LIMITS THAT ARE "COMPLETED" STATUS
    private void processPurchase(String date, String id, Integer amount, String status, String medium){
        //TODO:: This method actually connects and makes a transfer for this purchase IF IT REQUIRES ONE
        //TODO:: figure out how to do the date filtering
        if(amount % ROUNDUP_BOUND!=0&&medium.equals("completed")){
            //room for rounding up
            int guess= amount;
            //add until we get to cap
            while(guess%ROUNDUP_BOUND!=0){
                guess++;
            }
        }else{

        }

    }


    private void setUpDataBase() {
        db = Room.databaseBuilder(this, AppDatabase.class, "app-database").build();
    }



    //remember to add the accounts to the database and store them also

}