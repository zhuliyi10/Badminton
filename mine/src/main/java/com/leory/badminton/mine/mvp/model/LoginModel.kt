package com.leory.badminton.mine.mvp.model

import com.leory.badminton.mine.mvp.contract.LoginContract
import com.leory.badminton.mine.mvp.model.api.LoginApi
import com.leory.badminton.mine.mvp.model.bean.UserInfoBean
import com.leory.commonBusiness.http.BaseBusinessBean
import com.leory.commonlib.di.scope.ActivityScope
import com.leory.commonlib.http.IRepositoryManager
import com.leory.commonlib.mvp.BaseModel
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Describe : 登陆 model
 * Author : leory
 * Date : 2019-07-11
 */
@ActivityScope
class LoginModel @Inject constructor(repositoryManager: IRepositoryManager) :
        BaseModel(repositoryManager), LoginContract.Model {
    private var url = "http://192.168.0.92:8000/login/"

    override fun login(phone: String, pwd: String): Observable<BaseBusinessBean<UserInfoBean>> {
        return repositoryManager.obtainRetrofitService(LoginApi::class.java).login(url, phone, pwd)
    }
}
