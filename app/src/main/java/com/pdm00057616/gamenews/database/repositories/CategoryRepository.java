package com.pdm00057616.gamenews.database.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.pdm00057616.gamenews.database.AppDB;
import com.pdm00057616.gamenews.database.daos.CategoryDao;
import com.pdm00057616.gamenews.database.entities_models.CategoryEntity;

import java.util.List;

public class CategoryRepository {

    private CategoryDao categoryDao;

    public CategoryRepository(Application application) {
        AppDB db = AppDB.getInstance(application);
        this.categoryDao = db.categoryDao();
    }

    public LiveData<List<CategoryEntity>> getCategories() {
        return categoryDao.getAllCategories();
    }

    public void insert(CategoryEntity categoryEntity) {
        new InsertCategoryAsyncTask(categoryDao).execute(categoryEntity);
    }

    private static class InsertCategoryAsyncTask extends AsyncTask<CategoryEntity, Void, Void> {

        private CategoryDao categoryDao;

        private InsertCategoryAsyncTask(CategoryDao categoryDao) {
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(CategoryEntity... categoryEntities) {
            categoryDao.insetCategory(categoryEntities[0]);
            return null;
        }
    }

}
