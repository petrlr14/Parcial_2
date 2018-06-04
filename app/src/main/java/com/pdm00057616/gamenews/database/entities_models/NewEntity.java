package com.pdm00057616.gamenews.database.entities_models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Comparator;

@Entity
public class NewEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "_id")
    private String id;

    public NewEntity(@NonNull String id, String title, String coverImage, String description, String body, String game, String createDate) {
        this.id = id;
        this.title = title;
        this.coverImage = coverImage;
        this.description = description;
        this.body = body;
        this.game = game;
        this.createDate = createDate;
    }

    private String title, coverImage, description, body, game;
    @ColumnInfo(name = "crate_date")
    private String createDate;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
