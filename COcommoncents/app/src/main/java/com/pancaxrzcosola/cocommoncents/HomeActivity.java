package com.pancaxrzcosola.cocommoncents;

import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Index;
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
    private String customerID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setUpDataBase();


        mTextView = (TextView) findViewById(R.id.text);

        //Data initialization and creation
        adar = new AdapterArray(pTransfers);
        rView = findViewById(R.id.rViewID);
        rView.setAdapter(adar);

        communicator = new ServerCommunicator(this);
        String savingsvalue = "";
        customerID = this.getIntent().getExtras().getString("customer_id");
        Log.i("Customer id", customerID);
        communicator.getCustomerForID(customerID, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    cust.set_id(response.getString("_id"));
                    cust.setFirst_name(response.getString("first_name"));
                    cust.setLast_name(response.getString("last_name"));
                    cust.setAddress(response.getJSONObject("address"));
                    // we are done getting customer so now get the accounts
                    getAccountIDSFromCustomer();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

    }

    private void getAccountIDSFromCustomer(){
        communicator.getAccountIDSFromCustomer(customerID, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                cust.setAccounts(response) ;
                getAccountsForCustomer();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
    }

    private void getAccountsForCustomer() {
        for(int i = 0;i<cust.getAccounts().length();i++){
            JSONObject accObj=null;
            try {
                 accObj=(JSONObject) cust.getAccounts().get(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                communicator.getAccountfromAccountID(accObj.getString("_id"),new Response.Listener<JSONObject>() {
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
                            cust.addAccount(acc);
                            Log.i("ACCOUNTS FOR CUSTOMER", acc.get_id()+ " "+acc.getNickname());

                            if(acc.getNickname().equalsIgnoreCase(keyAccountNickname)){
                                savingsExists=true;
                                cust.setRainyAccountID(acc.get_id());
                            }
                            if(acc.getType().equalsIgnoreCase("checking")){
                                checkingExists=true;
                            }
                            if(cust.getAccounts().length()==cust.getAccountList().size()){
                                //we are done instantiating everything SO NOW DO EVERYTHING ELSE
                                continueOnCreate();
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
        //Does Check exist?
        if(!checkingExists){
            //print out that a checking acc does not exist for this
            //finish this activity
            //finish();
            Log.i("checking dont exist","hello");
        }
        else{
            //if savings dont exist we need to create a new account for this customer and call it the name
            if(!savingsExists){
                Log.i("Made it make","hello");
                communicator.makeCustomerSavingsAcc(customerID, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String id=null;
                            if(response.getString("message").equalsIgnoreCase("Account created")){
                                JSONObject inner = response.getJSONObject("objectCreated");
                                id = inner.getString("_id");
                            }
                            cust.setRainyAccountID(id);
                            Log.i("MAKE SAVINGS ACCOUNT",cust.getRainyAccountID());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        savingsExist();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("onfailure", error.toString());
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
                    this.sleep(500000);
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
            communicator.getPurchasesForAccount(x.get_id(), new IndexHandler(i,this,1, cust.getAccountList().size()));

        }
        //cleanup big list

        //just grab 25 at random who cares
        //TODO:: fix it later



        //TODO:: change this 4:54am
        /*
        ArrayList<JSONObject> passToBrayPurch = new ArrayList<>();
        for(int i=0;i<25&&i<purchaseObjects.size();i++){
            passToBrayPurch.add(purchaseObjects.get(i));

        }*/
        //give to brayden

    }
    public void eatPurchaseList(ArrayList<JSONObject> list){
        Log.i("Straight chomping","hg");
        purchaseObjects.addAll(list);
    }
    public void continueWithTransfers() {

        for(int i=0;i<cust.getAccountList().size();i++) {
            Account x = cust.getAccountList().get(i);
            communicator.getTransfersForAccount(x.get_id(), new IndexHandler(i,this,2,cust.getAccountList().size()));
        }
    }


    public void doneWithBoth(){
            Log.i("BIGDOG", "hello");
            compareDataBaseToInformation();
    }



    //<purchase, transfer> or purchase transfer
    final double ROUNDUP_BOUND=5;
    boolean runOnce=true;
    private void compareDataBaseToInformation(){
        ArrayList<TransferDB> ourList = new ArrayList<>(db.transferDao().getAll());
        HashSet<String> idSet = new HashSet<>();
        int i=0;

        for(TransferDB x: ourList) {
            idSet.add(x.purchaseID);

            //make the purchase/transfer objects we already have in the database
            //TODO::
            //we have transaction and purchase id aswell as acc, just get the right thing and give info bray bray
            //basically get the transfer and purchases based on the transfer and purchase ids
            if(runOnce) {
                pTransfers.add(new PTransfer());
                adar.notifyDataSetChanged();
                communicator.getTransferFromTransferID(x.transferID, new DatabaseIndexHandler(i, this, 1));
                communicator.getPurchaseFromPurchaseID(x.purchaseID, new DatabaseIndexHandler(i, this, 2));

            }

            i++;
        }
        runOnce=false;
        for(JSONObject x: purchaseObjects){
            //Log.i("BUNCH OF JSON",x.toString());
            try {
                String purchaseDate = x.getString("purchase_date");
                String purchaseID = x.getString("_id");
                Integer amount = x.getInt("amount");
                String status = x.getString("status");
                String medium = x.getString("medium");
                String accountID=x.getString("payer_id");
                if(!idSet.contains(purchaseID)){
                    //IF THIS CALL RETURNS TRUE THEN WE ADDED TO A SET
                    Log.i("Process purchase called","helo");
                    processPurchase(x, amount,status,medium, accountID);
                }

            }catch(Exception e){e.printStackTrace();}
        }
    }

    public void handleTransferFromHandler(JSONObject transfer, int index){
        pTransfers.get(index).transfer=transfer;
    }
    public void handlePurchaseFromHandler(JSONObject purchase, int index){
        pTransfers.get(index).purchase=purchase;
    }


    // WE ONLY WANT PURCHASES MADE FROM LAST WEEK THAT ARE IN BOUND LIMITS THAT ARE "COMPLETED" STATUS
    private void processPurchase(JSONObject purchase,Integer amount, String status, String medium, String accountID){
        //TODO:: This method actually connects and makes a transfer for this purchase IF IT REQUIRES ONE
        //TODO:: figure out how to do the date filtering
        if(amount % ROUNDUP_BOUND!=0&&status.equals("executed")&&medium.equals("balance")){
            //room for rounding up
            int guess= amount;
            //add until we get to cap
            while(guess%ROUNDUP_BOUND!=0){
                guess++;
            }

            //once we have found the correct amount, and the purchase we want, make a transfer for it
            Log.i("make transfer for pu","helo");
            makeTransferForPurchase(purchase,guess-amount, accountID);

        }else{

        }

    }



    private void makeTransferForPurchase(JSONObject purchase, int transferAmount, String accountID) {

        String purchaseID="";
        try{
            purchaseID=purchase.getString("_id");
        }catch(Exception e){e.printStackTrace();}

        communicator.makeTransferFromAccount(accountID, cust.getRainyAccountID(), transferAmount, purchaseID,new TransferHandler(purchase, this));

                //make the transfer and then add to database
    }

    ArrayList<PTransfer> pTransfers = new ArrayList<>();
    public void updateDataBaseWithPurchase(JSONObject purchase, JSONObject response){
        //update database with purchase and transfer we just made, so we don't do it again
        //TODO:: update database so we dont duplicate stuff

        Log.i("REsponse transfer", response.toString());
        String transferID="";
        String accountID="";
        String transferDate="";
        String purchaseID="";
        String purchaseDate="";
        JSONObject inner=null;
        try {
            inner = response.getJSONObject("objectCreated");
            transferID=inner.getString("_id");
            accountID=inner.getString("payee_id");
            transferDate= inner.getString("transaction_date");
            purchaseID=purchase.getString("_id");
            purchaseDate=purchase.getString("purchase_date");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TransferDB transfer = new TransferDB(purchaseID,transferID,accountID,purchaseDate,transferDate);
        ArrayList<TransferDB> list= new ArrayList<>(db.transferDao().getAll());
        for(TransferDB x: list){
            Log.i("Transfer db x", x.transferID+" "+ x.purchaseID+" "+ x.accountID+" "+ x.purchaseDate+" "+ x.transferDate);
        }
        db.transferDao().insertAll(transfer);

        pTransfers.add(new PTransfer(purchase, inner));
        adar.notifyDataSetChanged();
    }

    private void setUpDataBase() {
        db = Room.databaseBuilder(this, AppDatabase.class, "app-database").allowMainThreadQueries().build();

    }

    public void eatTransferList(ArrayList<JSONObject> transferList) {
        transferObjects.addAll(transferList);
    }


    //remember to add the accounts to the database and store them also

}