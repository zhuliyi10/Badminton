package com.leory.commonlib.http;

/**
 * Describe : {@link RxHandlerSubscriber }错误回调
 * Author : zhuly
 * Date : 2019-05-14
 */
public interface ResponseErrorListener {
    String onError(Throwable t);
}
