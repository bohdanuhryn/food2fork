package com.bohdanuhryn.food2fork.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.bohdanuhryn.food2fork.models.Recipe;

import java.util.ArrayList;

/**
 * Created by BohdanUhryn on 04.02.2016.
 */
public class RecipesListLoader extends AsyncTaskLoader<ArrayList<Recipe>> {

    private String q;
    private String sort;
    private int page;

    public RecipesListLoader(Context context, String q, String sort, int page) {
        super(context);
        this.q = q;
        this.sort = sort;
        this.page = page;
    }

    @Override
    public ArrayList<Recipe> loadInBackground() {
        ArrayList<Recipe> arr = new ArrayList<Recipe>();
        for (int i = 0; i < 5; ++i) {
            Recipe r = new Recipe();
            r.id = i;
            arr.add(r);
        }
        return arr;
    }
}
