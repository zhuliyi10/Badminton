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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leory.badminton.news.R;
import com.leory.badminton.news.R2;
import com.leory.badminton.news.app.listener.AppBarStateChangeListener;
import com.leory.badminton.news.di.component.DaggerPlayerDetailComponent;
import com.leory.badminton.news.di.component.PlayerDetailComponent;
import com.leory.badminton.news.mvp.contract.PlayerContract;
import com.leory.badminton.news.mvp.model.bean.PlayerDetailBean;
import com.leory.badminton.news.mvp.model.bean.PlayerInfoBean;
import com.leory.badminton.news.mvp.presenter.PlayerDetailPresenter;
import com.leory.badminton.news.mvp.ui.adapter.TabPagerAdapter;
import com.leory.badminton.news.mvp.ui.fragment.PlayerInfoFragment;
import com.leory.badminton.news.mvp.ui.fragment.PlayerMatchFragment;
import com.leory.badminton.news.mvp.ui.widget.MatchTabView;
import com.leory.commonlib.base.BaseActivity;
import com.leory.commonlib.base.delegate.IComponent;
import com.leory.commonlib.di.component.AppComponent;
import com.leory.commonlib.di.scope.ActivityScope;
import com.leory.commonlib.utils.ImageUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Describe : 运动员详情
 * Author : leory
 * Date : 2019-06-10
 */
@ActivityScope
public class PlayerDetailActivity extends BaseActivity<PlayerDetailPresenter> implements PlayerContract.View {

    private static final String KEY_PLAYER_URL = "key_player_url";
    @BindView(R2.id.img_head)
    ImageView imgHead;
    @BindView(R2.id.player_name)
    LinearLayout playerName;
    @BindView(R2.id.ranking_num)
    TextView rankingNum;
    @BindView(R2.id.match_type)
    TextView matchType;
    @BindView(R2.id.player_ranking)
    LinearLayout playerRanking;
    @BindView(R2.id.txt_wins)
    TextView txtWins;
    @BindView(R2.id.txt_age)
    TextView txtAge;
    @BindView(R2.id.other)
    LinearLayout other;
    @BindView(R2.id.head)
    RelativeLayout head;
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.toolbarLayout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R2.id.tab)
    MatchTabView tab;
    @BindView(R2.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R2.id.view_pager)
    ViewPager viewPager;
    @BindView(R2.id.img_flag)
    ImageView imgFlag;
    @BindView(R2.id.progress)
    FrameLayout progress;
    @BindView(R2.id.ranking_num2)
    TextView rankingNum2;
    @BindView(R2.id.match_type2)
    TextView matchType2;
    @BindView(R2.id.ranking2)
    LinearLayout ranking2;


    public static void launch(Activity preActivity, String url) {
        preActivity.startActivity(new Intent(preActivity, PlayerDetailActivity.class).putExtra(KEY_PLAYER_URL, url));
    }

    @Override
    public IComponent setupActivityComponent(IComponent component) {
        PlayerDetailComponent playerDetailComponent = DaggerPlayerDetailComponent.builder()
                .appComponent((AppComponent) component)
                .view(this)
                .playerUrl(getIntent().getStringExtra(KEY_PLAYER_URL))
                .build();
        playerDetailComponent.inject(this);
        return playerDetailComponent;
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_player_detail;
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
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void showPlayerDetail(PlayerDetailBean bean) {
        ImageUtils.loadImage(this, imgHead, bean.getHead());
        ImageUtils.loadImage(this, imgFlag, bean.getFlag());
        toolbarLayout.setTitle(bean.getName());
        rankingNum.setText(bean.getRankNum());
        matchType.setText(bean.getType());
        txtWins.setText(bean.getWinNum());
        txtAge.setText(bean.getAge());
        if (bean.getRankNum2() != null) {
            ranking2.setVisibility(View.VISIBLE);
            rankingNum2.setText(bean.getRankNum2());
            matchType2.setText(bean.getType2());
        } else {
            ranking2.setVisibility(View.GONE);
        }
        initViewPager(bean.getInfoBean());
    }

    private void initViewPager(PlayerInfoBean infoBean) {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(PlayerInfoFragment.newInstance(infoBean));
        fragmentList.add(PlayerMatchFragment.newInstance());
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
        viewPager.setOffscreenPageLimit(2);
        String[] tabs = new String[]{"个人资料", "赛果", "历史排名"};
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
