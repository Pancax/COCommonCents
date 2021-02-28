package com.pancaxrzcosola.cocommoncents.sqlitestuff;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AccountDB {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    //accountNumber of this account
    @ColumnInfo(name = "account_nmbr")
    public String accountNumber;
    //type checkings or savings
    //there can be any number of checking BUT ONLY 1 savings
    @ColumnInfo(name = "type")
    public String type;
    //customer id associated with this acc
    @ColumnInfo(name = "customer_id")
    public String customerID;

    public AccountDB(String accountNumber, String type,String customerID){
        this.accountNumber=accountNumber;
        this.type=type;
        this.customerID=customerID;
    }
}