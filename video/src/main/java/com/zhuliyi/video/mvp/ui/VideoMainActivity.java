package com.zhuliyi.video.mvp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhuliyi.commonlib.base.BaseActivity;
import com.zhuliyi.commonlib.di.component.AppComponent;
import com.zhuliyi.interactions.RouterHub;
import com.zhuliyi.video.R;
import com.zhuliyi.video.R2;
import com.zhuliyi.video.di.component.DaggerVideoComponent;
import com.zhuliyi.video.mvp.contract.VideoListContract;
import com.zhuliyi.video.mvp.model.bean.VideoBean;
import com.zhuliyi.video.mvp.presenter.VideoPresenter;
import com.zhuliyi.video.mvp.ui.adapter.VideoAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Describe : 视频主页
 * Author : zhuly
 * Date : 2018-06-14
 */

@Route(path = RouterHub.VIDEO_VIDEOMAINACTIVITY)
public class VideoMainActivity extends BaseActivity<VideoPresenter> implements VideoListContract.View {

    @BindView(R2.id.rcv)
    RecyclerView rcv;
    @BindView(R2.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private VideoAdapter videoAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerVideoComponent.builder()
                .view(this)
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_video_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        rcv.setLayoutManager(new LinearLayoutManager(this));
        rcv.setAdapter(videoAdapter = new VideoAdapter(new ArrayList<>()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                presenter.requestData(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.requestData(false);
            }
        });
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        refreshLayout.finishRefresh();
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void startLoadMore() {

    }

    @Override
    public void endLoadMore() {
        refreshLayout.finishLoadMore();
    }

    @Override
    public void showVideoList(List<VideoBean> data, boolean refresh) {
        if (refresh) {
            videoAdapter.setNewData(data);
        } else {
            videoAdapter.addData(data);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
