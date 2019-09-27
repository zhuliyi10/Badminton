package com.leory.badminton.news.mvp.model

import android.text.TextUtils
import com.leory.badminton.news.mvp.contract.RankingContract
import com.leory.badminton.news.mvp.model.api.RankingApi
import com.leory.commonlib.di.scope.FragmentScope
import com.leory.commonlib.http.IRepositoryManager
import com.leory.commonlib.mvp.BaseModel
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Describe :赛事model
 * Author : leory
 * Date : 2019-05-19
 */
@FragmentScope
class RankingModel @Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), RankingContract.Model {


    override fun getRankingList(rankingType: String?, week: String?, pageNum: Int, num: Int): Observable<String> {
        val urlBuffer = StringBuffer().append(url)
        if (!TextUtils.isEmpty(rankingType)) {
            urlBuffer.append(rankingType)
        }
        if (!TextUtils.isEmpty(week)) {
            urlBuffer.append(week)
        }
        return repositoryManager.obtainRetrofitService(RankingApi::class.java).getRankingList(urlBuffer.toString(), pageNum, num)
    }

    companion object {
        private const val url = "https://bwfbadminton.cn/rankings/2/bwf-world-rankings/"
    }
}
