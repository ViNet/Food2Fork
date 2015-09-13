package com.example.vit.food2fork.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.vit.food2fork.R;
import com.example.vit.food2fork.rest.RestClient;
import com.example.vit.food2fork.rest.conf.ApiConfig;
import com.example.vit.food2fork.rest.model.RecipesPage;
import com.example.vit.food2fork.ui.activity.DetailActivity;
import com.example.vit.food2fork.ui.adapter.RecipesAdapter;
import com.example.vit.food2fork.ui.listener.EndlessRecyclerOnScrollListener;
import com.example.vit.food2fork.ui.listener.RecyclerItemClickListener;
import com.example.vit.food2fork.utils.NetworkUtil;
import com.example.vit.food2fork.utils.UrlBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by Vit on 11.09.2015.
 */
public class RecipesFragment extends Fragment implements EndlessRecyclerOnScrollListener.OnLoadMoreListener
                    , RecyclerItemClickListener.OnItemClickListener{

    static final String TAG = "food2fork";

    private String tag;

    GridLayoutManager layoutManager;
    RecipesAdapter adapter;
    EndlessRecyclerOnScrollListener scrollListener;

    boolean isLoadingNow = false;


    View view;
    RelativeLayout view_no_internet;
    ProgressBar pbLoading;
    RecyclerView rvRecipes;
    Button btnRetry;

    List<RecipesPage> recipes = new ArrayList<>();


    public static RecipesFragment newInstance(String tag) {
        RecipesFragment fragment = new RecipesFragment();
        Bundle args = new Bundle();
        args.putString("tag", tag);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tag = getArguments().getString("tag");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        this.view = inflater.inflate(R.layout.fragment_recipes, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated()");
        super.onViewCreated(view, savedInstanceState);
        initViews();
        setupRecyclerView();
        setListeners();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
        checkData();
    }

    protected void initViews() {
        this.rvRecipes = (RecyclerView) view.findViewById(R.id.rvRecipes);
        this.pbLoading = (ProgressBar) view.findViewById(R.id.pbLoading);
        this.view_no_internet = (RelativeLayout) view.findViewById(R.id.noInternetView);

        btnRetry = (Button) view_no_internet.findViewById(R.id.btnRetry);
    }

    protected void setupRecyclerView(){
        // use a grid layout manager
        layoutManager = new GridLayoutManager(getActivity().getBaseContext(), 2);

        rvRecipes.setLayoutManager(layoutManager);
        // specify an adapter
        adapter = new RecipesAdapter(getActivity().getBaseContext());
        rvRecipes.setAdapter(adapter);
    }

    protected void setListeners(){
        scrollListener =
                new EndlessRecyclerOnScrollListener(layoutManager, this);
        rvRecipes.addOnScrollListener(scrollListener);
        rvRecipes.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity().getBaseContext(), this));

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingView();
                loadRecipes();

            }
        });
}

    protected void fillData(RecipesPage recipesPage){
        adapter.addData(recipesPage.getRecipes());
        this.recipes.add(recipesPage);
    }


    protected void checkData(){
        if(recipes.size() == 0){
            loadRecipes();
        }
    }

    public void loadRecipes(){
        if(NetworkUtil.getConnectivityStatus(getActivity().getBaseContext()) == NetworkUtil.TYPE_NOT_CONNECTED){
            showNoInternetView();
            return;
        }

        isLoadingNow = true;
        Map<String, String> options = UrlBuilder.getRecipesOptions(tag, recipes.size() + 1);
        Call<RecipesPage> call = RestClient.getInstance().getApiService()
                .loadRecipes(ApiConfig.API_KEY, options);

        call.enqueue(new Callback<RecipesPage>() {
            @Override
            public void onResponse(Response<RecipesPage> response) {
                fillData(response.body());
                showContentView();
                isLoadingNow = false;
            }

            @Override
            public void onFailure(Throwable t) {
                isLoadingNow = false;
            }
        });
    }

    protected void hideLoadingView(){
        pbLoading.setVisibility(View.GONE);
    }

    protected void hideContentView(){
        rvRecipes.setVisibility(View.GONE);
    }

    protected void hideNoInternetView(){
        view_no_internet.setVisibility(View.GONE);
    }

    protected void showLoadingView(){
        hideContentView();
        hideNoInternetView();
        pbLoading.setVisibility(View.VISIBLE);
    }

    protected void showContentView(){
        hideLoadingView();
        hideNoInternetView();
        rvRecipes.setVisibility(View.VISIBLE);
    }

    protected void showNoInternetView(){
        Log.d("food2fork", "showNoInternetView()");
        hideLoadingView();
        hideContentView();
        view_no_internet.setVisibility(View.VISIBLE);
    }


    @Override
    public void onLoadMore() {
        if(isLoadingNow){
            return;
        }
        Log.d("food2fork", "onLoadMore()");
        loadRecipes();
    }

    @Override
    public void onItemClick(View view, int position) {
        String id = adapter.getItemAtPosition(position).getRecipeId();
        Log.d(TAG, "onItemClick() pos - " + position + "id - " + id);
        Intent showDetailsIntent = new Intent(getActivity(), DetailActivity.class);
        showDetailsIntent.putExtra("id", id);
        startActivity(showDetailsIntent);
    }
}
