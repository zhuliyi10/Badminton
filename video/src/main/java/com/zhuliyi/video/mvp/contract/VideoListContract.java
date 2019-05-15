package com.zhuliyi.video.mvp.contract;

import com.zhuliyi.commonlib.mvp.IModel;
import com.zhuliyi.commonlib.mvp.IView;
import com.zhuliyi.video.mvp.model.bean.VideoBaseResponse;
import com.zhuliyi.video.mvp.model.bean.VideoBean;
import com.zhuliyi.video.mvp.model.bean.VideoListBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * Describe : 视频列表的接口
 * Author : zhuly
 * Date : 2019-05-14
 */
public interface VideoListContract {
    interface View extends IView{
        void showVideoList(List<VideoBean>data);//显示视频列表
    }

    interface Model extends IModel{
        Observable<VideoBaseResponse<VideoListBean>> getVideoList(int num);
    }
}
