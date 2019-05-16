package com.zhuliyi.video.mvp.presenter;

import android.text.TextUtils;

import com.zhuliyi.commonlib.di.scope.ActivityScope;
import com.zhuliyi.commonlib.http.RxHandlerSubscriber;
import com.zhuliyi.commonlib.mvp.BasePresenter;
import com.zhuliyi.video.mvp.contract.VideoListContract;
import com.zhuliyi.video.mvp.model.bean.VideoBaseResponse;
import com.zhuliyi.video.mvp.model.bean.VideoBean;
import com.zhuliyi.video.mvp.model.bean.VideoListBean;

import java.util.ArrayList;
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
    private int selectPos = 0;
    private List<VideoBean> sourceData;

    @Inject
    public VideoPresenter(VideoListContract.Model model, VideoListContract.View rootView) {
        super(model, rootView);
        requestData(true);
    }

    public void requestData(boolean refresh) {
        if (refresh) lastPage = 0;
        model.getVideoList(lastPage + 1)
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
                                List<VideoBean> list = response.getData().getList();
                                if (refresh) {
                                    sourceData = list;
                                } else {
                                    if (sourceData == null) sourceData = new ArrayList<>();
                                    sourceData.addAll(list);
                                }
                                updateListView(list, refresh);

                            }
                        }
                    }
                });
    }

    /**
     * 设置选中的位置
     *
     * @param pos
     */
    public void setSelectPos(int pos) {
        this.selectPos = pos;
        updateListView(null, true);
    }

    private void updateListView(List<VideoBean> list, boolean refresh) {
        if (list == null) list = sourceData;
        if (list != null) {
            List<VideoBean> data = new ArrayList<>();
            for (VideoBean bean : list) {
                int second = -1;
                if (!TextUtils.isEmpty(bean.getTotalTimes())) {
                    second = getSecondTime(bean.getTotalTimes());
                }
                if (selectPos == 0) {
                    data.add(bean);
                } else if (selectPos == 1) {
                    if (second < 60) {
                        data.add(bean);
                    }
                } else if (selectPos == 2) {
                    if (second >= 60) {
                        data.add(bean);
                    }
                } else if (selectPos == 3) {
                    if (second < 60 * 10) {
                        data.add(bean);
                    }
                } else if (selectPos == 4) {
                    if (second >= 60 * 10) {
                        data.add(bean);
                    }
                } else if (selectPos == 5) {
                    if (second < 60 * 30) {
                        data.add(bean);
                    }
                } else if (selectPos == 6) {
                    if (second >= 60 * 30) {
                        data.add(bean);
                    }
                }
            }
            if(data.size()<=0){
                requestData(false);

            }
            rootView.showVideoList(data, refresh);


        }
    }

    private int getSecondTime(String time) {
        if (TextUtils.isDigitsOnly(time)) {
            return Integer.parseInt(time);
        }
        return -1;
    }
}
