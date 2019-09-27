package com.leory.badminton.news.mvp.model

import com.leory.badminton.news.mvp.contract.LiveContract
import com.leory.badminton.news.mvp.model.api.LiveApi
import com.leory.commonlib.di.scope.FragmentScope
import com.leory.commonlib.http.IRepositoryManager
import com.leory.commonlib.mvp.BaseModel
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Describe :直播model
 * Author : leory
 * Date : 2019-05-19
 */
@FragmentScope
class LiveModel @Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), LiveContract.Model {

    override val liveMatch: Observable<String>
        get() = repositoryManager.obtainRetrofitService(LiveApi::class.java).getLiveMatch(url)

    override fun getLiveDetail(url: String?): Observable<String> {
        return repositoryManager.obtainRetrofitService(LiveApi::class.java).getLiveDetail(url!!, ajaxSchedule)
    }

    override fun getLiveUrl(url: String): Observable<String> {
        return repositoryManager.obtainRetrofitService(LiveApi::class.java).getLiveUrl(url)
    }

    companion object {
        private const val url = "https://bwfworldtour.bwfbadminton.com/live/"


        private const val ajaxSchedule = 1
    }
}
