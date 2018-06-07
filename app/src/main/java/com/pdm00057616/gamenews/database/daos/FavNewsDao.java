package com.pdm00057616.gamenews.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.pdm00057616.gamenews.database.entities_models.FavNewsEntity;

import java.util.List;

@Dao
public interface FavNewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavNew(FavNewsEntity favNew);

    @Query("SELECT*FROM FavNewsEntity WHERE user_id=:user_id")
    LiveData<List<FavNewsEntity>> getFavByUser(String user_id);

    @Query("DELETE FROM FavNewsEntity where (user_id=:user and new_id=:news)")
    void deleteFav(String user, String news);

}
