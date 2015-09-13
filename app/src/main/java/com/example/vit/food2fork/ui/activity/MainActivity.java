package com.example.vit.food2fork.ui.activity;

import android.app.SearchManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.vit.food2fork.R;
import com.example.vit.food2fork.ui.adapter.ViewPagerAdapter;
import com.example.vit.food2fork.ui.fragment.FragmentTag;
import com.example.vit.food2fork.ui.fragment.RecipesFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        ComponentName cn = new ComponentName(this, SearchActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(cn));

        return true;
    }



    private void initViews(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        ViewPager viewPager = (ViewPager) findViewById(R.id.main_viewpager);
        setupViewPager(viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.main_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        RecipesFragment fragmentTop = RecipesFragment.newInstance(FragmentTag.TOP_RATED);
        RecipesFragment fragmentTrend = RecipesFragment.newInstance(FragmentTag.IN_TREND);
        adapter.addFragment(fragmentTop, getString(R.string.top_rated).toUpperCase());
        adapter.addFragment(fragmentTrend, getString(R.string.in_trend).toUpperCase());
        viewPager.setAdapter(adapter);
    }
}
