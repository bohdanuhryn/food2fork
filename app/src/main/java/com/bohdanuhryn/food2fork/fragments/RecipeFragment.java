package com.bohdanuhryn.food2fork.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bohdanuhryn.food2fork.R;
import com.bohdanuhryn.food2fork.RecipeActivity;

import butterknife.ButterKnife;

/**
 * Created by BohdanUhryn on 04.02.2016.
 */
public class RecipeFragment extends Fragment {

    public static final String TAG = "RecipeFragment";
    public static final String RECIPE_ID = "recipe_id";

    private View rootView;

    public static RecipeFragment newInstance(long id) {
        RecipeFragment fragment = new RecipeFragment();
        Bundle args = new Bundle();
        args.putLong(RECIPE_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    public RecipeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recipe, container, false);
        initViews();
        return rootView;
    }

    private void initViews() {
        ButterKnife.bind(this, rootView);
    }

}
