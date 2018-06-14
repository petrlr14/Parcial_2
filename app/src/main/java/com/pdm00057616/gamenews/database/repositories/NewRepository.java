package com.pdm00057616.gamenews.database.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.pdm00057616.gamenews.API.ClientRequest;
import com.pdm00057616.gamenews.database.AppDB;
import com.pdm00057616.gamenews.database.daos.NewDao;
import com.pdm00057616.gamenews.database.entities_models.NewEntity;

import java.util.List;

public class NewRepository {

    private NewDao newDao;

    public NewRepository(Application application) {
        AppDB db = AppDB.getInstance(application);
        newDao = db.newDao();
    }

    public LiveData<List<NewEntity>> getAllNews() {
        return newDao.getAllNews();
    }

    public void deleteNews() {
        new deleteTableAsyncTask(newDao).execute();
    }

    public LiveData<List<NewEntity>> getNewsByQuery(String query) {
        return newDao.getNewByQuery(query);
    }

    public LiveData<List<NewEntity>> getNewsByGame(String game) {
        return newDao.getNewsByGame(game);
    }

    public void update(int fav, String id, String token) {
        new updateAsyncTask(newDao, id, token).execute(fav);
    }

    public void pushAllFavs(String token) {
        new pushAsyncTask(newDao, token).execute();
    }

    public void insert(NewEntity news) {
        new insertAsyncTask(newDao).execute(news);
    }

    private static class insertAsyncTask extends AsyncTask<NewEntity, Void, Void> {

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

    private static class updateAsyncTask extends AsyncTask<Integer, Void, Void> {

        NewDao newDao;
        String id, token;

        public updateAsyncTask(NewDao newDao, String id, String token) {
            this.newDao = newDao;
            this.token = token;
            this.id = id;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            newDao.updateNew(integers[0], id);
            return null;
        }
    }

    private static class deleteTableAsyncTask extends AsyncTask<Void, Void, Void> {

        NewDao newDao;

        public deleteTableAsyncTask(NewDao newDao) {
            this.newDao = newDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            newDao.nukeTable();
            return null;
        }
    }

    private static class pushAsyncTask extends AsyncTask<String, Void, List<NewEntity>> {

        NewDao newdao;
        String token;

        public pushAsyncTask(NewDao newdao, String token) {
            this.newdao = newdao;
            this.token = token;
        }

        @Override
        protected List<NewEntity> doInBackground(String... strings) {
            return newdao.getAllNewsForFav();
        }

        @Override
        protected void onPostExecute(List<NewEntity> aVoid) {
            super.onPostExecute(aVoid);
            for (NewEntity x : aVoid) {
                ClientRequest.pushFav(token, x.getId());
            }
        }
    }
}
