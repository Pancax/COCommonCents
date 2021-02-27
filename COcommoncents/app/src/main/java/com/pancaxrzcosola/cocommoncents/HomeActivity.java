package com.pancaxrzcosola.cocommoncents;

import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.reimaginebanking.api.nessieandroidsdk.NessieError;
import com.reimaginebanking.api.nessieandroidsdk.NessieResultsListener;
import com.reimaginebanking.api.nessieandroidsdk.constants.AccountType;
import com.reimaginebanking.api.nessieandroidsdk.models.Account;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity {
    /*
    private TextView mTextView;
    private final String keyAccountNickname = "COCCUTDHKTN2021";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mTextView = (TextView) findViewById(R.id.text);

        Data initialization and creation
        final Customer cust = new Customer();
        String savingsvalue = "";
        MainActivity.nessieAPI.CUSTOMER.getCustomers(new NessieResultsListener() {
            @Override
            public void onSuccess(Object result) {
                JSONObject customer = (JSONObject) result;
                try {
                    cust.set_id(customer.getString("_id"));
                    cust.setFirst_name(customer.getString("first_name"));
                    cust.setLast_name(customer.getString("last_name"));
                    cust.setAddress(customer.getJSONObject("address"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(NessieError error) {
                Log.d("get1","Failed to obtain Customer!");
            }
        });

        MainActivity.nessieAPI.ACCOUNT.getCustomerAccounts(cust.get_id(), new NessieResultsListener() {
            @Override
            public void onSuccess(Object result) {
                cust.setList((ArrayList<JSONObject>) result);
            }

            @Override
            public void onFailure(NessieError error) {
                Log.d("get2","Failed to obtain Account Data!");
            }
        });


        //Check if Savings and Checking account exists
        boolean savingsExists = false, checkingExists = false;
        for(JSONObject son : cust.getList()){
            try {
                if (son.getString("nickname").compareToIgnoreCase(keyAccountNickname)==0) {
                    savingsExists=true;
                }
                if (son.getString("type").compareToIgnoreCase("Checking")==0) {
                    checkingExists=true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Does Check exist?
        if(!checkingExists){
            savingsvalue = "Checking Account Not Found!";
        }
        //Generate Savings if false
        else{
            if(!savingsExists){
                Account account = new Account.Builder().type(AccountType.SAVINGS).nickname(keyAccountNickname).rewards(0).balance(0).accountNumber(cust.get_id()+"0").build();
                MainActivity.nessieAPI.ACCOUNT.createAccount(cust.get_id(), account, new NessieResultsListener() {
                    @Override
                    public void onSuccess(Object result) {

                    }

                    @Override
                    public void onFailure(NessieError error) {

                    }
                });
            }
        }

    }*/

}