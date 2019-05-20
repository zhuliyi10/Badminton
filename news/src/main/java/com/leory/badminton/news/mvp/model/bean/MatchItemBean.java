package com.leory.badminton.news.mvp.model.bean;

/**
 * Describe : 赛事列表item
 * Author : zhuly
 * Date : 2019-05-20
 */
public class MatchItemBean {
    private String countryFlagUrl;//国旗url
    private String countryName;//国家名字简称
    private String cityName;//城市名称
    private String matchDay;//赛事日期
    private String matchName;//赛事名称
    private String matchUrl;//赛事url
    private String matchClassify;//赛事分类
    private String matchBonus;//赛事奖金

    private int bgColor;//背景颜色


    public String getCountryFlagUrl() {
        return countryFlagUrl;
    }

    public void setCountryFlagUrl(String countryFlagUrl) {
        this.countryFlagUrl = countryFlagUrl;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getMatchDay() {
        return matchDay;
    }

    public void setMatchDay(String matchDay) {
        this.matchDay = matchDay;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getMatchClassify() {
        return matchClassify;
    }

    public void setMatchClassify(String matchClassify) {
        this.matchClassify = matchClassify;
    }

    public String getMatchBonus() {
        return matchBonus;
    }

    public void setMatchBonus(String matchBonus) {
        this.matchBonus = matchBonus;
    }

    public String getMatchUrl() {
        return matchUrl;
    }

    public void setMatchUrl(String matchUrl) {
        this.matchUrl = matchUrl;
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }
}
