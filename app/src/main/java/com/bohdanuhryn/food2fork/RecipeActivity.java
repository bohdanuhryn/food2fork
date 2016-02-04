package com.bohdanuhryn.food2fork;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.bohdanuhryn.food2fork.fragments.RecipeFragment;

/**
 * Created by BohdanUhryn on 04.02.2016.
 */
public class RecipeActivity extends AppCompatActivity {

    public static final String TAG = "RecipeActivity";
    public static final String RECIPE_ID = "recipe_id";

    private RecipeFragment recipeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_host);
        initFragment(savedInstanceState);
        setupActionBar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initFragment(Bundle savedInstanceState) {
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                recipeFragment = (RecipeFragment)getSupportFragmentManager().findFragmentByTag(RecipeFragment.TAG);
            }
            createFragment();
        }
    }

    private void createFragment() {
        long recipeId = -1;
        if (getIntent().getExtras() != null) {
            recipeId = getIntent().getExtras().getLong(RECIPE_ID, -1);
        }
        recipeFragment = RecipeFragment.newInstance(recipeId);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, recipeFragment, RecipeFragment.TAG)
                .commit();
    }

    private void setupActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
