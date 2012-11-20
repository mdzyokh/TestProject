package com.vibeit.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.vibeit.fragment.AbstractFragment;

/**
 * @author Maria Dzyokh
 */
public class HomeScreenPagerAdapter extends FragmentPagerAdapter {

    private AbstractFragment[] fragments;

    public HomeScreenPagerAdapter(FragmentManager fm, AbstractFragment[] fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

}