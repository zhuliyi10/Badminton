package com.zhuliyi.video.mvp.presenter;

import android.util.Log;

import com.zhuliyi.commonlib.di.scope.ActivityScope;
import com.zhuliyi.commonlib.http.RxHandlerSubscriber;
import com.zhuliyi.commonlib.mvp.BasePresenter;
import com.zhuliyi.video.mvp.contract.VideoListContract;
import com.zhuliyi.video.mvp.model.bean.VideoBaseResponse;
import com.zhuliyi.video.mvp.model.bean.VideoBean;
import com.zhuliyi.video.mvp.model.bean.VideoListBean;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Describe : 视频presenter
 * Author : zhuly
 * Date : 2019-05-14
 */
@ActivityScope
public class VideoPresenter extends BasePresenter<VideoListContract.Model, VideoListContract.View> {
    private int lastPage = 0;

    @Inject
    public VideoPresenter(VideoListContract.Model model, VideoListContract.View rootView) {
        super(model, rootView);
        requestData(true);
    }

    public void requestData(boolean refresh) {
        if (refresh) lastPage = 0;
        model.getVideoList(lastPage+1)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if (refresh) {
                        rootView.showLoading();//显示下拉刷新的进度条
                    } else {
                        rootView.startLoadMore();//显示上拉加载更多的进度条
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (refresh)
                        rootView.hideLoading();//隐藏下拉刷新的进度条
                    else
                        rootView.endLoadMore();//隐藏上拉加载更多的进度条
                })
                .subscribe(new RxHandlerSubscriber<VideoBaseResponse<VideoListBean>>() {

                    @Override
                    public void onNext(VideoBaseResponse<VideoListBean> response) {
                        if (response.isSuccess()) {
                            if (response.getData() != null) {
                                int count = response.getData().getTotalcount();
                                lastPage++;
                                List<VideoBean> videoList = response.getData().getList();
                                Log.d(TAG, "onNext: " + videoList.toString());
                                rootView.showVideoList(videoList, refresh);
                            }
                        }
                    }
                });
    }
}
