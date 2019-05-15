package com.zhuliyi.video.mvp.model.bean;

import java.io.Serializable;

/**
 * Describe : baseResponse
 * Author : zhuly
 * Date : 2019-05-15
 */
public class VideoBaseResponse<T> implements Serializable {
    private String status;
    private T data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess(){
        return "200".equals(status);
    }
}
