package com.pdm00057616.gamenews.database.repositories;

import android.app.Application;
import android.os.AsyncTask;

import com.pdm00057616.gamenews.database.AppDB;
import com.pdm00057616.gamenews.database.daos.UserDao;
import com.pdm00057616.gamenews.database.entities_models.UserEntity;
import com.pdm00057616.gamenews.models.User;

public class UserRepository {

    private UserDao userDao;

    public UserRepository(Application application) {
        this.userDao = AppDB.getInstance(application).userDao();
    }

    public void insert(UserEntity user){
        new InsertUserAsyncTask(userDao).execute(user);
    }

    private static class InsertUserAsyncTask extends AsyncTask<UserEntity, Void, Void>{

        private UserDao userDao;

        private InsertUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(UserEntity... users) {
            userDao.insertUser(users[0]);
            return null;
        }
    }
}
