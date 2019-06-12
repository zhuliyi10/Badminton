package com.leory.badminton.news.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.leory.badminton.news.R;
import com.leory.badminton.news.R2;
import com.leory.badminton.news.di.component.PlayerDetailComponent;
import com.leory.badminton.news.mvp.contract.PlayerContract;
import com.leory.badminton.news.mvp.model.bean.PlayerMatchBean;
import com.leory.badminton.news.mvp.presenter.PlayerMatchPresenter;
import com.leory.badminton.news.mvp.ui.adapter.PlayerMatchAdapter;
import com.leory.badminton.news.mvp.ui.widget.decoration.MatchDateItemDecoration;
import com.leory.commonlib.base.BaseLazyLoadFragment;
import com.leory.commonlib.base.delegate.IComponent;
import com.leory.commonlib.di.scope.FragmentScope;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Describe : 运动员比赛结果
 * Author : leory
 * Date : 2019-06-11
 */
@FragmentScope
public class PlayerMatchFragment extends BaseLazyLoadFragment<PlayerMatchPresenter> implements PlayerContract.MatchView {

    PlayerMatchAdapter adapter;
    @BindView(R2.id.rcv)
    RecyclerView rcv;
    @BindView(R2.id.progress)
    FrameLayout progress;

    public static PlayerMatchFragment newInstance() {
        return new PlayerMatchFragment();
    }

    @Override
    public IComponent setupActivityComponent(IComponent component) {
        ((PlayerDetailComponent) component).buildPlayerMatchComponent()
                .view(this)
                .build()
                .inject(this);
        return super.setupActivityComponent(component);
    }

    @Override
    protected void lazyLoadData() {
        presenter.requestData(null);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_player_match, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv.addItemDecoration(new MatchDateItemDecoration(getContext()));
        rcv.setAdapter(adapter = new PlayerMatchAdapter(new ArrayList<>()));
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
    public void showMatchData(List<PlayerMatchBean> data) {
        adapter.setNewData(data);
    }

}
