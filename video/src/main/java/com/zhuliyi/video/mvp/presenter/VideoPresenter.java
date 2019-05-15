package com.zhuliyi.video.mvp.presenter;

import android.util.Log;

import com.zhuliyi.commonlib.di.scope.ActivityScope;
import com.zhuliyi.commonlib.http.RxHandlerSubscriber;
import com.zhuliyi.commonlib.mvp.BasePresenter;
import com.zhuliyi.commonlib.utils.RxUtils;
import com.zhuliyi.video.mvp.contract.VideoListContract;
import com.zhuliyi.video.mvp.model.bean.VideoBaseResponse;
import com.zhuliyi.video.mvp.model.bean.VideoBean;
import com.zhuliyi.video.mvp.model.bean.VideoListBean;

import java.util.List;

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
                .compose(RxUtils.<VideoBaseResponse<VideoListBean>>applySchedulers(rootView))
                .subscribe(new RxHandlerSubscriber<VideoBaseResponse<VideoListBean>>() {

                    @Override
                    public void onNext(VideoBaseResponse<VideoListBean> response) {
                        if (response.isSuccess()) {
                            if (response.getData() != null) {
                                int count = response.getData().getTotalcount();
                                List<VideoBean> videoList = response.getData().getList();
                                Log.d(TAG, "onNext: " + videoList.toString());
                                rootView.showVideoList(videoList);
                            }
                        }
                    }
                });
    }
}
