package com.pdm00057616.gamenews.API;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pdm00057616.gamenews.API.deserializer.CategoriesDeserializer;
import com.pdm00057616.gamenews.API.deserializer.DeleteFavDeserializer;
import com.pdm00057616.gamenews.API.deserializer.PlayerDeserializer;
import com.pdm00057616.gamenews.API.deserializer.PushFavDeserializer;
import com.pdm00057616.gamenews.API.deserializer.TokenDeserializer;
import com.pdm00057616.gamenews.API.deserializer.UserDeserializer;
import com.pdm00057616.gamenews.activities.LoginActivity;
import com.pdm00057616.gamenews.activities.MainActivity;
import com.pdm00057616.gamenews.database.entities_models.CategoryEntity;
import com.pdm00057616.gamenews.database.entities_models.NewEntity;
import com.pdm00057616.gamenews.database.entities_models.PlayerEntity;
import com.pdm00057616.gamenews.models.Login;
import com.pdm00057616.gamenews.models.New;
import com.pdm00057616.gamenews.models.Player;
import com.pdm00057616.gamenews.models.User;
import com.pdm00057616.gamenews.utils.Converters;
import com.pdm00057616.gamenews.utils.SharedPreferencesUtils;
import com.pdm00057616.gamenews.viewmodels.CategoryViewModel;
import com.pdm00057616.gamenews.viewmodels.NewsViewModel;
import com.pdm00057616.gamenews.viewmodels.PlayerViewModel;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientRequest {

    private static String massage;

    private static GameNewsAPI getClient(Gson gson) {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor((chain) -> {
                    okhttp3.Response response = chain.proceed(chain.request());
                    if (response.code() == 401) {
                        massage = response.body().string();
                        return chain.proceed(chain.request());
                    }
                    return response;
                }).build();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(GameNewsAPI.END_POINT)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit = builder.build();
        return retrofit.create(GameNewsAPI.class);
    }

    public static void login(String user, String password, Activity context, RelativeLayout relativeLayout) {
        relativeLayout.setVisibility(View.GONE);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Login.class, new TokenDeserializer())
                .create();
        Call<Login> call = getClient(gson).
                login(user, password);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                System.out.println(response.code());
                if (response.code() == 200) {
                    Toast.makeText(context, "Exito", Toast.LENGTH_SHORT).show();
                    SharedPreferencesUtils.saveToken(context, response.body().getToken());
                    startMain(context);
                } else if (response.code() == 401) {
                    if (massage.matches("Contraseña")) {
                        relativeLayout.setVisibility(View.VISIBLE);
                        Toast.makeText(context, "Contraseña", Toast.LENGTH_SHORT).show();
                    } else {
                        relativeLayout.setVisibility(View.VISIBLE);
                        Toast.makeText(context, "Usuario", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    relativeLayout.setVisibility(View.VISIBLE);
                    System.out.println("something");
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(context, "Time out bitch", Toast.LENGTH_SHORT).show();
                    relativeLayout.setVisibility(View.VISIBLE);
                    t.printStackTrace();
                } else {
                    relativeLayout.setVisibility(View.VISIBLE);
                    Toast.makeText(context, "Something weird happend", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            }
        });
    }


    private static void startMain(Activity activity) {
        activity.startActivity(new Intent(activity, MainActivity.class));
        activity.finish();
    }

    private static void startLogin(Activity activity) {
        activity.startActivity(new Intent(activity, LoginActivity.class));
        activity.finish();
    }


    public static void fetchAllNews(Context context, NewsViewModel viewModel, String token, SwipeRefreshLayout... refreshLayout) {
        Call<List<New>> news = getClient(new Gson()).getNews("Beared " + token);
        news.enqueue(new Callback<List<New>>() {
            @Override
            public void onResponse(Call<List<New>> call, Response<List<New>> response) {
                int code = response.code();
                if (refreshLayout.length > 0) {
                    refreshLayout[0].setRefreshing(false);
                }
                if (code == 200) {
                    viewModel.pushAllFavs(token);
                    viewModel.delete();
                    setListNewEntity(response.body(), viewModel);
                    getUserInfo(token, context, viewModel);
                    Toast.makeText(context, "Fetching data", Toast.LENGTH_SHORT).show();
                } else if (code == 401) {
                    SharedPreferencesUtils.deleteSharePreferences(context);
                    startLogin((Activity) context);
                }
            }

            @Override
            public void onFailure(Call<List<New>> call, Throwable t) {
                if (refreshLayout.length > 0) {
                    refreshLayout[0].setRefreshing(false);
                }
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static void setListNewEntity(List<New> list, NewsViewModel viewModel) {
        for (New x : list) {
            NewEntity newEntity = new NewEntity(
                    x.get_id(), x.getTitle(), x.getCoverImage(), x.getDescription(),
                    x.getBody(), x.getGame(), Converters.fromDate(x.getCreated_date()),
                    0);
            viewModel.insert(newEntity);
        }
    }


    public static void getCategories(String token, CategoryViewModel viewModel) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ArrayList.class, new CategoriesDeserializer())
                .create();
        Call<List<String>> categories = getClient(gson).getCategories("Beared " + token);
        categories.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.body() != null) {
                    for (String x : response.body()) {
                        viewModel.insertCategory(new CategoryEntity(x));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
    }


    public static void getPlayers(String token, PlayerViewModel viewModel, SwipeRefreshLayout... refresh) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Player.class, new PlayerDeserializer())
                .create();
        Call<List<Player>> players = getClient(gson).getPlayers("Beared " + token);
        players.enqueue(new Callback<List<Player>>() {
            @Override
            public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
                if (response.code() == 200) {
                    for (Player x : response.body()) {
                        PlayerEntity player = new PlayerEntity(
                                x.get_id(), x.getAvatar(), x.getName(),
                                x.getBiografia(), x.getGame()
                        );
                        viewModel.insert(player);
                        if (refresh.length > 0) {
                            refresh[0].setRefreshing(false);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Player>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    public static void getUserInfo(String token, Context context, NewsViewModel repository) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(User.class, new UserDeserializer())
                .create();
        Call<User> userInfo = getClient(gson).getUserInfo("Beared " + token);
        userInfo.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    SharedPreferencesUtils.saveUserID(response.body().getId(), context);
                    System.out.println(response.body().getFavNews().size());
                    for (String x : response.body().getFavNews()) {
                        repository.update(1, x, token);
                    }
                } else {
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    public static void pushFav(String token, String newID) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(String.class, new PushFavDeserializer())
                .create();
        Call<String> call = getClient(gson).pushFav("Bearer " + token, newID);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                System.out.println(response.code() + " push");
                if (response.code() == 200) {
                    if (response.body().matches("true")) {
                        System.out.println("se hizo");
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static void deleteFav(String token, String newID) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(String.class, new DeleteFavDeserializer())
                .create();
        Call<String> call = getClient(gson).deleteFav(("Bearer " + token), newID);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                System.out.println(response.code());
                System.out.println(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
