package com.pancaxrzcosola.cocommoncents;

import android.content.Context;



import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.android.volley.Request.Method.GET;

public class ServerCommunicator {

    private RequestQueue queue = null;
    final private String API_KEY ="37e93a473684febe1afbe773dbe31637";
    final private String CUSTOMER_ID_URL="http://api.nessieisreal.com/enterprise/customers/";//+id ad the end


    public ServerCommunicator(Context c){
        queue = Volley.newRequestQueue(c);
    }

    public void getCustomerForID(String id, Response.Listener<JSONObject> list, Response.ErrorListener eList){
        JSONObject send = new JSONObject();
        JsonObjectRequest request = new JsonObjectRequest(GET, CUSTOMER_ID_URL+id+"?key="+API_KEY, null, list,eList);
        queue.add(request);
    }


}
