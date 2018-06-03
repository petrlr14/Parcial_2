package com.pdm00057616.gamenews.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.pdm00057616.gamenews.models.New;

import java.util.List;

@Dao
public interface NewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNew(New news);

    @Query("SELECT*FROM NewEntity")
    LiveData<List<New>> getAllNews();

    @Query("SELECT*FROM NewEntity WHERE game=:game")
    LiveData<List<New>> getNewByGame(String game);
}
