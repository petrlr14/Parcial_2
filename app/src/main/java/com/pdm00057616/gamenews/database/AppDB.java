package com.pdm00057616.gamenews.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import com.pdm00057616.gamenews.database.daos.CategoryDao;
import com.pdm00057616.gamenews.database.daos.FavNewsDao;
import com.pdm00057616.gamenews.database.daos.NewDao;
import com.pdm00057616.gamenews.database.daos.PlayerDao;
import com.pdm00057616.gamenews.database.daos.UserDao;
import com.pdm00057616.gamenews.database.entities_models.CategoryEntity;
import com.pdm00057616.gamenews.database.entities_models.FavNewsEntity;
import com.pdm00057616.gamenews.database.entities_models.NewEntity;
import com.pdm00057616.gamenews.database.entities_models.PlayerEntity;
import com.pdm00057616.gamenews.database.entities_models.UserEntity;

@Database(
        entities = { PlayerEntity.class,FavNewsEntity.class, NewEntity.class, UserEntity.class, CategoryEntity.class},
        exportSchema = false, version = 3)
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
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .build();
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE PlayerEntity(id TEXT primary key not null," +
                    "avatar TEXT, name TEXT, bio TEXT, game TEXT)");
        }
    };

    public abstract FavNewsDao favNewsDao();

    public abstract NewDao newDao();

    public abstract UserDao userDao();

    public abstract CategoryDao categoryDao();

    public abstract PlayerDao playerDao();

}
