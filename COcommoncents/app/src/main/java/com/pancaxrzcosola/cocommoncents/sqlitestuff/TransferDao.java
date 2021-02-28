package com.pancaxrzcosola.cocommoncents.sqlitestuff;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TransferDao{

    @Query("SELECT * FROM TransferDB")
    List<TransferDB> getAll();

    @Insert
    void insertAll(TransferDB... transferDBS);

    @Delete
    void delete(TransferDB transferDB);
}