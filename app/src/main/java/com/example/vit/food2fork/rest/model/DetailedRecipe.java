package com.example.vit.food2fork.rest.model;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by Vit on 12.09.2015.
 */

@Parcel
public class DetailedRecipe extends Recipe{

    private List<String> ingredients;

    public List<String> getIngredients(){
        return this.ingredients;
    }

}
