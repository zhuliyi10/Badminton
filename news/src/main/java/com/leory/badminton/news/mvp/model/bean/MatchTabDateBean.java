package com.leory.badminton.news.mvp.model.bean;

import java.io.Serializable;

/**
 * Describe : 赛程日期头部
 * Author : leory
 * Date : 2019-06-06
 */
public class MatchTabDateBean implements Serializable {
    private String link;//链接
    private String name;//名字

    public MatchTabDateBean(String link, String name) {
        this.link = link;
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
