package com.pdm00057616.gamenews.models;

public class New {

    private String _id, title, body, game, created_date, coverImage;
    private int __v;

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

    public int get__v() {
        return __v;
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

    public void set__v(int __v) {
        this.__v = __v;
    }

    public String getCover_image() {
        return coverImage;
    }

    public void setCover_image(String cover_image) {
        this.coverImage = cover_image;
    }
}
