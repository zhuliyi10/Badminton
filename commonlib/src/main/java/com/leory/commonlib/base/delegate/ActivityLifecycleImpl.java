package com.leory.commonlib.base.delegate;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.leory.commonlib.app.AppManager;
import com.leory.commonlib.base.lifecycle.ActivityLifecycleable;
import com.leory.commonlib.utils.AppUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;

import org.simple.eventbus.EventBus;

import javax.inject.Inject;

import io.reactivex.subjects.Subject;

/**
 * Describe : 通用Activity生命周期的回调，包括第三方Activity，在这里实现通用Activity生命周期的方法
 * Author : zhuly
 * Date : 2018-06-12
 */

public class ActivityLifecycleImpl implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = ActivityLifecycleImpl.class.getSimpleName();

    @Inject
    AppManager appManager;

    private IActivity iActivity;

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        if (activity instanceof ActivityLifecycleable) {
            obtainSubject(activity).onNext(ActivityEvent.CREATE);
        }
        Log.d(TAG, activity.getClass().getSimpleName() + ":onActivityCreated");
        AppUtils.obtainAppComponent().inject(this);
        appManager.addActivity(activity);
        if (activity instanceof IActivity) {
            iActivity = (IActivity) activity;
            if (iActivity.useEventBus()) {
                //注册到事件主线
                EventBus.getDefault().register(activity);
            }
            if (iActivity.useFragment() && iActivity instanceof FragmentActivity) {
                ((FragmentActivity) iActivity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(new FragmentLifecycleImpl(), true);
            }
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (activity instanceof ActivityLifecycleable) {
            obtainSubject(activity).onNext(ActivityEvent.START);
        }
        Log.d(TAG, activity.getClass().getSimpleName() + ":onActivityStarted");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (activity instanceof ActivityLifecycleable) {
            obtainSubject(activity).onNext(ActivityEvent.RESUME);
        }
        Log.d(TAG, activity.getClass().getSimpleName() + ":onActivityResumed");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        if (activity instanceof ActivityLifecycleable) {
            obtainSubject(activity).onNext(ActivityEvent.PAUSE);
        }
        Log.d(TAG, activity.getClass().getSimpleName() + ":onActivityPaused");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (activity instanceof ActivityLifecycleable) {
            obtainSubject(activity).onNext(ActivityEvent.STOP);
        }
        Log.d(TAG, activity.getClass().getSimpleName() + ":onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        Log.d(TAG, activity.getClass().getSimpleName() + ":onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (activity instanceof ActivityLifecycleable) {
            obtainSubject(activity).onNext(ActivityEvent.DESTROY);
        }
        Log.d(TAG, activity.getClass().getSimpleName() + ":onActivityDestroyed");
        appManager.removeActivity(activity);
        if (iActivity != null && iActivity.useEventBus()) {
            //取消注册
            EventBus.getDefault().unregister(activity);
        }
        iActivity = null;
    }

    private Subject<ActivityEvent> obtainSubject(Activity activity) {
        return ((ActivityLifecycleable) activity).provideLifecycleSubject();
    }
}
