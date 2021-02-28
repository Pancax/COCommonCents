package com.pancaxrzcosola.cocommoncents;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

public class TransferHandler  {

    String purchaseID;
    Response.Listener<JSONObject> list;
    Response.ErrorListener eList;
    HomeActivity caller;

    public TransferHandler(String id, Response.Listener<JSONObject> list, Response.ErrorListener eList){
        this.purchaseID=id;
        this.list=list;
        this.eList=eList;

    }
    public TransferHandler(String id, HomeActivity caller){
        this.caller=caller;
        this.purchaseID=id;
        this.list=new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                (TransferHandler.this).caller.updateDataBaseWithPurchase(purchaseID,response);
            }
        };
        this.eList=new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
    }


}
