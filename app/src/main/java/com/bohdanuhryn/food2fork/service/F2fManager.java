package com.bohdanuhryn.food2fork.service;

import android.util.Log;

import com.bohdanuhryn.food2fork.models.Recipe;
import com.bohdanuhryn.food2fork.models.RecipeGet;
import com.bohdanuhryn.food2fork.models.RecipeSearchParams;
import com.bohdanuhryn.food2fork.models.RecipesList;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by BohdanUhryn on 04.02.2016.
 */
public class F2fManager {

    private static final String TAG = "F2fManager";

    public static final String API_KEY = "7b09c51bbc0b0986ec85980cc3212d58";

    public static final String BASE_URL = "http://food2fork.com";

    private static Retrofit retrofit;

    public static void init() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RecipesList getRecipesList(RecipeSearchParams recipeSearchParams) {
        RecipesList list = null;
        if (retrofit == null) {
            init();
        }
        if (retrofit != null) {
            try {
                IF2fApi apiService = retrofit.create(IF2fApi.class);
                if (recipeSearchParams == null) {
                    recipeSearchParams = new RecipeSearchParams();
                }
                Call<RecipesList> call = apiService.getRecipesList(
                        API_KEY,
                        recipeSearchParams.query,
                        recipeSearchParams.sort,
                        recipeSearchParams.page
                );
                Response<RecipesList> response = call.execute();
                list = response.body();
            } catch (IOException e) {
                Log.e(TAG, "getRecipesList method error! " + e.getMessage());
            }
        }
        return list;
    }

    public static Recipe getRecipe(long id) {
        Recipe recipe = null;
        if (retrofit == null) {
            init();
        }
        if (retrofit != null) {
            try {
                IF2fApi apiService = retrofit.create(IF2fApi.class);
                Call<RecipeGet> call = apiService.getRecipeGet(API_KEY, id);
                Response<RecipeGet> response = call.execute();
                recipe = response.body().recipe;
            } catch (IOException e) {
                Log.e(TAG, "getRecipe method error! " + e.getMessage());
            }
        }
        return recipe;
    }

}
