package com.leory.badminton;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ViewUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.leory.badminton.adapter.MainPagerAdapter;
import com.leory.badminton.utils.FragmentUtils;
import com.leory.badminton.widget.BottomItemView;
import com.leory.commonlib.base.BaseActivity;
import com.leory.commonlib.di.component.AppComponent;
import com.leory.commonlib.utils.StatusBarUtils;
import com.leory.interactions.RouterHub;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = RouterHub.APP_MAINACTIVITY)
public class MainActivity extends BaseActivity {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.item_match)
    BottomItemView itemMatch;
    @BindView(R.id.item_video)
    BottomItemView itemVideo;
    @BindView(R.id.item_circle)
    BottomItemView itemCircle;
    @BindView(R.id.item_mine)
    BottomItemView itemMine;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(FragmentUtils.getNewsFragment());
        fragmentList.add(FragmentUtils.getVideoFragment());
        fragmentList.add(FragmentUtils.getCircleFragment());
        fragmentList.add(FragmentUtils.getMineFragment());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if(i==0){
                    StatusBarUtils.setDarkStatusBar(MainActivity.this,true,R.color.colorPrimary);
                }else {
                    StatusBarUtils.setDarkStatusBar(MainActivity.this,false,0);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), fragmentList));
        viewPager.setOffscreenPageLimit(4);
        StatusBarUtils.setDarkStatusBar(MainActivity.this,true,R.color.colorPrimary);//进来没有执行onPageSelected回调，这里要设置一下
    }


    @OnClick({R.id.item_match, R.id.item_video, R.id.item_circle, R.id.item_mine})
    public void onViewClicked(View view) {
        onItemClick((BottomItemView) view);
    }

    private void onItemClick(BottomItemView view) {
        if (view.isActivate()) return;
        itemMatch.setActivate(false);
        itemVideo.setActivate(false);
        itemCircle.setActivate(false);
        itemMine.setActivate(false);
        view.setActivate(true);
        if (view.equals(itemMatch)) {
            viewPager.setCurrentItem(0);
        } else if (view.equals(itemVideo)) {
            viewPager.setCurrentItem(1);
        } else if (view.equals(itemCircle)) {
            viewPager.setCurrentItem(2);
        } else if (view.equals(itemMine)) {
            viewPager.setCurrentItem(3);
        }
    }
}
