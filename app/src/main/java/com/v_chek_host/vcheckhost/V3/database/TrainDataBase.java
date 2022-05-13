package com.v_chek_host.vcheckhost.V3.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TrainModel.class}, version = 1)
public abstract class TrainDataBase extends RoomDatabase {
    public abstract Dao dao();
}
