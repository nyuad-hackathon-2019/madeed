package com.android.madeed;

import android.net.Uri;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ResultsActivity extends AppCompatActivity {

    TabLayout tablay;
    TabItem tab1;
    TabItem tab2;
    TabItem tab3;
    ViewPager viewPager;
    PagerAdapter pageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_main);
        tablay=(TabLayout) findViewById(R.id.tabLayout);
        tab1 = (TabItem) findViewById(R.id.tabOntology);
        tab2 = (TabItem) findViewById(R.id.tabDefinition);
        tab3  = (TabItem) findViewById(R.id.tabMorph);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        pageAdapter = new PageAdapter(getSupportFragmentManager(), tablay.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablay));


    }
}
class PageAdapter extends FragmentPagerAdapter{

    private int numOfTabs;

    PageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Tab1();
            case 1:
                return new Tab2();
            case 2:
                return new Tab3();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }


}