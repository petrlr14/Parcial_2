package com.pdm00057616.gamenews.database.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pdm00057616.gamenews.API.GameNewsAPI;
import com.pdm00057616.gamenews.API.NewsDeserializer;
import com.pdm00057616.gamenews.database.AppDB;
import com.pdm00057616.gamenews.database.daos.NewDao;
import com.pdm00057616.gamenews.database.entities_models.NewEntity;
import com.pdm00057616.gamenews.models.New;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewRepository {

    private NewDao newDao;
    private GameNewsAPI gameNewsAPI;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public NewRepository(Application application) {
        AppDB db = AppDB.getInstance(application);
        newDao = db.newDao();
//        createAPIClient();
    }

    public LiveData<List<NewEntity>> getAllNews(/*String auth*/) {
    /*    compositeDisposable.add(gameNewsAPI.getNews("Bearerd "+auth)
        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
        subscribeWith(getNewsObserver()));*/
        return newDao.getAllNews();
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

/*    private void createAPIClient() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .registerTypeAdapter(GameNewsAPI.class, new NewsDeserializer())
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GameNewsAPI.END_POINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        gameNewsAPI = retrofit.create(GameNewsAPI.class);
    }

    private DisposableSingleObserver<List<New>> getNewsObserver(){
     return new DisposableSingleObserver<List<New>>() {
         @Override
         public void onSuccess(List<New> news) {
             if (!news.isEmpty()) {
                 for(New x:news){
                    insert(x);
                 }
             }
         }

         @Override
         public void onError(Throwable e) {
            e.getCause();
         }
     };
    }*/
}
