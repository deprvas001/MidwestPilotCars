package com.midwestpilotcars.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.midwestpilotcars.views.fragments.PlaceHolderFragment;


public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        return PlaceHolderFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return 3;
    }
}