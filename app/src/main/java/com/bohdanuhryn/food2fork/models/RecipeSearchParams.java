package com.bohdanuhryn.food2fork.models;

import java.io.Serializable;

/**
 * Created by BohdanUhryn on 04.02.2016.
 */
public class RecipeSearchParams implements Serializable {

    public String query;
    public String sort;
    public int page;

    public RecipeSearchParams() {
        query = "";
        sort = "r";
        page = 1;
    }

}
