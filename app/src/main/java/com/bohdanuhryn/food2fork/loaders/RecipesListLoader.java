package com.bohdanuhryn.food2fork.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.bohdanuhryn.food2fork.models.RecipeSearchParams;
import com.bohdanuhryn.food2fork.models.RecipesList;
import com.bohdanuhryn.food2fork.service.F2fManager;

/**
 * Created by BohdanUhryn on 04.02.2016.
 */
public class RecipesListLoader extends AsyncTaskLoader<RecipesList> {

    private RecipeSearchParams recipeSearchParams;

    public RecipesListLoader(Context context, RecipeSearchParams recipeSearchParams) {
        super(context);
        this.recipeSearchParams = recipeSearchParams;
    }

    @Override
    public RecipesList loadInBackground() {
        return F2fManager.getRecipesList(recipeSearchParams);
    }
}
