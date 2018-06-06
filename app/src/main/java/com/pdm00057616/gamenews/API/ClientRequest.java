package com.pdm00057616.gamenews.API;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pdm00057616.gamenews.API.deserializer.CategoriesDeserializer;
import com.pdm00057616.gamenews.API.deserializer.PlayerDeserializer;
import com.pdm00057616.gamenews.API.deserializer.TokenDeserializer;
import com.pdm00057616.gamenews.activities.MainActivity;
import com.pdm00057616.gamenews.database.entities_models.CategoryEntity;
import com.pdm00057616.gamenews.database.entities_models.NewEntity;
import com.pdm00057616.gamenews.database.entities_models.PlayerEntity;
import com.pdm00057616.gamenews.models.Login;
import com.pdm00057616.gamenews.models.New;
import com.pdm00057616.gamenews.models.Player;
import com.pdm00057616.gamenews.viewmodels.CategoryViewModel;
import com.pdm00057616.gamenews.viewmodels.NewsViewModel;
import com.pdm00057616.gamenews.viewmodels.PlayerViewModel;

import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.View.GONE;

public class ClientRequest {

    public static void login(String user, String password, Activity context, RelativeLayout relativeLayout) {
        relativeLayout.setVisibility(View.VISIBLE);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Login.class, new TokenDeserializer())
                .create();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(GameNewsAPI.END_POINT)
                .addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit = builder.build();
        GameNewsAPI gameNewsAPI = retrofit.create(GameNewsAPI.class);
        Call<Login> call = gameNewsAPI.
                login(user, password);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                System.out.println(response.code());
                if (response.isSuccessful() && response.body().isOKResponse()) {
                    Toast.makeText(context, "Exito", Toast.LENGTH_SHORT).show();
                    saveToken(context, response.body().getToken());
                    startMain(context);
                } else if (!response.body().isOKResponse()) {
                    relativeLayout.setVisibility(View.GONE);
                    Toast.makeText(context, response.body().getToken(), Toast.LENGTH_SHORT).show();
                } else {
                    relativeLayout.setVisibility(View.GONE);
                    Toast.makeText(context, "Something weird happend", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(context, "Time out bitch", Toast.LENGTH_SHORT).show();
                    relativeLayout.setVisibility(GONE);
                    t.printStackTrace();
                }
            }
        });
    }

    private static void saveToken(Context context, String token) {
        SharedPreferences preferences = context.getSharedPreferences("log", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", token);
        editor.commit();
    }

    private static void startMain(Activity activity) {
        activity.startActivity(new Intent(activity, MainActivity.class));
        activity.finish();
    }


    public static void fetchAllNews(Context context, NewsViewModel viewModel, String token, SwipeRefreshLayout refreshLayout) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GameNewsAPI.END_POINT)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        GameNewsAPI service = retrofit.create(GameNewsAPI.class);
        Call<List<New>> news = service.getNews("Beared " + token);
        news.enqueue(new Callback<List<New>>() {
            @Override
            public void onResponse(Call<List<New>> call, Response<List<New>> response) {
                refreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    setListNewEntity(response.body(), viewModel);
                    Toast.makeText(context, "Fetching data", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<New>> call, Throwable t) {
                refreshLayout.setRefreshing(false);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static void setListNewEntity(List<New> list, NewsViewModel viewModel) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        try {
            for (New li : list) {
                li.setCreatedDate(df.parse(li.getCreated_date()));
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        Collections.sort(list);
        Collections.reverse(list);
        for (New x : list) {
            NewEntity newEntity = new NewEntity(
                    x.get_id(), x.getTitle(), x.getCoverImage(), x.getDescription(),
                    x.getBody(), x.getGame(), x.getCreated_date()
            );
            viewModel.insert(newEntity);
        }
    }


    public static void getCategories(String token, CategoryViewModel viewModel){
        Gson gson=new GsonBuilder()
                .registerTypeAdapter(ArrayList.class, new CategoriesDeserializer())
                .create();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(GameNewsAPI.END_POINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        GameNewsAPI service=retrofit.create(GameNewsAPI.class);
        Call<List<String>> categories=service.getCategories("Beared " + token);
        categories.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                System.out.println(response.code());
                if (response.body()!=null) {
                    for(String x:response.body()){
                        viewModel.insertCategory(new CategoryEntity(x));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
    }


    public static void getPlayers(String token, PlayerViewModel viewModel){
        Gson gson=new GsonBuilder()
                .registerTypeAdapter(Player.class, new PlayerDeserializer())
                .create();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(GameNewsAPI.END_POINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        GameNewsAPI service=retrofit.create(GameNewsAPI.class);
        Call<List<Player>> players=service.getPlayers("Beared "+token);
        players.enqueue(new Callback<List<Player>>() {
            @Override
            public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
                if(response.code()==200){
                    for (Player x : response.body()) {
                        System.out.println(x.get_id());
                        PlayerEntity player = new PlayerEntity(
                                x.get_id(), x.getAvatar(), x.getName(),
                                x.getBiografia(), x.getGame()
                        );
                        viewModel.insert(player);

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Player>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
