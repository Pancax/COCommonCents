package com.pancaxrzcosola.cocommoncents;

import android.content.Context;



import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;

public class ServerCommunicator {

    private RequestQueue queue = null;
    final private String API_KEY ="37e93a473684febe1afbe773dbe31637";
    final private String CUSTOMER_ID_URL="http://api.nessieisreal.com/customers/";//+id ad the end
    final private String CUSTOMER_ADD_URL="http://api.nessieisreal.com/customers";
    final private String ACCOUNT_ID_URL="http://api.nessieisreal.com/accounts/";//+id
    final private String MAKE_SAVINGS_ACCOUNT_URL="http://api.nessieisreal.com/customers/";//+id+"/accounts"
    final private String GET_PURCHASES_FOR_ACCOUNT_URL="http://api.nessieisreal.com/accounts/";//+id+"/purchases"
    final private String GET_TRANSFERS_FOR_ACCOUNT_URL="http://api.nessieisreal.com/accounts/";//+id+/transfers
    final private String MAKE_TRANSFER_FROM_ACCOUNT_URL="http://api.nessieisreal.com/accounts/";//+id+"/transfers"



    public ServerCommunicator(Context c){
        queue = Volley.newRequestQueue(c);
    }

    public void getCustomerForID(String id, Response.Listener<JSONObject> list, Response.ErrorListener eList){
        JSONObject send = new JSONObject();
        JsonObjectRequest request = new JsonObjectRequest(GET, CUSTOMER_ID_URL+id+"?key="+API_KEY, null, list,eList);
        queue.add(request);
    }

    public void makeCustomerAcc(String firstName, String lastName, JSONObject address, Response.Listener<JSONObject> list, Response.ErrorListener eList){

        JSONObject body = new JSONObject();
        try {
            body.put("first_name",firstName);
            body.put("last_name",lastName);
            body.put("address",address);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(POST, CUSTOMER_ADD_URL+"?key="+API_KEY, body, list, eList);
        queue.add(request);
    }

    public void getAccountfromAccountID(String id, Response.Listener<JSONObject> list, Response.ErrorListener eList){
        JSONObject send = new JSONObject();
        JsonObjectRequest request = new JsonObjectRequest(GET, ACCOUNT_ID_URL+id+"?key="+API_KEY,null,list,eList);
        queue.add(request);
    }

    public void makeCustomerSavingsAcc(String customerID, Response.Listener<JSONObject> list, Response.ErrorListener eList){
        JSONObject accBody=new JSONObject();
        try{
            accBody.put("type","Savings");
            accBody.put("nickname", HomeActivity.keyAccountNickname);
            accBody.put("rewards",0);
            accBody.put("balance",0);
        }catch(Exception e){e.printStackTrace();}
        JsonObjectRequest request = new JsonObjectRequest(POST, MAKE_SAVINGS_ACCOUNT_URL+customerID+"/accounts"+"?key="+API_KEY,accBody,list,eList);
        queue.add(request);
    }

    public void getPurchasesForAccount(String accountId, Response.Listener<JSONArray> list, Response.ErrorListener eList){

        JsonArrayRequest request = new JsonArrayRequest(GET, GET_PURCHASES_FOR_ACCOUNT_URL+accountId+"/purchases"+"?key="+API_KEY,null,list,eList);
        queue.add(request);
    }
    public void getTransfersForAccount(String accountId, Response.Listener<JSONArray> list, Response.ErrorListener eList){
        JsonArrayRequest request = new JsonArrayRequest(GET, GET_TRANSFERS_FOR_ACCOUNT_URL+accountId+"/transfers"+"?key="+API_KEY,null,list,eList);
        queue.add(request);
    }


    public void makeTransferFromAccount(String accountId, String savingsAccountId, int transferAmount, String purchaseID, TransferHandler tranHand){
        JSONObject body= new JSONObject();

        try{
            body.put("medium","balance");
            body.put("payee_id", savingsAccountId);
            body.put("transaction_date","2021-02-28"); //TODO:: figur eout to way generate this date format
            body.put("status", "pending");
            body.put("amount",(double)transferAmount);
            body.put("description", "Automatic transfer of funds due to Common Cents");
        }catch(Exception e){
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(POST, MAKE_TRANSFER_FROM_ACCOUNT_URL+accountId+"/transfers"+"?key="+API_KEY,null,tranHand.list,tranHand.eList);
        queue.add(request);
    }
}
