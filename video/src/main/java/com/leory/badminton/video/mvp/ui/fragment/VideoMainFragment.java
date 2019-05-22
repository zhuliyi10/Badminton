package com.leory.badminton.video.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.leory.badminton.video.R;
import com.leory.badminton.video.R2;
import com.leory.badminton.video.di.component.DaggerVideoComponent;
import com.leory.badminton.video.mvp.contract.VideoListContract;
import com.leory.badminton.video.mvp.model.bean.VideoBean;
import com.leory.badminton.video.mvp.presenter.VideoPresenter;
import com.leory.badminton.video.mvp.ui.adapter.VideoAdapter;
import com.leory.commonlib.base.BaseFragment;
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
 * Describe : 视频首页
 * Author : zhuly
 * Date : 2019-05-22
 */
@Route(path = RouterHub.VIDEO_VIDEOMAINFRAGMENT)
public class VideoMainFragment extends BaseFragment<VideoPresenter> implements VideoListContract.View {
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
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerVideoComponent.builder()
                .view(this)
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_video_main, container, false);
        //监听back必须设置的
        root.setFocusable(true);
        root.setFocusableInTouchMode(true);
        root.setOnKeyListener(backListener);
        return root;
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
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
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
                            if (GSYVideoManager.isFullState(getActivity())) {
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
    public void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
    }


    private View.OnKeyListener backListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                //这边判断,如果是back的按键被点击了   就自己拦截实现掉
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (GSYVideoManager.backFromWindowFull(getActivity())) {
                        return true;//表示处理了
                    }

                }
            }
            return false;
        }
    };
}
