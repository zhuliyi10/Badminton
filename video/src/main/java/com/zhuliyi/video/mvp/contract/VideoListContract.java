package com.zhuliyi.video.mvp.contract;

import com.zhuliyi.commonlib.mvp.IModel;
import com.zhuliyi.commonlib.mvp.IView;

import io.reactivex.Observable;


/**
 * Describe : 视频列表的接口
 * Author : zhuly
 * Date : 2019-05-14
 */
public interface VideoListContract {
    interface View extends IView{

    }

    interface Model extends IModel{
        Observable<Object> getVideoList(int num);
    }
}
