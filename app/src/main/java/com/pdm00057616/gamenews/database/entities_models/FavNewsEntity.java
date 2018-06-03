package com.pdm00057616.gamenews.database.entities_models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity
        (indices = {@Index("new_id"), @Index("user_id")},
                foreignKeys = {
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
    @NonNull
    @ColumnInfo(name = "user_id")
    private String userID;
    @NonNull
    @ColumnInfo(name = "new_id")
    private String newID;

    public FavNewsEntity(@NonNull String userID, @NonNull String newID) {
        this.userID = userID;
        this.newID = newID;
    }

    @NonNull
    public String getUserID() {
        return userID;
    }

    public void setUserID(@NonNull String userID) {
        this.userID = userID;
    }

    @NonNull
    public String getNewID() {
        return newID;
    }

    public void setNewID(@NonNull String newID) {
        this.newID = newID;
    }
}
