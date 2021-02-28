package com.pancaxrzcosola.cocommoncents;

import org.json.JSONObject;

import java.util.ArrayList;

public class Customer {
    private String _id;
    private String first_name;
    private String last_name;
    private JSONObject address;
    private ArrayList<JSONObject> list;

    public Customer(){
        this._id = "";
        this.first_name = "";
        this.last_name = "";
        this.address = null;
    }

    public Customer(String _id, String first_name, String last_name, JSONObject address) {
        this._id = _id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public JSONObject getAddress() {
        return address;
    }

    public void setAddress(JSONObject address) {
        this.address = address;
    }

    public ArrayList<JSONObject> getList() {
        return list;
    }

    public void setList(ArrayList<JSONObject> list) {
        this.list = list;
    }
}