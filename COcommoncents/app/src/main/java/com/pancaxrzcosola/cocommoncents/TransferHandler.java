package com.pancaxrzcosola.cocommoncents;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.pancaxrzcosola.cocommoncents.sqlitestuff.TransferDB;

import org.json.JSONObject;

public class TransferHandler  {


    Response.Listener<JSONObject> list;
    Response.ErrorListener eList;
    HomeActivity caller;
    JSONObject purchase;

    public TransferHandler(String id, Response.Listener<JSONObject> list, Response.ErrorListener eList){
        this.list=list;
        this.eList=eList;

    }
    public TransferHandler(JSONObject purchase, HomeActivity caller){
        this.caller=caller;
        this.purchase=purchase;
        this.list=new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                (TransferHandler.this).caller.updateDataBaseWithPurchase((TransferHandler.this).purchase, response);
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
