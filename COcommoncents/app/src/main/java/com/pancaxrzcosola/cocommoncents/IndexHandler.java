package com.pancaxrzcosola.cocommoncents;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class IndexHandler {

    int index;
    Response.Listener<JSONArray> list;
    Response.ErrorListener eList;
    HomeActivity caller;
    int type;
    int bigSize;
    public IndexHandler(int index, Response.Listener<JSONArray> list, Response.ErrorListener eList, int type){
        this.index=index;
        this.list=list;
        this.eList=eList;

    }
    public IndexHandler(int index, HomeActivity caller, int type, int bigSize){
        this.caller=caller;
        this.index=index;
        this.type=type;
        this.bigSize=bigSize;
        this.list=new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if((IndexHandler.this).type==1){
                    //Log.i("COOL RESPONSE", response.toString());

                    ArrayList<JSONObject> purchaseList = new ArrayList<JSONObject>();
                    for(int j=0;j<response.length();j++){
                            try {
                                purchaseList.add(response.getJSONObject(j));
                            } catch (JSONException e) {
                            e.printStackTrace();}
                    }

                    (IndexHandler.this).caller.eatPurchaseList(purchaseList);
                    if((IndexHandler.this).index==((IndexHandler.this).bigSize-1)){
                        (IndexHandler.this).caller.continueWithTransfers();
                    }

                }else{
                    //handle transfer
                    //Log.i("WE MADE IT BROTHER","HELLO");
                    Log.i("T_COOL_RESPONSE", response.toString());
                    ArrayList<JSONObject> transferList = new ArrayList<JSONObject>();
                    for(int j=0;j<response.length();j++){
                        try {
                            transferList.add(response.getJSONObject(j));
                        } catch (JSONException e) {
                            e.printStackTrace();}
                    }

                    (IndexHandler.this).caller.eatTransferList(transferList);
                    if((IndexHandler.this).index==((IndexHandler.this).bigSize-1)){
                        (IndexHandler.this).caller.doneWithBoth();
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
