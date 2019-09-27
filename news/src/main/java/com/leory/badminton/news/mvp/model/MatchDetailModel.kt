package com.leory.badminton.news.mvp.model

import com.leory.badminton.news.mvp.contract.MatchDetailContract
import com.leory.badminton.news.mvp.model.api.MatchApi
import com.leory.commonlib.di.scope.ActivityScope
import com.leory.commonlib.http.IRepositoryManager
import com.leory.commonlib.mvp.BaseModel

import javax.inject.Inject

import io.reactivex.Observable

/**
 * Describe :赛事详情model
 * Author : leory
 * Date : 2019-05-27
 */
@ActivityScope
class MatchDetailModel @Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), MatchDetailContract.Model {

    private val ajaxTmt = "bwfdraw"
    private val bwfdate = "bwfdate"
    private val ajax = "bwfstats"
    private val stab = "result"

    override fun getMatchInfo(url: String): Observable<String> {
        return repositoryManager.obtainRetrofitService(MatchApi::class.java).getMatchInfo(url)
    }

    override fun getMatchDetail(url: String): Observable<String> {
        return repositoryManager.obtainRetrofitService(MatchApi::class.java).getMatchDetail(url, ajaxTmt)
    }

    override fun getMatchDate(url: String): Observable<String> {
        return repositoryManager.obtainRetrofitService(MatchApi::class.java).getMatchDate(url, bwfdate)
    }

    override fun getMatchDate(url: String, match: String): Observable<String> {
        return repositoryManager.obtainRetrofitService(MatchApi::class.java).getMatchDate(url, match, ajax, stab)
    }

    override fun getMatchHistory(url: String): Observable<String> {
        return repositoryManager.obtainRetrofitService(MatchApi::class.java).getMatchHistory(url)
    }

    override fun getMatchPlayers(url: String, tab: String): Observable<String> {
        return repositoryManager.obtainRetrofitService(MatchApi::class.java).getMatchPlayers(url, tab)
    }
}
