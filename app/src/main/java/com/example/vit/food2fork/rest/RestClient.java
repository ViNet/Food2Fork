package com.example.vit.food2fork.rest;

import com.example.vit.food2fork.rest.service.ApiService;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Vit on 11.09.2015.
 */
public class RestClient {

    static final String CLASS = RestClient.class.getSimpleName() + ": ";
    private static final String BASE_URL = "http://food2fork.com/api/";

    private static RestClient restClient;
    private ApiService apiService;
    //private Bus bus;

    public static RestClient getInstance() {
        if (restClient == null) {
            restClient = new RestClient();
        }
        return restClient;
    }

    private RestClient() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public ApiService getApiService() {
        return apiService;
    }

}
