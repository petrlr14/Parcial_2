package com.pdm00057616.gamenews.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.pdm00057616.gamenews.database.entities_models.CategoryEntity;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert
    void insetCategory(CategoryEntity categoryEntity);

    @Query("SELECT*FROM CategoryEntity")
    LiveData<List<CategoryEntity>> getAllCategories();

}
