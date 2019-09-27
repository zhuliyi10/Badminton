package com.leory.badminton.news.mvp.model

import com.leory.badminton.news.mvp.contract.MatchContract
import com.leory.badminton.news.mvp.model.api.MatchApi
import com.leory.commonlib.di.scope.FragmentScope
import com.leory.commonlib.http.IRepositoryManager
import com.leory.commonlib.mvp.BaseModel

import javax.inject.Inject

import io.reactivex.Observable

/**
 * Describe :赛事model
 * Author : leory
 * Date : 2019-05-19
 */
@FragmentScope
class MatchModel @Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), MatchContract.Model {

    override fun getMatchList(ryear: String, rstate: String): Observable<String> {
        return repositoryManager.obtainRetrofitService(MatchApi::class.java)
                .getMatchList("$url$ryear/$rstate/", ajax, ryear, rstate)
    }

    companion object {
        private const val url = "https://bwfbadminton.cn/calendar/"
        private const val ajax = "bwfresultlanding"
    }
}
