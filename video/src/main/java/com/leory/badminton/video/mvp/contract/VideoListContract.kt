package com.leory.badminton.video.mvp.contract

import com.leory.badminton.video.mvp.model.bean.VideoBaseResponse
import com.leory.badminton.video.mvp.model.bean.VideoBean
import com.leory.badminton.video.mvp.model.bean.VideoListBean
import com.leory.commonlib.mvp.IModel
import com.leory.commonlib.mvp.IView
import io.reactivex.Observable


/**
 * Describe : 视频列表的接口
 * Author : zhuly
 * Date : 2019-05-14
 */
interface VideoListContract {
    interface View : IView {
        fun startLoadMore()
        fun endLoadMore()
        fun showVideoList(data: List<VideoBean>, refresh: Boolean) //显示视频列表
    }

    interface Model : IModel {
        fun getVideoList(num: Int): Observable<VideoBaseResponse<VideoListBean>>
    }
}
