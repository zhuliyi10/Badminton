package com.leory.badminton.news.mvp.model

import com.leory.badminton.news.mvp.contract.HandOffRecordContract
import com.leory.badminton.news.mvp.model.api.PlayerApi
import com.leory.commonlib.di.scope.ActivityScope
import com.leory.commonlib.http.IRepositoryManager
import com.leory.commonlib.mvp.BaseModel

import javax.inject.Inject

import io.reactivex.Observable

/**
 * Describe : 交手记录model
 * Author : leory
 * Date : 2019-07-23
 */
@ActivityScope
class HandOffRecordModel @Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), HandOffRecordContract.Model {

    override fun getHandOffRecords(url: String): Observable<String> {
        return repositoryManager.obtainRetrofitService(PlayerApi::class.java).getHandOffRecords(url)
    }
}
