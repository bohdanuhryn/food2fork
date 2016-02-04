package com.bohdanuhryn.food2fork.service;

import com.bohdanuhryn.food2fork.models.Recipe;
import com.bohdanuhryn.food2fork.models.SearchResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by BohdanUhryn on 04.02.2016.
 */
public interface IApiMethods {

    @GET("/api/search")
    Call<SearchResponse> getRecipesList(
            @Query("key") String key
    );

    @GET("/api/get")
    Call<Recipe> getRecipe(
            @Query("key") String key,
            @Query("rId") long id
    );

}
