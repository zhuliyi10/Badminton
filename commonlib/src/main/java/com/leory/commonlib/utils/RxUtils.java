package com.leory.commonlib.utils;

import com.leory.commonlib.mvp.IView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Describe : Rx工具
 * Author : zhuly
 * Date : 2019-05-14
 */
public class RxUtils {
    public static <T> ObservableTransformer<T, T> applySchedulers(final IView view) {
        return applySchedulers(view,0);
    }
    public static <T> ObservableTransformer<T, T> applySchedulersDelayFinal(final IView view) {
        return applySchedulers(view,500);
    }
    /**
     *
     * @param view
     * @param delay 单位 TimeUnit.MILLISECONDS
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> applySchedulers(final IView view,long delay) {
        return new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull Disposable disposable) throws Exception {
                                view.showLoading();//显示进度条
                            }
                        })
                        .delay(delay, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doFinally(new Action() {
                            @Override
                            public void run() {
                                view.hideLoading();//隐藏进度条
                            }
                        }).compose(RxLifecycleUtils.bindToLifecycle(view));
            }
        };
    }
}
