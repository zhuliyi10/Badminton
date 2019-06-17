package com.leory.badminton.news.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.leory.badminton.news.R;
import com.leory.badminton.news.R2;
import com.leory.badminton.news.app.listener.AppBarStateChangeListener;
import com.leory.badminton.news.app.utils.TranslateUtils;
import com.leory.badminton.news.di.component.DaggerMatchDetailComponent;
import com.leory.badminton.news.di.component.MatchDetailComponent;
import com.leory.badminton.news.mvp.contract.MatchDetailContract;
import com.leory.badminton.news.mvp.model.bean.MatchInfoBean;
import com.leory.badminton.news.mvp.presenter.MatchDetailPresenter;
import com.leory.badminton.news.mvp.ui.adapter.TabPagerAdapter;
import com.leory.badminton.news.mvp.ui.fragment.MatchAgainstChartFragment;
import com.leory.badminton.news.mvp.ui.fragment.MatchDateFragment;
import com.leory.badminton.news.mvp.ui.fragment.MatchHistoryFragment;
import com.leory.badminton.news.mvp.ui.fragment.MatchPlayersFragment;
import com.leory.badminton.news.mvp.ui.widget.MatchTabView;
import com.leory.commonlib.base.BaseActivity;
import com.leory.commonlib.base.delegate.IComponent;
import com.leory.commonlib.di.component.AppComponent;
import com.leory.commonlib.utils.ImageUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Describe : 赛事详情activity
 * Author : leory
 * Date : 2019-05-23
 */
public class MatchDetailActivity extends BaseActivity<MatchDetailPresenter> implements MatchDetailContract.View {
    public static final String KEY_DETAIL_URL = "detail_url";
    public static final String KEY_MATCH_CLASSIFY = "key_match_classify";

    @BindView(R2.id.toolbarLayout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R2.id.txt_name)
    TextView txtName;
    @BindView(R2.id.txt_time)
    TextView txtTime;
    @BindView(R2.id.txt_site)
    TextView txtSite;
    @BindView(R2.id.txt_bonus)
    TextView txtBonus;
    @BindView(R2.id.icon_match)
    ImageView iconMatch;
    @BindView(R2.id.img_bg)
    ImageView imgBg;
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.appBarLayout)
    AppBarLayout appBarLayout;

    @BindView(R2.id.head)
    FrameLayout head;
    @BindView(R2.id.tab)
    MatchTabView tab;
    @BindView(R2.id.view_pager)
    ViewPager viewPager;

    public static void launch(Activity preActivity, String detailUrl, String matchClassify) {
        preActivity.startActivity(new Intent(preActivity, MatchDetailActivity.class).putExtra(KEY_DETAIL_URL, detailUrl).putExtra(KEY_MATCH_CLASSIFY, matchClassify));
    }


    @Override
    public IComponent setupActivityComponent(IComponent component) {
        MatchDetailComponent matchDetailComponent = DaggerMatchDetailComponent.builder()
                .view(this)
                .detailUrl(getIntent().getStringExtra(KEY_DETAIL_URL))
                .matchClassify(getIntent().getStringExtra(KEY_MATCH_CLASSIFY))
                .hashMap(TranslateUtils.translatePlayerName())
                .appComponent((AppComponent) component)
                .build();
        matchDetailComponent.inject(this);
        return matchDetailComponent;
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_match_detail1;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {


        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    toolbar.setNavigationIcon(R.mipmap.arrow_left_white);
                } else if (state == State.COLLAPSED) {
                    toolbar.setNavigationIcon(R.mipmap.arrow_left_gray);
                } else {
                    toolbar.setNavigationIcon(R.mipmap.arrow_left_gray);
                }
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }


    @Override
    public void showMatchInfo(MatchInfoBean bean) {
        toolbarLayout.setTitle(bean.getMatchName());
        txtName.setText(bean.getMatchName());
        txtTime.setText(bean.getMatchDate());
        txtSite.setText(bean.getMatchSite());
        txtBonus.setText(bean.getMatchBonus());

        ImageUtils.loadSvg(this, iconMatch, bean.getMatchIcon());
        ImageUtils.loadImage(this, imgBg, bean.getMatchBackground());
        initViewPager(bean);
    }

    private void initViewPager(MatchInfoBean bean) {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(MatchDateFragment.newInstance(bean.getTabDateHeads()));
        fragmentList.add(MatchAgainstChartFragment.newInstance());
        fragmentList.add(MatchHistoryFragment.newInstance(bean.getHistoryUrl()));
        fragmentList.add(MatchPlayersFragment.newInstance());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                tab.setSelectPos(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        viewPager.setAdapter(new TabPagerAdapter(getSupportFragmentManager(), fragmentList));
        viewPager.setOffscreenPageLimit(4);
        String[] tabs = new String[]{"赛程", "对阵", "历史", "运动员"};
        tab.initData(Arrays.asList(tabs));
        tab.setOnChildClickListener(new MatchTabView.OnChildClickListener() {
            @Override
            public void onClick(TextView tv, int position) {
                tab.setSelectPos(position);
                viewPager.setCurrentItem(position);
            }
        });
        tab.setSelectPos(0);
    }
}

