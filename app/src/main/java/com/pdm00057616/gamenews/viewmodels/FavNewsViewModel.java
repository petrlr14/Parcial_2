package com.pdm00057616.gamenews.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.pdm00057616.gamenews.database.entities_models.FavNewsEntity;
import com.pdm00057616.gamenews.database.repositories.FavNewsRepository;

import java.util.List;

public class FavNewsViewModel extends AndroidViewModel{

    FavNewsRepository repository;

    public FavNewsViewModel(@NonNull Application application) {
        super(application);
        repository=new FavNewsRepository(application);
    }
    public LiveData<List<FavNewsEntity>> getFavByUser(String user){
        return repository.getFavByUser(user);
    }

    public void insert(String user, String news){
        repository.insert(user, news);
    }

    public void delete(String user, String news){
        repository.delete(user, news);
    }
}
