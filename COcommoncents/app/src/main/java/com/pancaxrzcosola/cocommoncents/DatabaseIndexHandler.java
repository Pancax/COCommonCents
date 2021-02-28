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
    int bigSize;
    boolean TReady=false;
    boolean PReady=false;
    public DatabaseIndexHandler(int index, HomeActivity caller, int type, int bigSize){
        this.caller=caller;
        this.index=index;
        this.type=type;
        this.bigSize=bigSize;
        this.list=new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if((DatabaseIndexHandler.this).type==1){
                    (DatabaseIndexHandler.this).caller.handleTransferFromHandler(response,(DatabaseIndexHandler.this).index);
                    if((DatabaseIndexHandler.this).index==(DatabaseIndexHandler.this).bigSize-1){
                        TReady=true;
                        if(PReady){
                            (DatabaseIndexHandler.this).caller.pushToBig();
                        }
                    }
                }else{
                    (DatabaseIndexHandler.this).caller.handlePurchaseFromHandler(response,(DatabaseIndexHandler.this).index);
                    if((DatabaseIndexHandler.this).index==(DatabaseIndexHandler.this).bigSize-1){
                        PReady=true;
                        if(TReady){
                            (DatabaseIndexHandler.this).caller.pushToBig();
                        }
                    }
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
