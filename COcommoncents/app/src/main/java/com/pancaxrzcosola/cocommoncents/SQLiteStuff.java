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

    @Entity
    public class Account{
        @PrimaryKey
        public int uid;
        @ColumnInfo(name = "account_nmbr")
        public String accountNumber;
        @ColumnInfo(name = "type")
        public String type;
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

    @Database(entities = {Account.class}, version =1)
    public abstract class AppDatabase extends RoomDatabase {
        public abstract AccountDao accountDao();
    }

}
