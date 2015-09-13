package com.example.vit.food2fork.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.vit.food2fork.R;
import com.example.vit.food2fork.ui.fragment.DetailedRecipeFragment;

public class DetailActivity extends AppCompatActivity {

    Toolbar toolbar;
    DetailedRecipeFragment recipeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        startFragment();
    }

    private void startFragment(){
        recipeFragment = (DetailedRecipeFragment) getFragmentManager()
                .findFragmentById(R.id.detail_container);
        if(recipeFragment == null){
            recipeFragment = DetailedRecipeFragment.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.detail_container, recipeFragment).commit();
        }
    }
}
