package com.pdm00057616.gamenews.database.entities_models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class PlayerEntity {

    @NonNull
    @PrimaryKey
    private String id;
    private String avatar, name, bio, game;

    public PlayerEntity(@NonNull String id, String avatar, String name, String bio, String game) {
        this.id = id;
        this.avatar = avatar;
        this.name = name;
        this.bio = bio;
        this.game = game;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }
}
