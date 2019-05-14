package com.zhuliyi.video.mvp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zhuliyi.commonlib.base.BaseActivity;
import com.zhuliyi.commonlib.di.component.AppComponent;
import com.zhuliyi.interactions.RouterHub;
import com.zhuliyi.video.R;
import com.zhuliyi.video.di.component.DaggerVideoComponent;
import com.zhuliyi.video.mvp.contract.VideoListContract;
import com.zhuliyi.video.mvp.presenter.VideoPresenter;

/**
 * Describe : 视频主页
 * Author : zhuly
 * Date : 2018-06-14
 */

@Route(path = RouterHub.VIDEO_VIDEOMAINACTIVITY)
public class VideoMainActivity extends BaseActivity<VideoPresenter> implements VideoListContract.View {


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

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}
