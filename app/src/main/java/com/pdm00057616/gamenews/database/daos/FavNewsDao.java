package com.pdm00057616.gamenews.database.daos;

import android.arch.persistence.room.Insert;

import com.pdm00057616.gamenews.database.entities_models.FavNewsEntity;

public interface FavNewsDao {

    @Insert
    void insertFavNew(FavNewsEntity favNew);

}