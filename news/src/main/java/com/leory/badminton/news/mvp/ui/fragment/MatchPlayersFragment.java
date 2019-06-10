package com.leory.badminton.news.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.leory.badminton.news.R;
import com.leory.badminton.news.R2;
import com.leory.badminton.news.di.component.MatchDetailComponent;
import com.leory.badminton.news.mvp.contract.MatchDetailContract;
import com.leory.badminton.news.mvp.model.bean.MultiMatchPlayersBean;
import com.leory.badminton.news.mvp.presenter.MatchPlayersPresenter;
import com.leory.badminton.news.mvp.ui.adapter.MatchPlayersAdapter;
import com.leory.badminton.news.mvp.ui.widget.MatchTabView;
import com.leory.badminton.news.mvp.ui.widget.decoration.MatchDateItemDecoration;
import com.leory.commonlib.base.BaseLazyLoadFragment;
import com.leory.commonlib.base.delegate.IComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Describe : 参赛运动员
 * Author : leory
 * Date : 2019-06-10
 */
public class MatchPlayersFragment extends BaseLazyLoadFragment<MatchPlayersPresenter> implements MatchDetailContract.MatchPlayersView {
    @BindView(R2.id.rcv)
    RecyclerView rcv;
    @BindView(R2.id.progress)
    FrameLayout progress;
    MatchTabView tab;
    private MatchPlayersAdapter adapter;

    public static MatchPlayersFragment newInstance() {
        MatchPlayersFragment fragment = new MatchPlayersFragment();
        return fragment;
    }

    @Override
    public IComponent setupActivityComponent(IComponent component) {
        ((MatchDetailComponent) component).buildMatchPlayersComponent()
                .view(this)
                .build()
                .inject(this);
        return super.setupActivityComponent(component);
    }

    @Override
    protected void lazyLoadData() {
        String[] types = new String[]{"男单", "女单", "男双", "女双", "混双"};
        tab.initData(Arrays.asList(types));
        tab.setOnChildClickListener(new MatchTabView.OnChildClickListener() {
            @Override
            public void onClick(TextView tv, int position) {
                tab.setSelectPos(position);
                presenter.requestData(types[position]);
            }
        });
        tab.setSelectPos(0);
        presenter.requestData(types[0]);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_match_players, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv.addItemDecoration(new MatchDateItemDecoration(getContext()));
        ConstraintLayout head = (ConstraintLayout) LayoutInflater.from(getContext()).inflate(R.layout.head_match_players, null);
        tab = head.findViewById(R.id.tab);
        rcv.setAdapter(adapter = new MatchPlayersAdapter(new ArrayList<>()));
        adapter.addHeaderView(head);
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
    public void showPlayersData(List<MultiMatchPlayersBean> data) {
        adapter.setNewData(data);
    }
}
