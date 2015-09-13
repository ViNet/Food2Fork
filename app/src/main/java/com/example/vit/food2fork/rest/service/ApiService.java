package com.example.vit.food2fork.rest.service;

import com.example.vit.food2fork.rest.model.DetailedRecipePage;
import com.example.vit.food2fork.rest.model.RecipesPage;

import java.util.Map;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.http.QueryMap;

/**
 * Created by Vit on 11.09.2015.
 */
public interface ApiService {

    @GET("search")
    Call<RecipesPage> loadRecipes(@Query("key") String apiKey,  @QueryMap Map<String, String> options);

    @GET("get")
    Call<DetailedRecipePage> loadRecipe(@Query("key") String apiKey, @Query("rId") String id);
}
