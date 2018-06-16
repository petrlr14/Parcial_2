package com.pdm00057616.gamenews.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.pdm00057616.gamenews.API.ClientRequest;
import com.pdm00057616.gamenews.R;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText editTextUser, editTextPassword;
    private Button button;
    private RelativeLayout relativeLayout;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindViews();
    }

    private void bindViews() {
        editTextUser = findViewById(R.id.edit_text_username);
        editTextPassword = findViewById(R.id.edit_text_password);
        button = findViewById(R.id.login_button);
        relativeLayout = findViewById(R.id.relative_progress);
        progressBar=findViewById(R.id.login_progress);
        button.setOnClickListener(v -> buttonAction());
    }

    private void buttonAction() {
        if (editTextUser.getText().toString().equals("") || editTextPassword.getText().toString().equals("")) {
            Toast.makeText(this, "Fields Must not be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        ClientRequest.login(editTextUser.getText().toString(), editTextPassword.getText().toString(), this, relativeLayout, progressBar, true);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

