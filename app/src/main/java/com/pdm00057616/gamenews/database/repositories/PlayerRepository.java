package com.pdm00057616.gamenews.database.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.pdm00057616.gamenews.database.AppDB;
import com.pdm00057616.gamenews.database.daos.PlayerDao;
import com.pdm00057616.gamenews.database.entities_models.PlayerEntity;

import java.util.List;

public class PlayerRepository {

    private PlayerDao playerDao;

    public PlayerRepository(Application application) {
        AppDB db = AppDB.getInstance(application);
        playerDao = db.playerDao();
    }

    public LiveData<List<PlayerEntity>> getPlayersByGame(String game) {
        return playerDao.getPlayer(game);
    }

    public void insertPlayer(PlayerEntity playerEntity) {
        new InsertAsyncTask(playerDao).execute(playerEntity);
    }

    private static class InsertAsyncTask extends AsyncTask<PlayerEntity, Void, Void> {

        private PlayerDao playerDao;

        private InsertAsyncTask(PlayerDao playerDao) {
            this.playerDao = playerDao;
        }

        @Override
        protected Void doInBackground(PlayerEntity... playerEntities) {
            playerDao.insertPlayer(playerEntities[0]);
            return null;
        }
    }
}
