package com.leory.badminton.news.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.leory.badminton.news.R;
import com.leory.badminton.news.R2;
import com.leory.badminton.news.di.component.MatchDetailComponent;
import com.leory.badminton.news.mvp.contract.MatchDetailContract;
import com.leory.badminton.news.mvp.presenter.MatchAgainstPresenter;
import com.leory.badminton.news.mvp.ui.widget.MatchTabView;
import com.leory.badminton.news.mvp.ui.widget.againstFlow.AgainFlowView;
import com.leory.badminton.news.mvp.ui.widget.againstFlow.AgainstFlowBean;
import com.leory.badminton.news.mvp.ui.widget.spinner.SpinnerPopView;
import com.leory.commonlib.base.BaseLazyLoadFragment;
import com.leory.commonlib.base.delegate.IComponent;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Describe : 对阵表
 * Author : leory
 * Date : 2019-06-04
 */

public class MatchAgainstChartFragment extends BaseLazyLoadFragment<MatchAgainstPresenter> implements MatchDetailContract.MatchAgainView, SpinnerPopView.OnSelectListener {
    private String[] matchType = new String[]{"男单", "女单", "男双", "女双", "混双"};
    @BindView(R2.id.scheduleView)
    MatchTabView scheduleView;
    @BindView(R2.id.spinner_type)
    SpinnerPopView spinnerType;
    @BindView(R2.id.tab)
    FrameLayout tab;
    @BindView(R2.id.progress)
    FrameLayout progress;
    @BindView(R2.id.againstFlow)
    AgainFlowView againstFlow;

    public static MatchAgainstChartFragment newInstance() {
        MatchAgainstChartFragment fragment = new MatchAgainstChartFragment();
        return fragment;
    }

    @Override
    protected void lazyLoadData() {
        presenter.requestData(null);
    }

    @Override
    public IComponent setupActivityComponent(IComponent component) {
        ((MatchDetailComponent) component).buildMatchAgainstComponent()
                .view(this)
                .build()
                .inject(this);
        return super.setupActivityComponent(component);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_match_against_chart, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        spinnerType.initData(Arrays.asList(matchType));
        spinnerType.setOnSelectListener(this);
        if (presenter.isGroup()) {
            spinnerType.setVisibility(View.GONE);

        }
    }

    @Override
    public void showLoading() {
        progress.setVisibility(View.VISIBLE);
        againstFlow.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        if (progress != null) {
            progress.setVisibility(View.GONE);
            againstFlow.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void showAgainstView(List<List<AgainstFlowBean>> againstData) {
        againstFlow.setAgainstData(againstData);
    }

    @Override
    public void showMatchSchedule(List<String> tags) {
        if (tags != null && tags.size() > 0) {
            scheduleView.initData(tags);
            scheduleView.setOnChildClickListener(new MatchTabView.OnChildClickListener() {
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

    @Override
    public void onItemClick(int pos, String name) {
        presenter.requestData(name);
    }
}
