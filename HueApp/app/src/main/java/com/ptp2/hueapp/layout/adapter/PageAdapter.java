package com.ptp2.hueapp.layout.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PageAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragments;
    private final List<String> fragmentsTitles;

    public PageAdapter(FragmentManager fm) {
        super(fm);
         this.fragments = new ArrayList<>();
         this.fragmentsTitles = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragmentsTitles.size();
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return this.fragmentsTitles.get(position);
    }

    public void add(Fragment fragment, String title)
    {
        this.fragments.add(fragment);
        this.fragmentsTitles.add(title);
    }

    public List<Fragment> getFragments() {
        return fragments;
    }

    public List<String> getFragmentsTitles() {
        return fragmentsTitles;
    }
}
