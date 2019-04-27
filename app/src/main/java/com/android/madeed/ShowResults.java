package com.android.madeed;

import android.app.SearchManager;
import android.content.Intent;
import android.database.MatrixCursor;
import android.provider.BaseColumns;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ShowResults extends AppCompatActivity implements MadeedListener {


    private ViewPager viewPager;
    private PageAdapter myPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_results);

        setUpViewPager();




    }

    private void setUpViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
         myPagerAdapter = new PageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(), true);
            }

        });
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.POSITION_MESSAGE);
        final MadeedApi madeedApi = MadeedApp.getApi(getApplicationContext());
        madeedApi.define(message, ShowResults.this);
    }

    @Override
    public void onTermDefinitionComplete(String originalTerm, List<Word> words) {

        DictionaryFragment df = (DictionaryFragment) myPagerAdapter.getItem(viewPager.getCurrentItem());
        df.setData(words);
    }

    @Override
    public void onSuggestionLookupComplete(String originalTerm, List<String> words) {
        Log.e("Madeed", words.toString());
    }
}
