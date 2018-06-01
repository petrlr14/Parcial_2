package com.pdm00057616.gamenews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.pdm00057616.gamenews.API.GameNewsAPI;
import com.pdm00057616.gamenews.models.New;
import com.pdm00057616.gamenews.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    private TextView textView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textview_);
        imageView=findViewById(R.id.imageview);
        init();
    }

    private void init() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gamenewsuca.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        GameNewsAPI service = retrofit.create(GameNewsAPI.class);
        /*Call<List<New>> news=service.getNews();
        news.enqueue(new Callback<List<New>>() {
            @Override
            public void onResponse(Call<List<New>> call, Response<List<New>> response) {
                List<New> news1=response.body();
                String y="";
                if(news1!=null)
                    Glide.with(getBaseContext())
                    .load(news1.get(3).getCover_image()).into(imageView);
            }

            @Override
            public void onFailure(Call<List<New>> call, Throwable t) {
                t.printStackTrace();
            }
        });*/
        /*final Call<List<User>> users = service.getUser();
        users.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> usersList;
                String aux = "";
                usersList=response.body();
                if(usersList!=null){
                    for (User x : usersList) {
                        aux += x.getUser()+"---"+x.getFavNews().size()+"\n";
                        textView.setText(aux);
                    }
                }

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                t.printStackTrace();
            }
        });*/
    }
}
