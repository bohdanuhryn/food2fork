package com.bohdanuhryn.food2fork.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bohdanuhryn.food2fork.R;
import com.bohdanuhryn.food2fork.models.Recipe;
import com.bohdanuhryn.food2fork.models.RecipeGet;
import com.bohdanuhryn.food2fork.service.F2fManager;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by BohdanUhryn on 04.02.2016.
 */
public class RecipeFragment extends Fragment {

    public static final String TAG = "RecipeFragment";
    public static final String RECIPE_ID = "recipe_id";

    private View rootView;
    @Bind(R.id.recipe_title_view)
    TextView titleView;
    @Bind(R.id.recipe_image_view)
    ImageView imageView;
    @Bind(R.id.recipe_publisher_view)
    TextView publisherView;
    @Bind(R.id.recipe_rank_view)
    TextView rankView;
    @Bind(R.id.recipe_ingredients_view)
    TextView ingredientsView;

    private String recipeId;
    private Recipe recipe;

    public static RecipeFragment newInstance(String id) {
        RecipeFragment fragment = new RecipeFragment();
        Bundle args = new Bundle();
        args.putString(RECIPE_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    public RecipeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recipe, container, false);
        readArguments();
        initViews();
        loadRecipe();
        return rootView;
    }

    private void readArguments() {
        Bundle args = getArguments();
        recipeId = "";
        if (args != null) {
            recipeId = args.getString(RECIPE_ID);
        }
    }

    private void initViews() {
        ButterKnife.bind(this, rootView);
    }

    private void setupViews() {
        if (recipe != null) {
            titleView.setText(Html.fromHtml(String.format("<a href=\"%s\">%s</a>", recipe.source_url, recipe.title)));
            titleView.setMovementMethod(LinkMovementMethod.getInstance());
            Picasso.with(imageView.getContext()).load(recipe.image_url).into(imageView);
            publisherView.setText(Html.fromHtml(String.format("<a href=\"%s\">%s</a>", recipe.publisher_url, recipe.publisher)));
            publisherView.setMovementMethod(LinkMovementMethod.getInstance());
            rankView.setText(String.format("%.0f%%", recipe.social_rank));
        }
        setupIngredientsView();
    }

    private void setupIngredientsView() {
        if (recipe != null && recipe.ingredients != null) {
            String ingredientsStr = "";
            for (int i = 0; i < recipe.ingredients.size(); ++i) {
                ingredientsStr += "\n - " + recipe.ingredients.get(i) + ";\n";
            }
            ingredientsView.setText(ingredientsStr);
        }
    }

    private void loadRecipe() {
        F2fManager.getRecipe(recipeId).enqueue(new Callback<RecipeGet>() {
            @Override
            public void onResponse(Response<RecipeGet> response) {
                if (response.body() != null && response.body().recipe != null) {
                    recipe = response.body().recipe;
                    setupViews();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

}
