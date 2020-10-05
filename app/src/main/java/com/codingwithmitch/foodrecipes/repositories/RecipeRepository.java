package com.codingwithmitch.foodrecipes.repositories;

import android.content.Context;

import com.codingwithmitch.foodrecipes.AppExecutors;
import com.codingwithmitch.foodrecipes.models.Recipe;
import com.codingwithmitch.foodrecipes.persistence.RecipeDao;
import com.codingwithmitch.foodrecipes.persistence.RecipeDatabase;
import com.codingwithmitch.foodrecipes.requests.responses.ApiResponse;
import com.codingwithmitch.foodrecipes.requests.responses.RecipeSearchResponse;
import com.codingwithmitch.foodrecipes.util.NetworkBoundResource;
import com.codingwithmitch.foodrecipes.util.Resource;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

public class RecipeRepository {

    private static RecipeRepository instance;
    private RecipeDao recipeDao;

    public static RecipeRepository getInstance(Context context){
        if (instance == null){
            instance = new RecipeRepository(context);
        }
        return instance;
    }

    public RecipeRepository(Context context) {
        recipeDao = RecipeDatabase.getInstance(context).getRecipeDao();
    }

    // Return live data for searching the recipe API
    public LiveData<Resource<List<Recipe>>> searchRecipesApi(final String query , final int pageNumber){
        return new NetworkBoundResource<List<Recipe>, RecipeSearchResponse>(AppExecutors.getInstance()){

            @Override
            protected void saveCallResult(@NonNull RecipeSearchResponse item) {

            }

            @Override
            protected boolean shouldFetch(@Nullable List<Recipe> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<Recipe>> loadFromDb() {
                return recipeDao.searchRecipes(query , pageNumber);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RecipeSearchResponse>> createCall() {
                return null;
            }
        }.getAsLiveData();
    }
}
