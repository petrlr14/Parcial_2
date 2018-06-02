package com.pdm00057616.gamenews.API;

import com.pdm00057616.gamenews.models.New;
import com.pdm00057616.gamenews.models.User;

import java.util.List;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface GameNewsAPI {

    @GET("/news")
    Call<List<New>> getNews();

    @GET("/users")
    Call<List<User>> getUser();

    @FormUrlEncoded
    @POST("/login")
    Call<String> login(
            @Field("user") String username,
            @Field("password") String password
    );

}
