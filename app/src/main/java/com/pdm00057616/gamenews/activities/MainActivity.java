package com.pdm00057616.gamenews.activities;

import android.arch.lifecycle.ViewModelProviders;
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
import com.pdm00057616.gamenews.database.entities_models.CategoryEntity;
import com.pdm00057616.gamenews.fragments.IndividualGameFragment;
import com.pdm00057616.gamenews.fragments.NewsViewFragment;
import com.pdm00057616.gamenews.viewmodels.CategoryViewModel;
import com.pdm00057616.gamenews.viewmodels.NewsViewModel;
import com.pdm00057616.gamenews.viewmodels.PlayerViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private CategoryViewModel categoryViewModel;
    private PlayerViewModel playerViewModel;
    private NewsViewModel newsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindAll();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return true;
    }

    private void bindViews() {
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_dehaze_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(item -> {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.all_news:
                    fragment = NewsViewFragment.newInstance(0);
                    break;
                case R.id.fav_news:
                    fragment = NewsViewFragment.newInstance(2);
                    break;
                case R.id.logout:
                    logout();
                    fragment = null;
                    break;
                default:
                    fragment = IndividualGameFragment.newInstance(item.getTitle().toString().toLowerCase());
                    break;
            }
            if (fragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_content, fragment)
                        .commit();
                drawerLayout.closeDrawers();
                item.setChecked(true);
                getSupportActionBar().setTitle(item.getTitle());
            }
            return true;
        });
    }

    private void addMenuItems(List<CategoryEntity> categories) {
        navigationView.getMenu().findItem(R.id.game_section).getSubMenu().clear();
        for (CategoryEntity x : categories) {
            navigationView
                    .getMenu().findItem(R.id.game_section)
                    .getSubMenu().add(x.getName().toUpperCase())
                    .setIcon(R.drawable.ic_videogame_24dp);
        }
    }


    private void bindAll(){
        if (isLogged()) {
            setContentView(R.layout.activity_main);
            newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
            playerViewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);
            categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
            categoryViewModel
                    .getAllCategories()
                    .observe(this, this::addMenuItems);
            ClientRequest.getCategories(getToken(), categoryViewModel);
            ClientRequest.getPlayers(getToken(), playerViewModel);
            ClientRequest.getCategories(getToken(), categoryViewModel);
            setUserID();
            bindViews();
            setFirstView();
        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }


    private boolean isLogged() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("log", MODE_PRIVATE);
        if (!sharedPreferences.contains("token")) {
            return false;
        } else {
            return true;
        }
    }

    private String getToken() {
        SharedPreferences preferences = this.getSharedPreferences("log", Context.MODE_PRIVATE);
        if (preferences.contains("token")) {
            return preferences.getString("token", "");
        }
        return "";
    }

    private void logout() {
        SharedPreferences preferences = this.getSharedPreferences("log", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void setUserID() {
        ClientRequest.getUserInfo(getToken(), this, newsViewModel);
    }

    private void setFirstView() {
        navigationView.getMenu().getItem(0).setChecked(true);
        Fragment fragment = NewsViewFragment.newInstance(0);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_content, fragment)
                .commit();
        getSupportActionBar().setTitle(navigationView.getMenu().getItem(0).getTitle());
    }
}
