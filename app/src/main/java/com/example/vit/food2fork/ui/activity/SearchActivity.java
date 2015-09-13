package com.example.vit.food2fork.ui.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.vit.food2fork.R;
import com.example.vit.food2fork.ui.fragment.FragmentTag;
import com.example.vit.food2fork.ui.fragment.SearchFragment;

public class SearchActivity extends AppCompatActivity {

    Toolbar toolbar;
    SearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initViews();
        handleIntent(getIntent());
    }

    private void initViews(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent){
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            getSupportActionBar().setTitle(query);
            startFragment(query);
        }
    }

    private void startFragment(String query){
        searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.search_container);
        if(searchFragment == null){
            searchFragment = SearchFragment.newInstance(FragmentTag.SEARCH_RESULT,
                    getIntent().getStringExtra(SearchManager.QUERY));

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.search_container, searchFragment).commit();
        }
    }

}
