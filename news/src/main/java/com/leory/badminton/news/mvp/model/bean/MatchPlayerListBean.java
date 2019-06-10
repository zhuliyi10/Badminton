package com.leory.badminton.news.mvp.model.bean;

import java.util.List;

/**
 * Describe : 一组运动员
 * Author : leory
 * Date : 2019-06-10
 */
public class MatchPlayerListBean {
    private List<MatchPlayerBean>data;

    public List<MatchPlayerBean> getData() {
        return data;
    }

    public void setData(List<MatchPlayerBean> data) {
        this.data = data;
    }
}
