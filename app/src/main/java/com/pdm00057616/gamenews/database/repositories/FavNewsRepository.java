package com.pdm00057616.gamenews.database.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.pdm00057616.gamenews.database.AppDB;
import com.pdm00057616.gamenews.database.daos.FavNewsDao;
import com.pdm00057616.gamenews.database.entities_models.FavNewsEntity;

import java.util.List;

public class FavNewsRepository {

    private FavNewsDao favNewsDao;

    public FavNewsRepository(Application application) {
        this.favNewsDao = AppDB.getInstance(application).favNewsDao();
    }

    public LiveData<List<FavNewsEntity>>getFavByUser(String user_id){
        return favNewsDao.getFavByUser(user_id);
    }

    public void delete(String user, String news){
        new DeleteAsync(favNewsDao).execute(user, news);
    }

    public void insert(String user, String news){
        new InsertAsync(favNewsDao).execute(new FavNewsEntity(user, news));
    }

    private static class InsertAsync extends AsyncTask<FavNewsEntity, Void, Void>{

        private FavNewsDao favNewsDao;

        private InsertAsync(FavNewsDao favNewsDao) {
            this.favNewsDao = favNewsDao;
        }

        @Override
        protected Void doInBackground(FavNewsEntity... favNewsEntities) {
            favNewsDao.insertFavNew(favNewsEntities[0]);
            return null;
        }
    }

    private static class DeleteAsync extends AsyncTask<String, Void, Void>{

        private FavNewsDao favNewsDao;

        private DeleteAsync(FavNewsDao favNewsDao) {
            this.favNewsDao = favNewsDao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            favNewsDao.deleteFav(strings[0], strings[1]);
            return null;
        }
    }

}
