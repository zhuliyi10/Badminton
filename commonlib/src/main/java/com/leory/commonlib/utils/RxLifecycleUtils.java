package com.leory.commonlib.utils;

import com.leory.commonlib.base.lifecycle.ActivityLifecycleable;
import com.leory.commonlib.base.lifecycle.FragmentLifecycleable;
import com.leory.commonlib.base.lifecycle.Lifecycleable;
import com.leory.commonlib.mvp.IView;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

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

    private static <T> LifecycleTransformer<T> bindToLifecycle(@NonNull Lifecycleable lifecycleable) {
        if (lifecycleable instanceof ActivityLifecycleable) {
            return RxLifecycleAndroid.bindActivity(((ActivityLifecycleable) lifecycleable).provideLifecycleSubject());
        } else if (lifecycleable instanceof FragmentLifecycleable) {
            return RxLifecycleAndroid.bindFragment(((FragmentLifecycleable) lifecycleable).provideLifecycleSubject());
        } else {
            throw new IllegalArgumentException("Lifecycleable not match");
        }
    }

    /**
     * 在event事件时解绑
     *
     * @param view
     * @param event
     * @param <T>
     * @return
     */
    public static <T> LifecycleTransformer<T> bindUntilEvent(@NonNull IView view, @NonNull ActivityEvent event) {

        if (view instanceof Lifecycleable) {
            return bindUntilEvent((Lifecycleable) view, event);
        } else {
            throw new IllegalArgumentException("view isn't Lifecycleable");
        }
    }

    private static <T> LifecycleTransformer<T> bindUntilEvent(@NonNull Lifecycleable lifecycleable, @NonNull ActivityEvent event) {
        if (lifecycleable instanceof ActivityLifecycleable) {
            return RxLifecycle.bindUntilEvent(((ActivityLifecycleable) lifecycleable).provideLifecycleSubject(), event);
        } else {
            throw new IllegalArgumentException("Lifecycleable not match");
        }
    }

    /**
     * 在event事件时解绑
     *
     * @param view
     * @param event
     * @param <T>
     * @return
     */
    public static <T> LifecycleTransformer<T> bindUntilEvent(@NonNull IView view, @NonNull FragmentEvent event) {

        if (view instanceof Lifecycleable) {
            return bindUntilEvent((Lifecycleable) view, event);
        } else {
            throw new IllegalArgumentException("view isn't Lifecycleable");
        }
    }

    private static <T> LifecycleTransformer<T> bindUntilEvent(@NonNull Lifecycleable lifecycleable, @NonNull FragmentEvent event) {
        if (lifecycleable instanceof FragmentLifecycleable) {
            return RxLifecycle.bindUntilEvent(((FragmentLifecycleable) lifecycleable).provideLifecycleSubject(), event);
        } else {
            throw new IllegalArgumentException("Lifecycleable not match");
        }
    }

}
