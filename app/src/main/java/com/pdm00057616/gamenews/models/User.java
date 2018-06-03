package com.pdm00057616.gamenews.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {
    @SerializedName("_id")
    private String id;
    @SerializedName("created_date")
    private String date;
    @SerializedName("favoriteNews")
    private List<New> favNews;

    private String user;
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<New> getFavNews() {
        return favNews;
    }

    public void setFavNews(List<New> favNews) {
        this.favNews = favNews;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
