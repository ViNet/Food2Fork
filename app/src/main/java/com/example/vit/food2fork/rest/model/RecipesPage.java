package com.example.vit.food2fork.rest.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by Vit on 11.09.2015.
 */

@Parcel
public class RecipesPage {

    @SerializedName("count")
    private Integer count;
    private List<Recipe> recipes;

    public void print(){
        Log.d("food2fork", " count - " + count);

        for(int i = 0; i < recipes.size(); i++){
            recipes.get(i).print();
        }
    }

    public int getCount(){
        return this.count;
    }

    public List<Recipe> getRecipes(){
        return this.recipes;
    }
}
