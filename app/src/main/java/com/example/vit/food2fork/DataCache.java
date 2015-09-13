package com.example.vit.food2fork;

import com.example.vit.food2fork.rest.model.Recipe;

import java.util.List;

/**
 * Created by Vit on 11.09.2015.
 */
public class DataCache {

    // app's data cache
    private List<Recipe> recipes;
    private List<Recipe> nextRecipes;     // next loaded page of recipes
    private int pageId = 1;

    public List<Recipe> getRecipes(){
        return this.recipes;
    }

    public int getPageId(){
        return this.pageId;
    }

    public List<Recipe> getNextRecipes(){
        return this.nextRecipes;
    }

    public void setPageId(int pageId){
        this.pageId = pageId;
    }

    public void setRecipes(List<Recipe> movies){
        this.recipes = movies;
    }

    public void setNextRecipes(List<Recipe> recipes){
        this.nextRecipes = recipes;
        addRecipes(recipes);
    }

    private void addRecipes(List<Recipe> recipes){
        this.recipes.addAll(recipes);
    }


}
