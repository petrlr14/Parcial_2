package com.pdm00057616.gamenews.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.pdm00057616.gamenews.database.entities_models.NewEntity;
import com.pdm00057616.gamenews.models.New;

import java.util.List;

@Dao
public interface NewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNew(NewEntity news);

    @Query("SELECT*FROM NewEntity ORDER BY crate_date DESC")
    LiveData<List<NewEntity>> getAllNews();

    @Query("SELECT*FROM NewEntity WHERE title like :query")
    LiveData<List<NewEntity>> getNewByQuery(String query);

    @Query("SELECT*FROM NewEntity WHERE game =:game")
    LiveData<List<NewEntity>> getNewsByGame(String game);

    @Query("UPDATE NewEntity SET isFav=:value WHERE _id=:id")
    void updateNew(int value, String id);

    @Query("SELECT*FROM NewEntity WHERE isFav=1")
    List<NewEntity> getAllNewsForFav();

    @Query("DELETE FROM NewEntity")
    void nukeTable();
}
