package com.pancaxrzcosola.cocommoncents.sqlitestuff;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {AccountDB.class,TransferDB.class}, version =1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AccountDao accountDao();
    public abstract TransferDao transferDao();
}