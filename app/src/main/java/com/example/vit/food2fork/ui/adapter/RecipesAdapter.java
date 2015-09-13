package com.example.vit.food2fork.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vit.food2fork.R;
import com.example.vit.food2fork.rest.model.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vit on 12.09.2015.
 */
public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {

    List<Recipe> recipesList;
    private Context context;

    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title;

        public ViewHolder(View v) {
            super(v);
            image= (ImageView) v.findViewById(R.id.ivRecipeImg);
            title = (TextView) v.findViewById(R.id.tvRecipeTitle);
        }
    }

    public RecipesAdapter(Context context) {
        this.context = context;
        this.recipesList = new ArrayList<>();
    }


    @Override
    public RecipesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recipes_grid_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        holder.title.setText(recipesList.get(i).getTitle());

        String url = recipesList.get(i).getImageUrl();

        Glide.with(context)
                .load(url)
                .centerCrop()
                .crossFade()
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return recipesList.size();
    }

    public void setData(List<Recipe> data){
        this.recipesList.clear();
        addData(data);
    }

    public void addData(List<Recipe> data){
        recipesList.addAll(data);
        notifyDataSetChanged();
    }

    public Recipe getItemAtPosition(int position){
        return this.recipesList.get(position);
    }


}
