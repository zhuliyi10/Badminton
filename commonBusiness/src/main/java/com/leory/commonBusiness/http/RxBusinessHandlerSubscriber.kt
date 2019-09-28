package com.leory.commonBusiness.http

import com.leory.commonlib.http.ResponseErrorListener
import com.leory.commonlib.http.ResponseErrorListenerImpl

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Describe : Rx处理retrofit回调
 * Author : leory
 * Date : 2019-07-12
 */
abstract class RxBusinessHandlerSubscriber<T> : Observer<BaseBusinessBean<T>> {
    private val errorListener: ResponseErrorListener

    init {
        errorListener = ResponseErrorListenerImpl()
    }

    abstract fun onSuccess(data: T?)

    abstract fun onFail(errorMsg: String)

    override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(tBaseBusinessBean: BaseBusinessBean<T>) {
        if (tBaseBusinessBean.code == 0) {
            onSuccess(tBaseBusinessBean.data)
        } else {
            tBaseBusinessBean.message?.let { onFail(it) }
        }
    }

    override fun onError(e: Throwable) {
        val errorMsg = errorListener.onError(e)
        errorMsg?.let { onFail(it) }
    }

    override fun onComplete() {

    }
}
