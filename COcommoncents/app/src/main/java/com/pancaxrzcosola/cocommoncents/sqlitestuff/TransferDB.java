package com.pancaxrzcosola.cocommoncents.sqlitestuff;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TransferDB {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    //purchase id
    @ColumnInfo(name = "purchase_id")
    public String purchaseID;

    //transfer associated with purchase (when we round up) id
    @ColumnInfo(name = "transfer_id")
    public String transferID;

    //account of checkings associated with this purchase/transfer
    @ColumnInfo(name = "account_id")
    public String accountID;

    public TransferDB(String purchaseID, String transferID,String accountID){
        this.purchaseID=purchaseID;
        this.transferID=transferID;
        this.accountID=accountID;
    }
}