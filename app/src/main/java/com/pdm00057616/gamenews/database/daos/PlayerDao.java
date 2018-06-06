package com.pdm00057616.gamenews.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.pdm00057616.gamenews.database.entities_models.PlayerEntity;

import java.util.List;

@Dao
public interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPlayer(PlayerEntity playerEntity);

    @Query("SELECT*FROM PlayerEntity WHERE game=:game")
    LiveData<List<PlayerEntity>> getPlayer(String game);

}
