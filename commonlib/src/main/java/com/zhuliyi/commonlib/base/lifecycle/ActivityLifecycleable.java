package com.zhuliyi.commonlib.base.lifecycle;

import android.app.Activity;

import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;

/**
 * Describe : 让 {@link Activity} 实现此接口,即可正常使用 {@link RxLifecycle}
 * Author : zhuly
 * Date : 2019-05-14
 */
public interface ActivityLifecycleable extends Lifecycleable<ActivityEvent> {
}
