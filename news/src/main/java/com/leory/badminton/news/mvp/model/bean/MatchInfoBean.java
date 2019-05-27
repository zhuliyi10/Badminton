package com.leory.badminton.news.mvp.model.bean;

/**
 * Describe : 比赛信息
 * Author : leory
 * Date : 2019-05-27
 */
public class MatchInfoBean {
    private String matchBackground;//背景
    private String matchName;//比赛名称
    private String matchDate;//比赛日期
    private String matchSite;//比赛地点
    private String matchBonus;//比赛奖金
    private String matchIcon;//比赛图标

    public String getMatchBackground() {
        return matchBackground;
    }

    public void setMatchBackground(String matchBackground) {
        this.matchBackground = matchBackground;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public String getMatchSite() {
        return matchSite;
    }

    public void setMatchSite(String matchSite) {
        this.matchSite = matchSite;
    }

    public String getMatchBonus() {
        return matchBonus;
    }

    public void setMatchBonus(String matchBonus) {
        this.matchBonus = matchBonus;
    }

    public String getMatchIcon() {
        return matchIcon;
    }

    public void setMatchIcon(String matchIcon) {
        this.matchIcon = matchIcon;
    }
}
