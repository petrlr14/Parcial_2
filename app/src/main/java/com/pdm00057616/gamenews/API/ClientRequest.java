package com.pdm00057616.gamenews.API;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ProgressBar;
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
import com.pdm00057616.gamenews.R;
import com.pdm00057616.gamenews.activities.ChangePasswordActivity;
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

    public static void login(String user, String password, Activity context, RelativeLayout relativeLayout, ProgressBar progress, boolean bool) {
        relativeLayout.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
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
                    Toast.makeText(context, context.getString(R.string.succeed_login), Toast.LENGTH_SHORT).show();
                    SharedPreferencesUtils.saveToken(context, response.body().getToken());
                    startAct(context, bool);
                } else if (response.code() == 401) {
                    if (massage.matches("Contraseña")) {
                        relativeLayout.setVisibility(View.VISIBLE);
                        progress.setVisibility(View.GONE);
                        Toast.makeText(context, context.getString(R.string.wrong_pass), Toast.LENGTH_SHORT).show();
                    } else {
                        relativeLayout.setVisibility(View.VISIBLE);
                        progress.setVisibility(View.GONE);
                        Toast.makeText(context, context.getString(R.string.wrong_user), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    relativeLayout.setVisibility(View.VISIBLE);
                    Toast.makeText(context, context.getString(R.string.something_bad), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(context, context.getString(R.string.time_out), Toast.LENGTH_SHORT).show();
                    relativeLayout.setVisibility(View.VISIBLE);
                } else {
                    relativeLayout.setVisibility(View.VISIBLE);
                    Toast.makeText(context, context.getString(R.string.something_bad), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private static void startAct(Activity activity, boolean bool) {
        activity.startActivity(new Intent(activity, bool ? MainActivity.class : ChangePasswordActivity.class));
        if (bool)
            activity.finish();
    }

    private static void startLogin(Activity activity) {
        activity.startActivity(new Intent(activity, LoginActivity.class));
        activity.finish();
    }


    public static void fetchAllNews(Context context, NewsViewModel viewModel, String token, SwipeRefreshLayout... refreshLayout) {
        Call<List<New>> news = getClient(new Gson()).getNews("Bearer " + token);
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
                    Toast.makeText(context, context.getString(R.string.fetching_data), Toast.LENGTH_SHORT).show();
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


    public static void getCategories(String token, CategoryViewModel viewModel, Context context) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ArrayList.class, new CategoriesDeserializer())
                .create();
        Call<List<String>> categories = getClient(gson).getCategories("Bearer " + token);
        categories.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        for (String x : response.body()) {
                            viewModel.insertCategory(new CategoryEntity(x));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(context, context.getString(R.string.time_out), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, context.getString(R.string.something_bad), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public static void getPlayers(String token, PlayerViewModel viewModel, Context context, SwipeRefreshLayout... refresh) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Player.class, new PlayerDeserializer())
                .create();
        Call<List<Player>> players = getClient(gson).getPlayers("Bearer " + token);
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
                } else if (response.code() == 401) {
                    Toast.makeText(context, context.getString(R.string.session_expired), Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, LoginActivity.class));
                    ((Activity) context).finish();
                }
            }

            @Override
            public void onFailure(Call<List<Player>> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(context, context.getString(R.string.time_out), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, context.getString(R.string.something_bad), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public static void getUserInfo(String token, Context context, NewsViewModel repository) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(User.class, new UserDeserializer())
                .create();
        Call<User> userInfo = getClient(gson).getUserInfo("Bearer " + token);
        userInfo.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    SharedPreferencesUtils.saveUserID(response.body().getId(), context);
                    for (String x : response.body().getFavNews()) {
                        repository.update(1, x, token);
                    }
                } else {
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(context, context.getString(R.string.time_out), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, context.getString(R.string.something_bad), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public static void pushFav(String token, String newID, Context... context) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(String.class, new PushFavDeserializer())
                .create();
        Call<String> call = getClient(gson).pushFav("Bearer " + token, newID);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (context.length > 0) {
                    if (response.code() == 401) {
                        Toast.makeText(context[0], context[0].getString(R.string.session_expired), Toast.LENGTH_LONG).show();
                        context[0].startActivity(new Intent(context[0], LoginActivity.class));
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(context[0], context[0].getString(R.string.time_out), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context[0], context[0].getString(R.string.something_bad), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void deleteFav(String token, String newID, Context... context) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(String.class, new DeleteFavDeserializer())
                .create();
        Call<String> call = getClient(gson).deleteFav(("Bearer " + token), newID);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (context.length > 0) {
                    if (response.code() == 401) {
                        Toast.makeText(context[0], context[0].getString(R.string.session_expired), Toast.LENGTH_LONG).show();
                        context[0].startActivity(new Intent(context[0], LoginActivity.class));
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(context[0], context[0].getString(R.string.time_out), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context[0], context[0].getString(R.string.something_bad), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void updateUser(String token, String idUser, String newPassword, Activity activity) {
        Call<Void> call = getClient(new Gson()).updateUser("Bearer " + token, idUser, newPassword);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    SharedPreferencesUtils.deleteSharePreferences(activity);
                    Toast.makeText(activity, activity.getString(R.string.success_new_password), Toast.LENGTH_SHORT).show();
                    activity.startActivity(new Intent(activity, LoginActivity.class));
                    activity.finish();
                } else {
                    Toast.makeText(activity, activity.getString(R.string.something_bad), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(activity, activity.getString(R.string.time_out), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, activity.getString(R.string.something_bad), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
