package com.pdm00057616.gamenews.API;

import com.pdm00057616.gamenews.models.Login;
import com.pdm00057616.gamenews.models.New;
import com.pdm00057616.gamenews.models.Player;
import com.pdm00057616.gamenews.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface GameNewsAPI {

    static final String END_POINT = "http://gamenewsuca.herokuapp.com";

    @FormUrlEncoded
    @POST("/login")
    Call<Login> login(
            @Field("user") String username,
            @Field("password") String password
    );

    @GET("/news")
    Call<List<New>> getNews(@Header("Authorization") String auth);

    @GET("users/detail")
    Call<User> getUserInfo(@Header("Authorization") String auth);

    @GET("/news/type/list")
    Call<List<String>> getCategories(@Header("Authorization") String auth);

    @GET("/players")
    Call<List<Player>> getPlayers(@Header("Authorization")String auth);

}
