package com.pancaxrzcosola.cocommoncents;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Customer {
    private String _id;
    private String first_name;
    private String last_name;
    private JSONArray accounts;
    private JSONObject address;
    private ArrayList<JSONObject> list;
    private ArrayList<Account> accountList;


    private String rainyAccountID="";

    public Customer(){
        this._id = "";
        this.first_name = "";
        this.last_name = "";
        this.address = null;
        accountList=new ArrayList<>();
    }

    public Customer(String _id, String first_name, String last_name, JSONObject address) {
        this._id = _id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
    }


    public void setRainyAccountID(String id){
        this.rainyAccountID=id;
    }
    public String getRainyAccountID(){
        return this.rainyAccountID;
    }
    public void addAccount(Account acc){
        accountList.add(acc);
    }
    public ArrayList<Account> getAccountList(){
        return accountList;
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

    public JSONArray getAccounts() {
        return accounts;
    }

    public void setAccounts(JSONArray accounts) {
        this.accounts = accounts;
    }
}
