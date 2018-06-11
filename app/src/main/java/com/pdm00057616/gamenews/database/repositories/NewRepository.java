package com.pdm00057616.gamenews.database.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.pdm00057616.gamenews.API.ClientRequest;
import com.pdm00057616.gamenews.API.GameNewsAPI;
import com.pdm00057616.gamenews.database.AppDB;
import com.pdm00057616.gamenews.database.daos.NewDao;
import com.pdm00057616.gamenews.database.entities_models.NewEntity;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class NewRepository {

    private NewDao newDao;

    public NewRepository(Application application) {
        AppDB db = AppDB.getInstance(application);
        newDao = db.newDao();
    }

    public LiveData<List<NewEntity>> getAllNews() {
        return newDao.getAllNews();
    }

    public LiveData<List<NewEntity>> getNewsByQuery(String query){
        return newDao.getNewByQuery(query);
    }

    public LiveData<List<NewEntity>> getNewsByGame(String game){
        return newDao.getNewsByGame(game);
    }

    public void update(int fav, String id, String token, String user_id){
        new updateAsyncTask(newDao, id, user_id, token).execute(fav);
    }

    public void insert(NewEntity news){
        new insertAsyncTask(newDao).execute(news);
    }

    private static class insertAsyncTask extends AsyncTask<NewEntity, Void, Void>{

        private NewDao newDao;

        public insertAsyncTask(NewDao newDao) {
            this.newDao = newDao;
        }

        @Override
        protected Void doInBackground(NewEntity... news) {
            newDao.insertNew(news[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Integer, Void, Void>{

        NewDao newDao;
        String id, user_id, token;

        public updateAsyncTask(NewDao newDao, String id, String user_id, String token) {
            this.newDao = newDao;
            this.id=token;
            this.user_id=id;
            this.token=user_id;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            newDao.updateNew(integers[0], id);
            if(integers[0]==1){
                ClientRequest.pushFav(token, user_id, id);
            }
            return null;
        }
    }
}
