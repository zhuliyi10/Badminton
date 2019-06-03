package com.leory.badminton.news.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.leory.badminton.news.R;
import com.leory.badminton.news.R2;
import com.leory.badminton.news.app.listener.AppBarStateChangeListener;
import com.leory.badminton.news.di.component.DaggerMatchDetailComponent;
import com.leory.badminton.news.mvp.contract.MatchDetailContract;
import com.leory.badminton.news.mvp.model.bean.MatchInfoBean;
import com.leory.badminton.news.mvp.presenter.MatchDetailPresenter;
import com.leory.badminton.news.mvp.ui.widget.ScheduleView;
import com.leory.badminton.news.mvp.ui.widget.againstFlow.AgainFlowView;
import com.leory.badminton.news.mvp.ui.widget.againstFlow.AgainstFlowBean;
import com.leory.badminton.news.mvp.ui.widget.spinner.SpinnerPopView;
import com.leory.commonlib.base.BaseActivity;
import com.leory.commonlib.di.component.AppComponent;
import com.leory.commonlib.image.ImageConfig;
import com.leory.commonlib.utils.AppUtils;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Describe : 赛事详情activity
 * Author : leory
 * Date : 2019-05-23
 */
public class MatchDetailActivity extends BaseActivity<MatchDetailPresenter> implements MatchDetailContract.View, SpinnerPopView.OnSelectListener {
    private static final String KEY_DETAIL_URL = "detail_url";
    private static final String KEY_MATCH_CLASSIFY = "key_match_classify";
    @BindView(R2.id.scheduleView)
    ScheduleView scheduleView;
    private String[] matchType = new String[]{"男单", "女单", "男双", "女双", "混双"};
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
    @BindView(R2.id.progress)
    FrameLayout progress;
    @BindView(R2.id.againstFlow)
    AgainFlowView againstFlow;
    @BindView(R2.id.head)
    FrameLayout head;
    @BindView(R2.id.spinner_type)
    SpinnerPopView spinnerType;
    @BindView(R2.id.tab)
    FrameLayout tab;

    public static void launch(Activity preActivity, String detailUrl, String matchClassify) {
        preActivity.startActivity(new Intent(preActivity, MatchDetailActivity.class).putExtra(KEY_DETAIL_URL, detailUrl).putExtra(KEY_MATCH_CLASSIFY, matchClassify));
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMatchDetailComponent.builder()
                .view(this)
                .detailUrl(getIntent().getStringExtra(KEY_DETAIL_URL))
                .matchClassify(getIntent().getStringExtra(KEY_MATCH_CLASSIFY))
                .appComponent(appComponent)
                .build().inject(this);

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_match_detail1;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        spinnerType.initData(Arrays.asList(matchType));
        spinnerType.setOnSelectListener(this);
        if (presenter.isGroup()) {
            spinnerType.setVisibility(View.GONE);

        }
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
        againstFlow.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        if(progress!=null) {
            progress.setVisibility(View.GONE);
            againstFlow.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void onItemClick(int pos, String name) {
        presenter.requestData(name);
    }

    @Override
    public void showAgainstView(List<List<AgainstFlowBean>> againstData) {
        againstFlow.setAgainstData(againstData);
    }

    @Override
    public void showMatchInfo(MatchInfoBean bean) {
        toolbarLayout.setTitle(bean.getMatchName());
        txtName.setText(bean.getMatchName());
        txtTime.setText(bean.getMatchDate());
        txtSite.setText(bean.getMatchSite());
        txtBonus.setText(bean.getMatchBonus());
        ImageConfig config = new ImageConfig.Builder()
                .imageView(iconMatch)
                .url(bean.getMatchIcon())
                .build();
        AppUtils.obtainImageLoader().loadImage(this, config);
        config = new ImageConfig.Builder()
                .imageView(imgBg)
                .url(bean.getMatchBackground())
                .build();
        AppUtils.obtainImageLoader().loadImage(this, config);
    }

    @Override
    public void showMatchSchedule(int size) {
        if (size > 0 && scheduleView.getChildCount() == 0) {
            scheduleView.initData(size);
            scheduleView.setOnChildClickListener(new ScheduleView.OnChildClickListener() {
                @Override
                public void onClick(TextView tv, int position) {
                    scheduleView.setSelectPos(position);
                    presenter.selectSchedule(tv.getText().toString(), position);
                }
            });

            scheduleView.setSelectPos(0);
            presenter.selectSchedule(scheduleView.getTextView(0).getText().toString(), 0);
        }
    }

}

