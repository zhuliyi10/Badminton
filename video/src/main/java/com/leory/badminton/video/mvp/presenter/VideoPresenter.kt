package com.leory.badminton.video.mvp.presenter

import android.text.TextUtils
import com.leory.badminton.video.mvp.contract.VideoListContract
import com.leory.badminton.video.mvp.model.bean.VideoBaseResponse
import com.leory.badminton.video.mvp.model.bean.VideoBean
import com.leory.badminton.video.mvp.model.bean.VideoListBean
import com.leory.commonlib.di.scope.ActivityScope
import com.leory.commonlib.http.RxHandlerSubscriber
import com.leory.commonlib.mvp.BasePresenter
import com.leory.commonlib.utils.RxLifecycleUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

/**
 * Describe : 视频presenter
 * Author : zhuly
 * Date : 2019-05-14
 */
@ActivityScope
class VideoPresenter @Inject constructor(model: VideoListContract.Model, rootView: VideoListContract.View) :
        BasePresenter<VideoListContract.Model, VideoListContract.View>(model, rootView) {
    private var lastPage = 0
    private var selectPos = 0
    private var sourceData: MutableList<VideoBean>? = null

    fun requestData(refresh: Boolean) {
        if (refresh) lastPage = 0
        model.getVideoList(lastPage + 1)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    if (refresh) {
                        rootView.showLoading()//显示下拉刷新的进度条
                    } else {
                        rootView.startLoadMore()//显示上拉加载更多的进度条
                    }
                }.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    if (refresh)
                        rootView.hideLoading()//隐藏下拉刷新的进度条
                    else
                        rootView.endLoadMore()//隐藏上拉加载更多的进度条
                }
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(object : RxHandlerSubscriber<VideoBaseResponse<VideoListBean>>() {

                    override fun onNext(response: VideoBaseResponse<VideoListBean>) {
                        if (response.isSuccess) {
                            if (response.data != null) {
                                val count = response.data!!.totalcount
                                lastPage++
                                val list = response.data!!.list
                                if (refresh) {
                                    sourceData = list
                                } else {
                                    if (sourceData == null) sourceData = ArrayList()
                                    sourceData!!.addAll(list as MutableList<VideoBean>)
                                }
                                updateListView(list, refresh)

                            }
                        }
                    }
                })
    }

    /**
     * 设置选中的位置
     *
     * @param pos
     */
    fun setSelectPos(pos: Int) {
        this.selectPos = pos
        updateListView(null, true)
    }

    private fun updateListView(list: List<VideoBean>?, refresh: Boolean) {
        var list = list
        if (list == null) list = sourceData
        if (list != null) {
            val data = ArrayList<VideoBean>()
            for (bean in list) {
                var second: Int = bean.totalTimes?.let { getSecondTime(it) }?:-1

                when (selectPos) {
                    0 -> data.add(bean)
                    1 -> if (second < 60) {
                        data.add(bean)
                    }
                    2 -> if (second >= 60) {
                        data.add(bean)
                    }
                    3 -> if (second < 60 * 10) {
                        data.add(bean)
                    }
                    4 -> if (second >= 60 * 10) {
                        data.add(bean)
                    }
                    5 -> if (second < 60 * 30) {
                        data.add(bean)
                    }
                    6 -> if (second >= 60 * 30) {
                        data.add(bean)
                    }
                }
            }
            if (data.size <= 0) {
                requestData(false)

            }
            rootView.showVideoList(data, refresh)


        }
    }

    private fun getSecondTime(time: String): Int {
        return if (TextUtils.isDigitsOnly(time)) {
            Integer.parseInt(time)
        } else -1
    }
}
