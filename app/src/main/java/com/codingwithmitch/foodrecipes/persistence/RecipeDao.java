package com.codingwithmitch.foodrecipes.persistence;

import com.codingwithmitch.foodrecipes.models.Recipe;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao  // database access object
public interface RecipeDao {

    @Insert(onConflict = IGNORE)
    long[] insertRecipes(Recipe... recipes);
    /** for example : { id1 , id2 , id3.. etc }
        conflict in 1 & 3
        { -1 , id2 , -1 ,  }
     **/

    @Insert(onConflict = REPLACE)
    void insertRecipe(Recipe recipe);

    @Query("UPDATE recipes SET title = :title, publisher = :publisher, image_url = :image_url, social_rank = :social_rank " +
                    "WHERE recipe_id = :recipe_id")
    void updateRecipe(String recipe_id , String title , String publisher ,
                                        String image_url , float social_rank);

    @Query("SELECT * FROM recipes WHERE title LIKE '%' || :query || '%' OR ingredients LIKE '%' || :query || '%' " +
                    "ORDER BY social_rank DESC LIMIT (:pageNumber * 30)")
    LiveData<List<Recipe>> searchRecipes(String query , int pageNumber);
    /** 0 - 29 , PAGE 1
        0 - 58 , PAGE 2
        0 - 87 , PAGE 3
     **/

    @Query("SELECT * FROM recipes WHERE recipe_id = :recipe_id")
    LiveData<Recipe> getRecipe(String recipe_id);
}
