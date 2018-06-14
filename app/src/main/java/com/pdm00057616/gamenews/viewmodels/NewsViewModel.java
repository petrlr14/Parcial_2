package com.pdm00057616.gamenews.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import com.pdm00057616.gamenews.API.ClientRequest;
import com.pdm00057616.gamenews.database.entities_models.NewEntity;
import com.pdm00057616.gamenews.database.repositories.NewRepository;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {

    private NewRepository repository;

    public NewsViewModel(@NonNull Application application) {
        super(application);
        repository = new NewRepository(application);
    }

    public LiveData<List<NewEntity>> getAllNews() {
        return repository.getAllNews();
    }

    public LiveData<List<NewEntity>> getNewsByQuery(String query) {
        return repository.getNewsByQuery(query);
    }

    public LiveData<List<NewEntity>> getNewsByGame(String game) {
        return repository.getNewsByGame(game);
    }

    public void update(int fav, String id, String token) {
        repository.update(fav, id, token);
    }

    public void insert(NewEntity news) {
        repository.insert(news);
    }

    public void delete(){repository.deleteNews();}


}
