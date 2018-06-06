package com.pdm00057616.gamenews.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.pdm00057616.gamenews.database.entities_models.PlayerEntity;
import com.pdm00057616.gamenews.database.repositories.PlayerRepository;

import java.util.List;

public class PlayerViewModel extends AndroidViewModel {

    private PlayerRepository repository;

    public PlayerViewModel(@NonNull Application application) {
        super(application);
        repository=new PlayerRepository(application);
    }

    public LiveData<List<PlayerEntity>> getPlayersByGame(String game){
        return repository.getPlayersByGame(game);
    }

    public void insert(PlayerEntity playerEntity){
        repository.insertPlayer(playerEntity);
    }
}
