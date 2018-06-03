package com.pdm00057616.gamenews.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.pdm00057616.gamenews.database.entities_models.UserEntity;

@Dao
public interface UserDao {

    @Insert
    void insertUser(UserEntity user);

    @Query("SELECT*FROM UserEntity WHERE user like :username")
    UserEntity getUserByUsername(String username);

}
