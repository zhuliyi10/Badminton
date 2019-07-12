package com.leory.commonBusiness.http;

/**
 * Describe : 基本的bean
 * Author : leory
 * Date : 2019-07-12
 */
public class BaseBusinessBean<T> {
    private String msg;
    private int code;
    private T data;

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
