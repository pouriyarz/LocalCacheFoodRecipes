package com.codingwithmitch.foodrecipes.persistence;

import android.content.Context;

import com.codingwithmitch.foodrecipes.models.Recipe;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Recipe.class} , version = 1)
@TypeConverters({Converters.class})
public abstract class RecipeDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "recupes_db";
    private static RecipeDatabase instance;

    public static RecipeDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext() ,
                    RecipeDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract RecipeDao getRecipeDao();
}
