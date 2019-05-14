package com.zhuliyi.video.mvp.presenter;

import com.zhuliyi.commonlib.di.scope.ActivityScope;
import com.zhuliyi.commonlib.http.RxHandlerSubscriber;
import com.zhuliyi.commonlib.mvp.BasePresenter;
import com.zhuliyi.commonlib.utils.RxUtils;
import com.zhuliyi.commonlib.utils.ToastUtils;
import com.zhuliyi.video.mvp.contract.VideoListContract;

import javax.inject.Inject;

/**
 * Describe : 视频presenter
 * Author : zhuly
 * Date : 2019-05-14
 */
@ActivityScope
public class VideoPresenter extends BasePresenter<VideoListContract.Model, VideoListContract.View> {
    @Inject
    public VideoPresenter(VideoListContract.Model model, VideoListContract.View rootView) {
        super(model, rootView);
        requestData();
    }

    public void requestData() {
        model.getVideoList(1)
                .compose(RxUtils.applySchedulers(rootView))
                .subscribe(new RxHandlerSubscriber<Object>() {
                    @Override
                    public void onNext(Object o) {
                        ToastUtils.showLong(0);
                    }
                });
    }
}
