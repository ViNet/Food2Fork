package com.example.vit.food2fork.ui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vit.food2fork.R;
import com.example.vit.food2fork.rest.RestClient;
import com.example.vit.food2fork.rest.conf.ApiConfig;
import com.example.vit.food2fork.rest.model.DetailedRecipePage;
import com.example.vit.food2fork.utils.NetworkUtil;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

public class DetailedRecipeFragment extends Fragment {

    DetailedRecipePage recipePage;

    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView ivCoverImg;

    LinearLayout view_recipe;
    ProgressBar pbLoading;
    RelativeLayout view_no_internet;
    Button btnRetry;

    TextView tvRecipeTitle;
    TextView tvRecipePublisher;
    TextView tvRecipeRank;
    TextView tvIngredients;


    public static DetailedRecipeFragment newInstance(){
        return new DetailedRecipeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detailed_recipe, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initCollapseToolbar(view);
        initViews(view);
        setListeners();
    }

    @Override
    public void onStart() {
        super.onStart();
        checkData();
    }

    private void initViews(View view){

        view_no_internet = (RelativeLayout) view.findViewById(R.id.noInternetView);
        btnRetry = (Button) view_no_internet.findViewById(R.id.btnRetry);

        pbLoading = (ProgressBar) view.findViewById(R.id.pbLoading);
        view_recipe = (LinearLayout) view.findViewById(R.id.view_recipe);
        ivCoverImg = (ImageView) view.findViewById(R.id.ivCoverImage);

        tvRecipeTitle = (TextView) view_recipe.findViewById(R.id.tvRecipeTitle);
        tvRecipePublisher = (TextView) view_recipe.findViewById(R.id.tvRecipePublisher);
        tvRecipeRank = (TextView) view_recipe.findViewById(R.id.tvRecipeRank);
        tvIngredients = (TextView) view_recipe.findViewById(R.id.lvIngredients);
    }

    private void setListeners(){
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingView();
                loadRecipe();
            }
        });
    }

    private void checkData(){
        if(recipePage == null){
            loadRecipe();
        }
    }

    private void loadRecipe(){
        if(NetworkUtil.getConnectivityStatus(getActivity().getBaseContext()) == NetworkUtil.TYPE_NOT_CONNECTED){
            showNoInternetView();
            return;
        }

        String id = getActivity().getIntent().getStringExtra("id");
        Log.d("food2fork", "in detailed id = " + id);

        Call<DetailedRecipePage> call = RestClient.getInstance()
                .getApiService().loadRecipe(ApiConfig.API_KEY, id);
        call.enqueue(new Callback<DetailedRecipePage>() {
            @Override
            public void onResponse(Response<DetailedRecipePage> response) {
                recipePage = response.body();
                fillData();
                showRecipeView();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void fillData(){
        tvRecipeTitle.setText(recipePage.getRecipe().getTitle());
        tvRecipePublisher.setText(getString(R.string.by, recipePage.getRecipe().getPublisher()));

        Glide.with(getActivity().getBaseContext())
                .load(recipePage.getRecipe().getImageUrl())
                .centerCrop()
                .crossFade()
                .into(ivCoverImg);

        tvRecipeRank.setText(String.format("%.1f", recipePage.getRecipe().getSocialRank()));

        tvIngredients.setText(makeIngredientsString(recipePage.getRecipe().getIngredients()));
    }

    private void initCollapseToolbar(View view){
        collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        if (toolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
        }
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
    }

    private String makeIngredientsString(List<String> input){
        StringBuilder sb = new StringBuilder();
        for(String str : input){
            str = str.replace("\n", "");
            sb.append("- " + str + ";\n");
        }
        return sb.toString();
    }

    private void hideLoadingView(){
        pbLoading.setVisibility(View.GONE);
    }

    private void hideRecipeView(){
        view_recipe.setVisibility(View.GONE);
    }

    private void hideNoInternetView(){
        view_no_internet.setVisibility(View.GONE);
    }

    private void showLoadingView(){
        hideNoInternetView();
        hideRecipeView();
        pbLoading.setVisibility(View.VISIBLE);
    }

    private void showRecipeView(){
        hideNoInternetView();
        hideLoadingView();
        view_recipe.setVisibility(View.VISIBLE);
    }

    private void showNoInternetView(){
        hideLoadingView();
        hideRecipeView();
        view_no_internet.setVisibility(View.VISIBLE);
    }
}
