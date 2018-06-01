package com.pdm00057616.gamenews.API;

import com.pdm00057616.gamenews.models.New;
import com.pdm00057616.gamenews.models.User;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GameNewsAPI {

    @GET("/news")
    Call<List<New>> getNews();

    @GET("/users")
    Call<List<User>> getUser();

}
