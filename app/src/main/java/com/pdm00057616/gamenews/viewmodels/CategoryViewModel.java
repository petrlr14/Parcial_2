package com.pdm00057616.gamenews.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.pdm00057616.gamenews.database.entities_models.CategoryEntity;
import com.pdm00057616.gamenews.database.repositories.CategoryRepository;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {

    private CategoryRepository categoryRepository;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        categoryRepository = new CategoryRepository(application);
    }

    public LiveData<List<CategoryEntity>> getAllCategories() {
        return categoryRepository.getCategories();
    }

    public void insertCategory(CategoryEntity categoryEntity) {
        categoryRepository.insert(categoryEntity);
    }
}
