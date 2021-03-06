package com.bohdanuhryn.food2fork.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bohdanuhryn.food2fork.R;
import com.bohdanuhryn.food2fork.adapters.RecipesAdapter;
import com.bohdanuhryn.food2fork.models.Recipe;
import com.bohdanuhryn.food2fork.models.RecipeSearchParams;
import com.bohdanuhryn.food2fork.models.RecipesList;
import com.bohdanuhryn.food2fork.service.F2fManager;
import com.bohdanuhryn.food2fork.utils.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by BohdanUhryn on 04.02.2016.
 */
public class MainFragment extends Fragment {

    public static final String TAG = "MainFragment";

    private OnMainFragmentListener mainFragmentListener;

    private View rootView;
    @Bind(R.id.recipes_recycler_view)
    RecyclerView recipesRecyclerView;
    @Bind(R.id.recipes_progress_bar)
    ProgressBar recipesProgressBar;
    @Bind(R.id.recipes_swipe_refresh)
    SwipeRefreshLayout recipesSwipeRefresh;

    private RecyclerView.LayoutManager recipesLayoutManager;
    private RecipesAdapter recipesAdapter;

    private RecipeSearchParams recipeSearchParams;
    private ArrayList<Recipe> recipesList;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        fragment.recipeSearchParams = new RecipeSearchParams();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {

    }

    public void startSearch() {
        if (recipeSearchParams == null) {
            recipeSearchParams = new RecipeSearchParams();
        }
        recipeSearchParams.page = 1;
        loadRecipes();
    }

    public void setSearchQuery(String query) {
        if (recipeSearchParams == null) {
            recipeSearchParams = new RecipeSearchParams();
        }
        recipeSearchParams.query = query;
    }

    public void setSearchSortType(String sort) {
        if (recipeSearchParams == null) {
            recipeSearchParams = new RecipeSearchParams();
        }
        recipeSearchParams.sort = sort;
    }

    public void setViewList() {
        recipesLayoutManager = new LinearLayoutManager(getActivity());
        recipesRecyclerView.setLayoutManager(recipesLayoutManager);
    }

    public void setViewTable() {
        recipesLayoutManager = new GridLayoutManager(getActivity(), 2);
        recipesRecyclerView.setLayoutManager(recipesLayoutManager);
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initViews();
        setupRecipesRecyclerView();
        setupSwipeRefresh();
        loadRecipes();
        return rootView;
    }

    private void initViews() {
        ButterKnife.bind(this, rootView);
    }

    private void setupRecipesRecyclerView() {
        recipesLayoutManager = new LinearLayoutManager(getActivity());
        recipesRecyclerView.setLayoutManager(recipesLayoutManager);
        setupEndlessRecycler();
        setupRecipesAdapter();
    }

    private void setupEndlessRecycler() {
        recipesRecyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(recipesLayoutManager) {

            @Override
            public void onLoadMore(int current_page) {
                if (recipesList != null && recipeSearchParams != null) {
                    ++recipeSearchParams.page;
                    F2fManager.getRecipesList(recipeSearchParams).enqueue(new Callback<RecipesList>() {
                        @Override
                        public void onResponse(Response<RecipesList> response) {
                            if (response.body() != null && response.body().recipes != null) {
                                recipesList.addAll(response.body().recipes);
                                recipesAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {

                        }
                    });
                }
            }
        });
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

    private void setupSwipeRefresh() {
        recipesSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadRecipes();
                recipesSwipeRefresh.setRefreshing(false);
            }
        });
    }

    private void loadRecipes() {
        setupEndlessRecycler();
        recipesProgressBar.setVisibility(View.VISIBLE);
        F2fManager.getRecipesList(recipeSearchParams).enqueue(new Callback<RecipesList>() {
            @Override
            public void onResponse(Response<RecipesList> response) {
                if (response.body() != null && response.body().recipes != null) {
                    recipesList = response.body().recipes;
                    setupRecipesAdapter();
                }
                recipesProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Throwable t) {
                recipesProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    public interface OnMainFragmentListener {
        public void onStartRecipeActivity(String id);
    }
}
