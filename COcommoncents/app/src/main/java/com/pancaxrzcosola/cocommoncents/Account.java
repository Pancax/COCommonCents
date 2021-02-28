package com.pancaxrzcosola.cocommoncents;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Account {
    private String _id;
    private String type;
    private String nickname;
    private int rewards;
    private double balance;
    private String customer_id;
    private JSONArray bill_ids;

    public Account(String _id, String type, String nickname, int rewards, int balance, String customer_id) {
        this._id = _id;
        this.type = type;
        this.nickname = nickname;
        this.rewards = rewards;
        this.balance = balance;
        this.customer_id = customer_id;
    }

    public Account() {
        this._id = "";
        this.type = "";
        this.nickname = "";
        this.rewards = 0;
        this.balance = 0;
        this.customer_id = "";
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getRewards() {
        return rewards;
    }

    public void setRewards(int rewards) {
        this.rewards = rewards;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public JSONArray getBill_ids() {
        return bill_ids;
    }

    public void setBill_ids(JSONArray bill_ids) {
        this.bill_ids = bill_ids;
    }
}
