package com.bohdanuhryn.food2fork.service;

import com.bohdanuhryn.food2fork.models.RecipeGet;
import com.bohdanuhryn.food2fork.models.RecipeSearchParams;
import com.bohdanuhryn.food2fork.models.RecipesList;

import retrofit2.Call;
import retrofit2.GsonConverterFactory;
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

    public static Call<RecipesList> getRecipesList(RecipeSearchParams recipeSearchParams) {
        Call<RecipesList> call = null;
        if (retrofit == null) {
            init();
        }
        if (retrofit != null) {
            IF2fApi apiService = retrofit.create(IF2fApi.class);
            if (recipeSearchParams == null) {
                recipeSearchParams = new RecipeSearchParams();
            }
            call = apiService.getRecipesList(
                    API_KEY,
                    recipeSearchParams.query,
                    recipeSearchParams.sort,
                    recipeSearchParams.page
            );
        }
        return call;
    }

    public static Call<RecipeGet> getRecipe(String id) {
        Call<RecipeGet> call = null;
        if (retrofit == null) {
            init();
        }
        if (retrofit != null) {
            IF2fApi apiService = retrofit.create(IF2fApi.class);
            call = apiService.getRecipeGet(API_KEY, id);
        }
        return call;
    }

}
