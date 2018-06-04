package com.pdm00057616.gamenews.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pdm00057616.gamenews.API.GameNewsAPI;
import com.pdm00057616.gamenews.API.TokenDeserializer;
import com.pdm00057616.gamenews.R;
import com.pdm00057616.gamenews.models.Login;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.View.GONE;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText editTextUser, editTextPassword;
    private Button button;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindViews();
    }

    private void bindViews() {
        editTextUser = findViewById(R.id.edit_text_username);
        editTextPassword = findViewById(R.id.edit_text_password);
        button = findViewById(R.id.sign_in_button);
        relativeLayout = findViewById(R.id.relative_progress);
        button.setOnClickListener(v -> buttonAction());
    }

    private void buttonAction() {
        if (editTextUser.getText().toString().equals("") || editTextPassword.getText().toString().equals("")) {
            Toast.makeText(this, "Fields Must not be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        log();

    }

    private void log() {
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
                login(editTextUser.getText().toString(), editTextPassword.getText().toString());
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.isSuccessful()&&response.body().isOKResponse()) {
                    Toast.makeText(LoginActivity.this, "Succed", Toast.LENGTH_SHORT).show();
                    preferences(response.body().getToken());
                    startMain();
                }else if (!response.body().isOKResponse()){
                    relativeLayout.setVisibility(GONE);
                    Toast.makeText(LoginActivity.this, response.body().getToken(), Toast.LENGTH_SHORT).show();
                }else{
                    relativeLayout.setVisibility(GONE);
                    Toast.makeText(LoginActivity.this, "Something weird happend", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    relativeLayout.setVisibility(GONE);
                    Toast.makeText(LoginActivity.this, "Time out bitch", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void preferences(String token) {
        SharedPreferences preferences = this.getSharedPreferences("log", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", token);
        editor.commit();
    }

    private void startMain(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}

