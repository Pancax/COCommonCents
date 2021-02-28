package com.pancaxrzcosola.cocommoncents;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.RoomDatabase;

import java.util.List;

public class SQLiteStuff {


    //all accounts of type checking in this database are the checkings we are going to be examining the purchase history and making transfers based off of
    @Entity
    public class Account{
        @PrimaryKey
        public int uid;
        @ColumnInfo(name = "account_nmbr")
        public String accountNumber;
        @ColumnInfo(name = "type")
        public String type;
    }

    //transfers are here to matchup purchases we have already rounded up as well as to save that information to be displayed across runs
    @Entity
    public class Transfer{
        @PrimaryKey
        public int uid;
        @ColumnInfo(name = "later")
        public String later;
        ////
        ////
    }
    @Dao
    public interface AccountDao{
        @Query("SELECT * FROM account")
        List<Account> getAll();

        @Insert
        void insertALl(Account... accounts);

        @Delete
        void delete(Account account);
    }
    @Dao
    public interface TransferDao{
        ////
        ////
    }

    @Database(entities = {Account.class}, version =1)
    public abstract class AppDatabase extends RoomDatabase {
        public abstract AccountDao accountDao();
        public abstract TransferDao transferDao();
    }

}
