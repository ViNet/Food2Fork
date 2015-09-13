package com.example.vit.food2fork.rest.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Vit on 11.09.2015.
 */

@Parcel
public class Recipe {

    protected String publisher;
    @SerializedName("f2f_url")
    protected String f2fUrl;
    protected String title;
    @SerializedName("source_url")
    protected String sourceUrl;
    @SerializedName("recipe_id")
    protected String recipeId;
    @SerializedName("image_url")
    protected String imageUrl;
    @SerializedName("social_rank")
    protected Double socialRank;
    @SerializedName("publisher_url")
    protected String publisherUrl;

    public String getPublisher(){
        return this.publisher;
    }

    public String getF2fUrl(){
        return this.f2fUrl;
    }

    public String getTitle(){
        return this.title;
    }

    public String getSourceUrl(){
        return this.sourceUrl;
    }

    public String getRecipeId(){
        return this.recipeId;
    }

    public String getImageUrl(){
        return this.imageUrl;
    }

    public double getSocialRank(){
        return this.socialRank;
    }

    public String getPublisherUrl(){
        return this.publisherUrl;
    }

    public void print(){
        Log.d("food2fork", "t - " + this.title + "id - " + recipeId);
    }
}
