package com.pdm00057616.gamenews.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.pdm00057616.gamenews.API.ClientRequest;
import com.pdm00057616.gamenews.R;
import com.pdm00057616.gamenews.utils.SharedPreferencesUtils;

public class ChangePasswordActivity extends AppCompatActivity {

    Button button;
    TextInputEditText newPassword, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        bindViews();
    }

    private void bindViews(){
        newPassword=findViewById(R.id.new_password);
        confirmPassword=findViewById(R.id.confirm_password);
        button=findViewById(R.id.change_pass);
        button.setOnClickListener(v->onClickList());
    }

    private void onClickList(){
        if(newPassword.getText().toString().equals("")&&confirmPassword.getText().toString().equals("")){
            Toast.makeText(this, "Fields must not be empty", Toast.LENGTH_SHORT).show();
        }else{
            ClientRequest.updateUser(getToken(), SharedPreferencesUtils.getUserID(this), newPassword.getText().toString());
        }
    }

    private String getToken() {
        SharedPreferences preferences = this.getSharedPreferences("log", Context.MODE_PRIVATE);
        if (preferences.contains("token")) {
            return preferences.getString("token", "");
        }
        return "";
    }
}
