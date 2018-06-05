package com.pdm00057616.gamenews.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.pdm00057616.gamenews.API.ClientRequest;
import com.pdm00057616.gamenews.R;
import com.pdm00057616.gamenews.fragments.AllViewFragment;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLogged();
        if (Picasso.get() == null) {
            Picasso picasso = new Picasso.
                    Builder(getApplicationContext())
                    .indicatorsEnabled(true)
                    .build();
            Picasso.setSingletonInstance(picasso);
        }
        ClientRequest.getCategories(getToken());
        setContentView(R.layout.activity_main);
        bindViews();
        setFirstView();
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
                    switch (item.getItemId()) {
                        default:
                            fragment = new AllViewFragment();
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
    private void setFirstView(){
        navigationView.getMenu().getItem(0).setChecked(true);
        Fragment fragment = new AllViewFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_content, fragment)
                .commit();
        getSupportActionBar().setTitle(navigationView.getMenu().getItem(0).getTitle());
    }

    private void isLogged(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("log", MODE_PRIVATE);
        if (!sharedPreferences.contains("token")) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
    private String getToken(){
        SharedPreferences preferences = this.getSharedPreferences("log", Context.MODE_PRIVATE);
        if (preferences.contains("token")) {
            return preferences.getString("token", "");
        }
        return "";
    }
}
