package com.bohdanuhryn.food2fork.models;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by BohdanUhryn on 04.02.2016.
 */
public class Recipe {

    public String image_url;
    public String source_url;
    public String f2f_url;
    public String title;
    public String publisher;
    public String publisher_url;
    public float social_rank;
    public int page;
    public ArrayList<String> ingredients;

    public long getId() {
        long id = -1;
        try {
            int lastIndex = f2f_url.lastIndexOf("/");
            id = Long.parseLong(f2f_url.substring(lastIndex + 1));
        } catch (Exception e) {
            Log.e("Recipe", "Cannot get id from f2f_url!");
        }
        return id;
    }

}
