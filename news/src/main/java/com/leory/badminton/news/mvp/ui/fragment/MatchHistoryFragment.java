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
import com.leory.badminton.news.di.component.MatchDetailComponent;
import com.leory.badminton.news.mvp.contract.MatchDetailContract;
import com.leory.badminton.news.mvp.model.bean.MultiMatchHistoryBean;
import com.leory.badminton.news.mvp.presenter.MatchHistoryPresenter;
import com.leory.badminton.news.mvp.ui.adapter.MatchHistoryAdapter;
import com.leory.badminton.news.mvp.ui.widget.decoration.MatchDateItemDecoration;
import com.leory.commonlib.base.BaseLazyLoadFragment;
import com.leory.commonlib.base.delegate.IComponent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Describe : 历史比赛fragment
 * Author : leory
 * Date : 2019-06-06
 */
public class MatchHistoryFragment extends BaseLazyLoadFragment<MatchHistoryPresenter> implements MatchDetailContract.MatchHistory {
    private static final String KEY_HISTORY_URL = "key_history_url";

    @BindView(R2.id.rcv)
    RecyclerView rcv;
    @BindView(R2.id.progress)
    ProgressBar progress;

    private MatchHistoryAdapter adapter;

    public static MatchHistoryFragment newInstance(String historyUrl) {
        MatchHistoryFragment fragment = new MatchHistoryFragment();
        Bundle args = new Bundle();
        args.putString(KEY_HISTORY_URL, historyUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public IComponent setupActivityComponent(IComponent component) {
        ((MatchDetailComponent) component).buildMatchHistoryComponent()
                .view(this)
                .historyUrl(getArguments().getString(KEY_HISTORY_URL))
                .build()
                .inject(this);
        return super.setupActivityComponent(component);
    }

    @Override
    protected void lazyLoadData() {
        presenter.requestData();
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_match_history, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv.addItemDecoration(new MatchDateItemDecoration(getContext()));
        rcv.setAdapter(adapter = new MatchHistoryAdapter(new ArrayList<>()));
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
    public void showHistoryData(List<MultiMatchHistoryBean> data) {
        adapter.setNewData(data);
    }

}
