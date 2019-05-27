package com.leory.badminton.news.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.leory.badminton.news.R;
import com.leory.badminton.news.R2;
import com.leory.badminton.news.di.component.DaggerMatchDetailComponent;
import com.leory.badminton.news.mvp.contract.MatchDetailContract;
import com.leory.badminton.news.mvp.model.bean.MatchInfoBean;
import com.leory.badminton.news.mvp.presenter.MatchDetailPresenter;
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
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Describe : 赛事详情activity
 * Author : leory
 * Date : 2019-05-23
 */
public class MatchDetailActivity extends BaseActivity<MatchDetailPresenter> implements MatchDetailContract.View, SpinnerPopView.OnSelectListener {
    private static final String KEY_DETAIL_URL = "detail_url";
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
    private String[] matchType = new String[]{"男单", "女单", "男双", "女双", "混双"};
    @BindView(R2.id.progress)
    FrameLayout progress;
    @BindView(R2.id.againstFlow)
    AgainFlowView againstFlow;
    @BindView(R2.id.head)
    FrameLayout head;
    @BindView(R2.id.tab_16)
    TextView tab16;
    @BindView(R2.id.tab_8)
    TextView tab8;
    @BindView(R2.id.tab_4)
    TextView tab4;
    @BindView(R2.id.tab_2)
    TextView tab2;
    @BindView(R2.id.tab_1)
    TextView tab1;
    @BindView(R2.id.spinner_type)
    SpinnerPopView spinnerType;
    @BindView(R2.id.tab)
    FrameLayout tab;

    public static void launch(Activity preActivity, String detailUrl) {
        preActivity.startActivity(new Intent(preActivity, MatchDetailActivity.class).putExtra(KEY_DETAIL_URL, detailUrl));
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        String detailUrl = "https://bwfworldtour.bwfbadminton.com/tournament/3376/yonex-all-england-open-badminton-championships-2019/";
        DaggerMatchDetailComponent.builder()
                .view(this)
                .detailUrl(getIntent().getStringExtra(KEY_DETAIL_URL))
                .appComponent(appComponent)
                .build().inject(this);

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_match_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        spinnerType.initData(Arrays.asList(matchType));
        spinnerType.setOnSelectListener(this);
        setScheduleSelected(tab16);
    }


    @OnClick({R2.id.tab_16, R2.id.tab_8, R2.id.tab_4, R2.id.tab_2, R2.id.tab_1})
    public void onViewClicked(View view) {
        setScheduleSelected((TextView) view);
    }

    private void setScheduleSelected(TextView selectedView) {
        if (selectedView.isSelected()) return;
        tab1.setSelected(false);
        tab2.setSelected(false);
        tab4.setSelected(false);
        tab8.setSelected(false);
        tab16.setSelected(false);
        selectedView.setSelected(true);
        presenter.selectSchedule(selectedView.getText().toString());
    }

    @Override
    public void showLoading() {
        progress.setVisibility(View.VISIBLE);
        againstFlow.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progress.setVisibility(View.GONE);
        againstFlow.setVisibility(View.VISIBLE);
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
        txtName.setText(bean.getMatchName());
        txtTime.setText(bean.getMatchDate());
        txtSite.setText(bean.getMatchSite());
        txtBonus.setText(bean.getMatchBonus());
        ImageConfig config=new ImageConfig.Builder()
                .imageView(iconMatch)
                .url(bean.getMatchIcon())
                .build();
        AppUtils.obtainImageLoader().loadImage(this,config);
    }
}

