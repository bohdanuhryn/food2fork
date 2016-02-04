package com.bohdanuhryn.food2fork.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bohdanuhryn.food2fork.R;
import com.bohdanuhryn.food2fork.adapters.RecipesAdapter;
import com.bohdanuhryn.food2fork.loaders.RecipesListLoader;
import com.bohdanuhryn.food2fork.models.Recipe;
import com.bohdanuhryn.food2fork.models.RecipeSearchParams;
import com.bohdanuhryn.food2fork.models.RecipesList;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by BohdanUhryn on 04.02.2016.
 */
public class MainFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<RecipesList> {

    public static final String TAG = "MainFragment";

    private OnMainFragmentListener mainFragmentListener;

    private View rootView;
    @Bind(R.id.recipes_not_found_view)
    TextView recipesNotFound;
    @Bind(R.id.recipes_recycler_view)
    RecyclerView recipesRecyclerView;

    private RecipesAdapter recipesAdapter;

    private RecipeSearchParams recipeSearchParams;
    private ArrayList<Recipe> recipesList;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mainFragmentListener = (OnMainFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnMainFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainFragmentListener = null;
    }

    @Override
    public Loader<RecipesList> onCreateLoader(int id, Bundle args) {
        return new RecipesListLoader(getActivity(), recipeSearchParams);
    }

    @Override
    public void onLoadFinished(Loader<RecipesList> loader, RecipesList data) {
        if (data != null) {
            recipesList = data.recipes;
            recipesNotFound.setVisibility(View.INVISIBLE);
            setupRecipesAdapter();
        } else {
            recipesNotFound.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<RecipesList> loader) {
        recipesList = null;
        setupRecipesAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initViews();
        setupRecipesRecyclerView();
        loadRecipes();
        return rootView;
    }

    private void initViews() {
        ButterKnife.bind(this, rootView);
    }

    private void setupRecipesRecyclerView() {
        LinearLayoutManager recipesLayoutManager = new LinearLayoutManager(getActivity());
        recipesRecyclerView.setLayoutManager(recipesLayoutManager);
        setupRecipesAdapter();
    }

    private void setupRecipesAdapter() {
        recipesAdapter = new RecipesAdapter(recipesList);
        recipesAdapter.setOnItemClickListener(new RecipesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mainFragmentListener != null && recipesList != null) {
                    mainFragmentListener.onStartRecipeActivity(recipesList.get(position).getId());
                }
            }
        });
        recipesRecyclerView.setAdapter(recipesAdapter);
    }

    private void loadRecipes() {
        getLoaderManager().restartLoader(0, null, this).forceLoad();
    }

    public interface OnMainFragmentListener {
        public void onStartRecipeActivity(long id);
    }
}
