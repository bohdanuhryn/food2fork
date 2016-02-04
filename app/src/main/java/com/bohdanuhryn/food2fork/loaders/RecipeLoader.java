package com.bohdanuhryn.food2fork.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.bohdanuhryn.food2fork.models.Recipe;
import com.bohdanuhryn.food2fork.service.F2fManager;

/**
 * Created by BohdanUhryn on 04.02.2016.
 */
public class RecipeLoader extends AsyncTaskLoader<Recipe> {

    private long id;

    public RecipeLoader(Context context, long id) {
        super(context);
        this.id = id;
    }

    @Override
    public Recipe loadInBackground() {
        return F2fManager.getRecipe(id);
    }
}
