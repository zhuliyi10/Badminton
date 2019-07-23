package com.leory.commonlib.http;

import com.leory.commonlib.utils.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Describe : Rx处理retrofit回调
 * Author : zhuly
 * Date : 2019-05-14
 */
public abstract class RxHandlerSubscriber<T> implements Observer<T> {
    private ResponseErrorListener errorListener;
    private CompositeDisposable compositeDisposable;

    public RxHandlerSubscriber() {
        errorListener = new ResponseErrorListenerImpl();
    }

    public RxHandlerSubscriber(CompositeDisposable compositeDisposable) {
        errorListener = new ResponseErrorListenerImpl();
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (compositeDisposable != null) {
            compositeDisposable.add(d);
        }
    }


    @Override
    public void onError(Throwable e) {
        String errorMsg = errorListener.onError(e);
        ToastUtils.showLong(errorMsg);
    }

    @Override
    public void onComplete() {

    }
}
