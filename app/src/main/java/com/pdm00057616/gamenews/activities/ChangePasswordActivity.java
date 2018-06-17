package com.pdm00057616.gamenews.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.pdm00057616.gamenews.API.ClientRequest;
import com.pdm00057616.gamenews.R;
import com.pdm00057616.gamenews.utils.SharedPreferencesUtils;

public class ChangePasswordActivity extends AppCompatActivity {

    Button button;
    TextInputEditText newPassword, confirmPassword;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        bindViews();
    }

    private void bindViews() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.change_pass));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        newPassword = findViewById(R.id.new_password);
        confirmPassword = findViewById(R.id.confirm_password);
        button = findViewById(R.id.change_pass);
        button.setOnClickListener(v -> onClickList());
    }

    private void onClickList() {
        if (newPassword.getText().toString().equals("") && confirmPassword.getText().toString().equals("")) {
            Toast.makeText(this, getText(R.string.empty_fields_warning), Toast.LENGTH_SHORT).show();
        } else if (newPassword.getText().toString().equals(confirmPassword.getText().toString())) {
            ClientRequest.updateUser(SharedPreferencesUtils.getToken(this), SharedPreferencesUtils.getUserID(this), newPassword.getText().toString(), this);
        } else {
            Toast.makeText(this, getString(R.string.password_dont_match_warning), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }
}
