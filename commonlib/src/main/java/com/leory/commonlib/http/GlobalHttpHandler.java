package com.leory.commonlib.http;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Describe : 统一处理 Http 请求和响应结果的处理类
 * Author : zhuly
 * Date : 2018-06-12
 */

public interface GlobalHttpHandler {
    /**
     * 这里可以先客户端一步拿到每一次 Http 请求的结果, 可以先解析成 Json, 再做一些操作, 如检测到 token 过期后
     * 重新请求 token, 并重新执行请求
     *
     * @param httpResult 服务器返回的结果 (已被框架自动转换为字符串)
     * @param chain      {@link okhttp3.Interceptor.Chain}
     * @param response   {@link Response}
     * @return {@link Response}
     */
    Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response);

    /**
     * 这里可以在请求服务器之前拿到 {@link Request}, 做一些操作比如给 {@link Request} 统一添加 token 或者 header 以及参数加密等操作
     *
     * @param chain   {@link okhttp3.Interceptor.Chain}
     * @param request {@link Request}
     * @return {@link Request}
     */
    Request onHttpRequestBefore(Interceptor.Chain chain, Request request);
}
