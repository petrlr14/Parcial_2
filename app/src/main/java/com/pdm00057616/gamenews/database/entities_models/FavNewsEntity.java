package com.pdm00057616.gamenews.database.entities_models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity
        (foreignKeys = {
                @ForeignKey(
                        entity = UserEntity.class,
                        parentColumns = "_id",
                        childColumns = "user_id",
                        onDelete = CASCADE),
                @ForeignKey(entity = NewEntity.class,
                        parentColumns = "_id",
                        childColumns = "new_id",
                        onDelete = CASCADE)},
                primaryKeys = {"user_id", "new_id"})

public class FavNewsEntity {
    @ColumnInfo(name = "user_id")
    private String userID;

    @ColumnInfo(name = "new_id")
    private String newID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getNewID() {
        return newID;
    }

    public void setNewID(String newID) {
        this.newID = newID;
    }
}
