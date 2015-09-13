package com.example.vit.food2fork.utils;

import android.util.Log;

import com.example.vit.food2fork.ui.fragment.FragmentTag;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vit on 11.09.2015.
 */
public class UrlBuilder {

    public static Map<String, String> getRecipesOptions(String requester, int page) {
        Map<String, String> options = new HashMap<>();
        options.put("sort", getSortByForRequesterFragment(requester));
        options.put("page", String.valueOf(page));
        Log.d("food2fork", "query - " + options.toString());
        return options;
    }

    public static Map<String, String> getSearchRecipesOptions(String query, int page){
        Map<String, String> options = new HashMap<>();
        options.put("q", query);
        options.put("page", String.valueOf(page));
        Log.d("food2fork", "query Search - " + options.toString());
        return options;
    }

    public static String getSortByForRequesterFragment(String requester) {

        String sortBy = null;
        switch (requester) {
            case FragmentTag.TOP_RATED:
                sortBy = "r";
                break;
            case FragmentTag.IN_TREND:
                sortBy = "t";
                break;
        }
        return sortBy;
    }
}
