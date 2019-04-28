package com.android.madeed;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {

    private static final String[] TITLES = new String[] {"Dictionary", "Morphology"};

    private Fragment[] fragments = new Fragment[TITLES.length];

    public PageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (fragments[0] == null) {
                    fragments[0] =  new DictionaryFragment();
                }
                return fragments[0];
            case 1:
                if (fragments[1] == null) {
                    fragments[1] =  new MorphologyFragment();
                }
                return fragments[1];
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return TITLES.length ;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
}