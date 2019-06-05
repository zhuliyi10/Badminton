package com.leory.badminton.news.mvp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Describe : 标签PagerAdapter
 * Author : leory
 * Date : 2019-06-04
 */
public class TabPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public TabPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
