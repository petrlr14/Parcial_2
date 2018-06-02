package com.pdm00057616.gamenews;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pdm00057616.gamenews.API.TokenDeserializer;
import com.pdm00057616.gamenews.API.GameNewsAPI;
import com.pdm00057616.gamenews.fragments.AllViewFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        getToken();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void bindViews() {
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_dehaze_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener((item) -> {
                    Fragment fragment;
                    switch (item.getItemId()){
                        default:
                            fragment=new AllViewFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_content, fragment)
                            .commit();
                    drawerLayout.closeDrawers();
                    item.setChecked(true);
                    return true;
                }
        );
    }

    /*private void getToken(){
        Gson gson=new GsonBuilder()
                .registerTypeAdapter(String.class, new TokenDeserializer())
                .create();
        Retrofit.Builder builder=new Retrofit.Builder()
                .baseUrl("http://gamenewsuca.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit=builder.build();
        GameNewsAPI gameNewsAPI=retrofit.create(GameNewsAPI.class);
        String credenciales="00057616";
        Call<String> call=gameNewsAPI.login(credenciales, credenciales);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                System.out.println(response.code());
                if (response.isSuccessful()) {
                    preferences(response.body());
                }else
                    Toast.makeText(MainActivity.this, "Error_no funciona el login", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(MainActivity.this, "Error no funciona el login 2", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void preferences(String token){
        SharedPreferences preferences=this.getSharedPreferences("log", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("token", token);
        editor.commit();
    }*/

    /*private void init() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gamenewsuca.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        GameNewsAPI service = retrofit.create(GameNewsAPI.class);
        *//*Call<List<New>> news=service.getNews();
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
        });*//*
     *//*final Call<List<User>> users = service.getUser();
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
        });*//*
    }*/
}
