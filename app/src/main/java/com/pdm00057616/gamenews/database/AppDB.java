package com.pdm00057616.gamenews.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.pdm00057616.gamenews.database.daos.CategoryDao;
import com.pdm00057616.gamenews.database.daos.NewDao;
import com.pdm00057616.gamenews.database.daos.PlayerDao;
import com.pdm00057616.gamenews.database.entities_models.CategoryEntity;
import com.pdm00057616.gamenews.database.entities_models.NewEntity;
import com.pdm00057616.gamenews.database.entities_models.PlayerEntity;

@Database(
        entities = {PlayerEntity.class, NewEntity.class, CategoryEntity.class},
        exportSchema = false, version = 1)
public abstract class AppDB extends RoomDatabase {

    private static volatile AppDB db;
    private static final String DB_NAME = "gameNews.db";

    public static synchronized AppDB getInstance(Context context) {
        if (db == null) {
            db = createDB(context);
        }
        return db;
    }

    private static AppDB createDB(Context context) {
        return Room.
                databaseBuilder(context, AppDB.class, DB_NAME)
                .build();
    }

    public abstract NewDao newDao();

    public abstract CategoryDao categoryDao();

    public abstract PlayerDao playerDao();

}
