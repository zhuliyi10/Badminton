package com.leory.badminton.video.mvp.contract;

import com.leory.commonlib.mvp.IModel;
import com.leory.commonlib.mvp.IView;
import com.leory.badminton.video.mvp.model.bean.VideoBaseResponse;
import com.leory.badminton.video.mvp.model.bean.VideoBean;
import com.leory.badminton.video.mvp.model.bean.VideoListBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * Describe : 视频列表的接口
 * Author : zhuly
 * Date : 2019-05-14
 */
public interface VideoListContract {
    interface View extends IView{
        void startLoadMore();
        void endLoadMore();
        void showVideoList(List<VideoBean>data,boolean refresh);//显示视频列表
    }

    interface Model extends IModel{
        Observable<VideoBaseResponse<VideoListBean>> getVideoList(int num);
    }
}
