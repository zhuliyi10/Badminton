package com.leory.badminton.news.mvp.model.bean;

/**
 * Describe : 赛事历史头部
 * Author : leory
 * Date : 2019-06-07
 */
public class MatchHistoryHeadBean {
    private String year;
    private String matchName;
    private String detailUrl;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }
}
