package com.leory.badminton.news.mvp.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.leory.badminton.news.R;
import com.leory.badminton.news.R2;
import com.leory.badminton.news.mvp.ui.adapter.IndicatorPagerAdapter;
import com.leory.badminton.news.mvp.ui.fragment.LiveFragment;
import com.leory.badminton.news.mvp.ui.fragment.MatchFragment;
import com.leory.commonlib.base.BaseActivity;
import com.leory.commonlib.di.component.AppComponent;
import com.leory.interactions.RouterHub;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

@Route(path = RouterHub.NEWS_NEWSMAINACTIVITY)
public class NewsMainActivity extends BaseActivity {

    @BindView(R2.id.indicator)
    MagicIndicator indicator;
    @BindView(R2.id.view_pager)
    ViewPager viewPager;
    private static final String[] CHANNELS = new String[]{"直播", "赛事", "排名", "球星"};
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> dataList = Arrays.asList(CHANNELS);
    private IndicatorPagerAdapter indicatorPagerAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_news_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        fragmentList.add(new LiveFragment());
        fragmentList.add(new MatchFragment());
        fragmentList.add(new LiveFragment());
        fragmentList.add(new LiveFragment());
        indicatorPagerAdapter = new IndicatorPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(indicatorPagerAdapter);
        viewPager.setOffscreenPageLimit(4);
        initIndicator();
    }

    private void initIndicator() {
        indicator.setBackgroundColor(Color.parseColor("#d43d3d"));
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setSkimOver(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return dataList == null ? 0 : dataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
                clipPagerTitleView.setText(dataList.get(index));
                clipPagerTitleView.setTextColor(Color.parseColor("#f2c4c4"));
                clipPagerTitleView.setClipColor(Color.WHITE);
                clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return clipPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(indicator, viewPager);
    }

}
