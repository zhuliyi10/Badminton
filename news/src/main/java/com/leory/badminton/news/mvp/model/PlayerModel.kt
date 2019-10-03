package com.leory.badminton.news.mvp.model

import com.leory.badminton.news.mvp.contract.PlayerContract
import com.leory.badminton.news.mvp.model.api.PlayerApi
import com.leory.commonlib.di.scope.ActivityScope
import com.leory.commonlib.http.IRepositoryManager
import com.leory.commonlib.mvp.BaseModel

import javax.inject.Inject

import io.reactivex.Observable

/**
 * Describe : 运动员model
 * Author : leory
 * Date : 2019-06-11
 */
@ActivityScope
class PlayerModel @Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), PlayerContract.Model {

    override fun getPlayerDetail(url: String): Observable<String> {
        return repositoryManager.obtainRetrofitService(PlayerApi::class.java)
                .getPlayerDetail(url)
    }

    override fun getPlayerMatches(url: String, year: String?): Observable<String> {
        return repositoryManager.obtainRetrofitService(PlayerApi::class.java)
                .getPlayerMatch(url, year)
    }
}
