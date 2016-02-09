package com.bohdanuhryn.food2fork;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.bohdanuhryn.food2fork.fragments.MainFragment;
import com.bohdanuhryn.food2fork.utils.ViewUtils;

public class MainActivity extends AppCompatActivity implements MainFragment.OnMainFragmentListener {

    public static final String TAG = "MainActivity";

    private MainFragment mainFragment;
    private SearchView searchView;
    private MenuItem searchClearItem;

    private String prevSearchQuery;

    @Override
    public void onStartRecipeActivity(long id) {
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
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                /*EditText et = ViewUtils.getEditText(MainActivity.this, searchView);
                if (et.getText().toString().isEmpty() && searchView.is .isFocused()) {
                    searchRecipe("");
                }*/
                return false;
            }
        });
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    searchView.setQuery(prevSearchQuery, false);
                } else if (prevSearchQuery.length() > 0) {
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
        }
    }

    private void actionViewTable() {
        if (mainFragment != null) {
            mainFragment.setViewTable();
        }
    }

}