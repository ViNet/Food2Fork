package com.example.vit.food2fork.ui.fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.example.vit.food2fork.R;
import com.example.vit.food2fork.rest.RestClient;
import com.example.vit.food2fork.rest.conf.ApiConfig;
import com.example.vit.food2fork.rest.model.RecipesPage;
import com.example.vit.food2fork.utils.NetworkUtil;
import com.example.vit.food2fork.utils.UrlBuilder;

import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by Vit on 12.09.2015.
 */
public class SearchFragment extends RecipesFragment {

    boolean endOfSearch = false;

    public static SearchFragment newInstance(String tag, String query){
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString("tag", tag);
        args.putString("query", query);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void loadRecipes() {
        if(NetworkUtil.getConnectivityStatus(getActivity().getBaseContext()) == NetworkUtil.TYPE_NOT_CONNECTED){
            showNoInternetView();
            return;
        }

        if(endOfSearch){
            return;
        }

        isLoadingNow = true;
        Map<String, String> options = UrlBuilder.getSearchRecipesOptions(
                getArguments().getString("query")
                ,recipes.size() + 1);

        Call<RecipesPage> call = RestClient.getInstance().getApiService()
                .loadRecipes(ApiConfig.API_KEY, options);

        call.enqueue(new Callback<RecipesPage>() {
            @Override
            public void onResponse(Response<RecipesPage> response) {
                if(response.body().getRecipes().size() == 0) {
                    endOfSearch = true;
                    if(recipes.size() == 0){
                        // show no search result;
                        Toast.makeText(getContext(),getString(R.string.search_no_results), Toast.LENGTH_LONG).show();
                        hideLoadingView();
                    }
                    return;
                }

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
}
