package com.leory.badminton.news.mvp.model.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Describe : multi 参赛运动员bean
 * Author : leory
 * Date : 2019-06-10
 */
public class MultiMatchPlayersBean<T> implements MultiItemEntity {
    public static final int TYPE_HEAD = 1;
    public static final int TYPE_CONTENT = 2;
    private int itemType;
    private T t;

    public MultiMatchPlayersBean(int itemType, T t) {
        this.itemType = itemType;
        this.t = t;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
