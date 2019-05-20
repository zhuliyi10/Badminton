package com.leory.badminton.news.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.leory.badminton.news.R;
import com.leory.badminton.news.R2;
import com.leory.badminton.news.di.component.DaggerMatchComponent;
import com.leory.badminton.news.mvp.contract.MatchContract;
import com.leory.badminton.news.mvp.model.bean.MatchItemSection;
import com.leory.badminton.news.mvp.presenter.MatchPresenter;
import com.leory.badminton.news.mvp.ui.adapter.MatchSectionAdapter;
import com.leory.badminton.news.mvp.ui.widget.spinner.SpinnerPopView;
import com.leory.commonlib.base.BaseFragment;
import com.leory.commonlib.di.component.AppComponent;
import com.leory.commonlib.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Describe :赛事fragment
 * Author : leory
 * Date : 2019-05-19
 */
public class MatchFragment extends BaseFragment<MatchPresenter> implements MatchContract.View {


    @BindView(R2.id.spinner_year)
    SpinnerPopView spinnerYear;
    @BindView(R2.id.spinner_finish)
    SpinnerPopView spinnerFinish;
    @BindView(R2.id.rcv)
    RecyclerView rcv;
    MatchSectionAdapter adapter;
    @BindView(R2.id.progressBar)
    ProgressBar progressBar;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMatchComponent.builder()
                .view(this)
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_match, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String[] years = new String[]{"2020", "2019", "2018", "2017", "2016", "2015", "2014"};
        spinnerYear.initData(Arrays.asList(years), 1);
        spinnerYear.setOnSelectListener(new SpinnerPopView.OnSelectListener() {
            @Override
            public void onItemClick(int pos, String name) {
                presenter.requestData(name, spinnerFinish.getSelectName());
            }
        });
        String[] finishes = new String[]{"全部", "已完成", "剩余"};
        spinnerFinish.initData(Arrays.asList(finishes), 2);
        spinnerFinish.setOnSelectListener(new SpinnerPopView.OnSelectListener() {
            @Override
            public void onItemClick(int pos, String name) {
                presenter.requestData(spinnerYear.getSelectName(), name);
            }
        });
        presenter.requestData(spinnerYear.getSelectName(), spinnerFinish.getSelectName());
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MatchSectionAdapter(new ArrayList<>());
        rcv.setAdapter(adapter);
        adapter.addHeaderView(LayoutInflater.from(getActivity()).inflate(R.layout.head_match, null));
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        rcv.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        rcv.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtils.showShort(message);
    }

    @Override
    public void showMatchData(List<MatchItemSection> data) {
        adapter.setNewData(data);
    }

}
