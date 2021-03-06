package com.bohdanuhryn.food2fork;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bohdanuhryn.food2fork.fragments.MainFragment;

public class MainActivity extends AppCompatActivity implements MainFragment.OnMainFragmentListener {

    public static final String TAG = "MainActivity";

    private MainFragment mainFragment;
    private SearchView searchView;
    private MenuItem searchClearItem;

    private String prevSearchQuery;

    @Override
    public void onStartRecipeActivity(String id) {
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra(RecipeActivity.RECIPE_ID, id);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_host);
        initFragment(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        setupSearchView();
        searchClearItem = menu.findItem(R.id.action_search_clear);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_top:
                actionSortTop();
                item.setChecked(true);
                return true;
            case R.id.action_sort_tranding:
                actionSortTranding();
                item.setChecked(true);
                return true;
            case R.id.action_view_list:
                actionViewList();
                item.setChecked(true);
                return true;
            case R.id.action_view_table:
                actionViewTable();
                item.setChecked(true);
                return true;
            case R.id.action_search_clear:
                searchRecipe("");
                searchClearItem.setVisible(false);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initFragment(Bundle savedInstanceState) {
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                mainFragment = (MainFragment)getSupportFragmentManager().findFragmentByTag(MainFragment.TAG);
            }
            createFragment();
        }
    }

    private void createFragment() {
        mainFragment = MainFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, mainFragment, MainFragment.TAG)
                .commit();
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchRecipe(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    searchView.setQuery(prevSearchQuery, false);
                    searchClearItem.setVisible(false);
                } else if (prevSearchQuery != null && prevSearchQuery.length() > 0) {
                    searchClearItem.setVisible(true);
                }
            }
        });
    }

    private void searchRecipe(String query) {
        prevSearchQuery = query;
        if (mainFragment != null) {
            mainFragment.setSearchQuery(query);
            mainFragment.startSearch();
        }
    }

    private void actionSortTop() {
        if (mainFragment != null) {
            mainFragment.setSearchSortType("r");
            mainFragment.startSearch();
        }
    }

    private void actionSortTranding() {
        if (mainFragment != null) {
            mainFragment.setSearchSortType("t");
            mainFragment.startSearch();
        }
    }

    private void actionViewList() {
        if (mainFragment != null) {
            mainFragment.setViewList();
            mainFragment.startSearch();
        }
    }

    private void actionViewTable() {
        if (mainFragment != null) {
            mainFragment.setViewTable();
            mainFragment.startSearch();
        }
    }

}