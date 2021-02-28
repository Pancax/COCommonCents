package com.pancaxrzcosola.cocommoncents.sqlitestuff;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.pancaxrzcosola.cocommoncents.Account;

import java.util.List;

@Dao
public interface AccountDao{
    @Query("SELECT * FROM AccountDB")
    List<AccountDB> getAll();

    @Insert
    void insertAll(AccountDB accounts);

    @Delete
    void delete(AccountDB account);
}
