package com.leory.commonBusiness.http;

import com.leory.commonlib.http.ResponseErrorListener;
import com.leory.commonlib.http.ResponseErrorListenerImpl;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Describe : Rx处理retrofit回调
 * Author : leory
 * Date : 2019-07-12
 */
public abstract class RxBusinessHandlerSubscriber<T> implements Observer<BaseBusinessBean<T>> {
    private ResponseErrorListener errorListener;

    public RxBusinessHandlerSubscriber() {
        errorListener = new ResponseErrorListenerImpl();
    }

    public abstract void onSuccess(T data);

    public abstract void onFail(String errorMsg);

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(BaseBusinessBean<T> tBaseBusinessBean) {
        if (tBaseBusinessBean.getCode() == 0) {
            onSuccess(tBaseBusinessBean.getData());
        } else {
            onFail(tBaseBusinessBean.getMessage());
        }
    }

    @Override
    public void onError(Throwable e) {
        String errorMsg = errorListener.onError(e);
        onFail(errorMsg);
    }

    @Override
    public void onComplete() {

    }
}
