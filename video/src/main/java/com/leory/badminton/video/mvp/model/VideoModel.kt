package com.leory.badminton.video.mvp.model

import com.leory.badminton.video.mvp.contract.VideoListContract
import com.leory.badminton.video.mvp.model.api.VideoApi
import com.leory.badminton.video.mvp.model.bean.VideoBaseResponse
import com.leory.badminton.video.mvp.model.bean.VideoListBean
import com.leory.commonlib.di.scope.ActivityScope
import com.leory.commonlib.http.IRepositoryManager
import com.leory.commonlib.mvp.BaseModel
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Describe : 视频的数据操作类
 * Author : zhuly
 * Date : 2019-05-14
 */
@ActivityScope
class VideoModel @Inject constructor(repositoryManager: IRepositoryManager) :
        BaseModel(repositoryManager), VideoListContract.Model {

    companion object {
        private const val url = "http://v.zhibo.tv/ajax/appnesting/search"
        private const val rnd = "0.19737283560050511"
        private const val key = "羽毛球"
        private const val _csrf = "WE9xeFlXc1g1IysfChMQMjwjJDJrMiQXB3o3CAE4FS40eBIMK28GLQ=="
    }

    override fun getVideoList(num: Int): Observable<VideoBaseResponse<VideoListBean>> {
        return repositoryManager.obtainRetrofitService(VideoApi::class.java)
                .getVideoList(url, key, num, rnd, _csrf)
    }


}
