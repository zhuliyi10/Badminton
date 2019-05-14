package com.zhuliyi.commonlib.http;

/**
 * Describe : {@link RxHandlerSubscriber }错误回调
 * Author : zhuly
 * Date : 2019-05-14
 */
public interface ResponseErrorListener {
    void onError(Throwable t);
}
