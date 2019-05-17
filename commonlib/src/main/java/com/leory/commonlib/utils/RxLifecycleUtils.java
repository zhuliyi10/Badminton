package com.leory.commonlib.utils;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;
import com.leory.commonlib.base.lifecycle.ActivityLifecycleable;
import com.leory.commonlib.base.lifecycle.FragmentLifecycleable;
import com.leory.commonlib.base.lifecycle.Lifecycleable;
import com.leory.commonlib.mvp.IView;

import io.reactivex.annotations.NonNull;

/**
 * Describe : 使用此类操作 RxLifecycle 的特性
 * Author : zhuly
 * Date : 2019-05-14
 */
public class RxLifecycleUtils {



    /**
     * 绑定 Activity/Fragment 的生命周期
     *
     * @param view
     * @param <T>
     * @return
     */
    public static <T> LifecycleTransformer<T> bindToLifecycle(@NonNull IView view) {

        if (view instanceof Lifecycleable) {
            return bindToLifecycle((Lifecycleable) view);
        } else {
            throw new IllegalArgumentException("view isn't Lifecycleable");
        }
    }

    public static <T> LifecycleTransformer<T> bindToLifecycle(@NonNull Lifecycleable lifecycleable) {
        if (lifecycleable instanceof ActivityLifecycleable) {
            return RxLifecycleAndroid.bindActivity(((ActivityLifecycleable) lifecycleable).provideLifecycleSubject());
        } else if (lifecycleable instanceof FragmentLifecycleable) {
            return RxLifecycleAndroid.bindFragment(((FragmentLifecycleable) lifecycleable).provideLifecycleSubject());
        } else {
            throw new IllegalArgumentException("Lifecycleable not match");
        }
    }
}
