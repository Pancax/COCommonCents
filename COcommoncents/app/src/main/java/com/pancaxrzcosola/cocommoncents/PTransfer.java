package com.pancaxrzcosola.cocommoncents;

import org.json.JSONObject;

public class PTransfer {
    JSONObject purchase;
    JSONObject transfer;

    public PTransfer(){
        purchase=null;
        transfer=null;
    }
    public PTransfer(JSONObject purchase, JSONObject transfer){
        this.purchase=purchase;
        this.transfer=transfer;
    }
}
