package com.leory.badminton.news.mvp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Describe : 指示器adapter
 * Author : leory
 * Date : 2019-05-19
 */
public class IndicatorPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;

    public IndicatorPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }


    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
