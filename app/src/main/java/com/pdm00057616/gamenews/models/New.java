package com.pdm00057616.gamenews.models;

import java.util.Date;

public class New implements Comparable<New> {

    private String _id;
    private String title;
    private String body;
    private String game;
    private String created_date;
    private String coverImage;
    private Date createdDate;

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    private String description;

    @Override
    public String toString() {
        return title + "--" + game;
    }

    public String get_id() {
        return _id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getGame() {
        return game;
    }

    public String getCreated_date() {
        return created_date;
    }


    public void set_id(String _id) {
        this._id = _id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }


    public String getCover_image() {
        return coverImage;
    }

    public void setCover_image(String cover_image) {
        this.coverImage = cover_image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }


    @Override
    public int compareTo(New o) {
        return getCreatedDate().compareTo(o.getCreatedDate());
    }

}
