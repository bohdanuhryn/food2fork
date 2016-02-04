package com.bohdanuhryn.food2fork.service;

import android.util.Log;

import com.bohdanuhryn.food2fork.models.Recipe;
import com.bohdanuhryn.food2fork.models.SearchResponse;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by BohdanUhryn on 04.02.2016.
 */
public class ServiceManager {

    private static final String TAG = "ServiceManager";

    public static final String API_KEY = "7b09c51bbc0b0986ec85980cc3212d58";

    public static final String BASE_URL = "http://food2fork.com";

    public static final String SEARCH_PATH = "/api/search";
    public static final String GET_PATH = "/api/get";

    public static Retrofit retrofit;

    public static void init() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ArrayList<Recipe> getRecipesList() {
        ArrayList<Recipe> list = null;
        if (retrofit == null) {
            init();
        }
        if (retrofit != null) {
            try {
                IApiMethods apiService = retrofit.create(IApiMethods.class);
                Call<SearchResponse> call = apiService.getRecipesList(API_KEY);
                Response<SearchResponse> response = call.execute();
                list = response.body().recipes;
            } catch (IOException e) {
                Log.e(TAG, "getRecipesList method error! " + e.getMessage());
            }
        }
        return list;
    }

}
