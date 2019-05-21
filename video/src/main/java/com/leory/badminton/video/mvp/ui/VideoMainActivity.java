package com.leory.badminton.video.mvp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.leory.badminton.video.R;
import com.leory.badminton.video.R2;
import com.leory.badminton.video.di.component.DaggerVideoComponent;
import com.leory.badminton.video.mvp.contract.VideoListContract;
import com.leory.badminton.video.mvp.model.bean.VideoBean;
import com.leory.badminton.video.mvp.presenter.VideoPresenter;
import com.leory.badminton.video.mvp.ui.adapter.VideoAdapter;
import com.leory.commonlib.base.BaseActivity;
import com.leory.commonlib.di.component.AppComponent;
import com.leory.commonlib.utils.ToastUtils;
import com.leory.commonlib.widget.XSDToolbar;
import com.leory.commonlib.widget.morePop.MorePopBean;
import com.leory.commonlib.widget.morePop.MorePopView;
import com.leory.interactions.RouterHub;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

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
    @BindView(R2.id.toolbar)
    XSDToolbar toolbar;
    @Inject
    List<MorePopBean> morePopBeans;
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

        toolbar.addMorePopView(morePopBeans, new MorePopView.OnSelectListener() {
            @Override
            public void onItemClick(int pos, String name) {
                presenter.setSelectPos(pos);
                showMessage(name);
            }
        });
        toolbar.getMorePopView().setSelectStateChange(true);
        toolbar.setOnBackListener(new XSDToolbar.OnBackListener() {
            @Override
            public void onBackClick() {
                finish();
            }
        });
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
        refreshLayout.autoRefresh();

        rcv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取最后一个可见view的位置
                    int lastItemPosition = linearManager.findLastVisibleItemPosition();
                    //获取第一个可见view的位置
                    int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                    //大于0说明有播放
                    if (GSYVideoManager.instance().getPlayPosition() >= 0) {
                        //当前播放的位置
                        int position = GSYVideoManager.instance().getPlayPosition();
                        //对应的播放列表TAG
                        if (GSYVideoManager.instance().getPlayTag().equals(videoAdapter.TAG)
                                && (position < firstItemPosition || position > lastItemPosition)) {
                            if (GSYVideoManager.isFullState(VideoMainActivity.this)) {
                                return;
                            }
                            //如果滑出去了上面和下面就是否，和今日头条一样
                            GSYVideoManager.releaseAllVideos();
                            videoAdapter.notifyDataSetChanged();
                        }
                    }
                }
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
        ToastUtils.showShort(message);
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
    public void onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }

}
