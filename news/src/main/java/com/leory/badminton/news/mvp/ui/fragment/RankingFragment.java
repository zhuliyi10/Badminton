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
import com.leory.badminton.news.di.component.DaggerRankingComponent;
import com.leory.badminton.news.mvp.contract.RankingContract;
import com.leory.badminton.news.mvp.model.bean.RankingBean;
import com.leory.badminton.news.mvp.presenter.RankingPresenter;
import com.leory.badminton.news.mvp.ui.adapter.RankingAdapter;
import com.leory.badminton.news.mvp.ui.widget.spinner.SpinnerPopView;
import com.leory.commonlib.base.BaseLazyLoadFragment;
import com.leory.commonlib.base.delegate.IComponent;
import com.leory.commonlib.di.component.AppComponent;
import com.leory.commonlib.utils.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Describe :排名fragment
 * Author : leory
 * Date : 2019-05-19
 */
public class RankingFragment extends BaseLazyLoadFragment<RankingPresenter> implements RankingContract.View, OnLoadMoreListener {
    @BindView(R2.id.spinner_week)
    SpinnerPopView spinnerWeek;
    @BindView(R2.id.spinner_type)
    SpinnerPopView spinnerType;
    @BindView(R2.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R2.id.rcv)
    RecyclerView rcv;
    @BindView(R2.id.progressBar)
    ProgressBar progressBar;

    public static String[] RANKING_TYPES = new String[]{"男单", "女单", "男双", "女双", "混双"};
    private RankingAdapter adapter;


    @Override
    public IComponent setupActivityComponent(IComponent component) {
        DaggerRankingComponent.builder()
                .appComponent((AppComponent) component)
                .view(this)
                .build()
                .inject(this);
        return super.setupActivityComponent(component);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ranking, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv.setAdapter(adapter = new RankingAdapter(new ArrayList<>()));
        refreshLayout.setOnLoadMoreListener(this);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadMore(false);
    }

    @Override
    protected void lazyLoadData() {
        presenter.firstInit();
    }

    @Override
    public void startLoadMore() {

    }

    @Override
    public void endLoadMore() {
        refreshLayout.finishLoadMore();
    }

    @Override
    public void showRankingData(boolean refresh, List<RankingBean> data) {
        if (refresh) {
            adapter.setNewData(data);
        } else {
            adapter.addData(data);
        }
    }

    @Override
    public void showWeekData(List<String> data) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                spinnerWeek.initData(data);
                spinnerWeek.setOnSelectListener(new SpinnerPopView.OnSelectListener() {
                    @Override
                    public void onItemClick(int pos, String name) {
                        presenter.selectData(true, spinnerType.getSelectName(), name);
                    }
                });
                spinnerType.initData(Arrays.asList(RANKING_TYPES));
                spinnerType.setOnSelectListener(new SpinnerPopView.OnSelectListener() {
                    @Override
                    public void onItemClick(int pos, String name) {
                        presenter.selectData(true, name, spinnerWeek.getSelectName());
                    }
                });
                refreshLayout.setEnableLoadMore(true);
            }
        });

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
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        presenter.selectData(false, spinnerType.getSelectName(), spinnerWeek.getSelectName());
    }
}
