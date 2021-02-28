package com.pancaxrzcosola.cocommoncents;

import android.provider.ContactsContract;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DatabaseIndexHandler {

    int index;
    Response.Listener<JSONObject> list;
    Response.ErrorListener eList;
    HomeActivity caller;
    int type;

    public DatabaseIndexHandler(int index, HomeActivity caller, int type){
        this.caller=caller;
        this.index=index;
        this.type=type;
        this.list=new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if((DatabaseIndexHandler.this).type==1){
                    (DatabaseIndexHandler.this).caller.handleTransferFromHandler(response,(DatabaseIndexHandler.this).index);
                }else{
                    (DatabaseIndexHandler.this).caller.handlePurchaseFromHandler(response,(DatabaseIndexHandler.this).index);
                }
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
