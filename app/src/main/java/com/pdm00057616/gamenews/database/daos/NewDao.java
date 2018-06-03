package com.pdm00057616.gamenews.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.pdm00057616.gamenews.models.New;

@Dao
public interface NewDao {

    @Insert
    void insertNew(New news);

}
