package com.bohdanuhryn.food2fork.service;

import com.bohdanuhryn.food2fork.models.RecipeGet;
import com.bohdanuhryn.food2fork.models.RecipesList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by BohdanUhryn on 04.02.2016.
 */
public interface IF2fApi {

    @GET("/api/search")
    Call<RecipesList> getRecipesList(
            @Query("key") String key,
            @Query("q") String q,
            @Query("sort") String sort,
            @Query("page") int page
    );

    @GET("/api/get")
    Call<RecipeGet> getRecipeGet(
            @Query("key") String key,
            @Query("rId") long rId
    );

}
