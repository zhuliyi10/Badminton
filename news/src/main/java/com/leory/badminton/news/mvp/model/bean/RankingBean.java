package com.leory.badminton.news.mvp.model.bean;

/**
 * Describe : 排名的bean
 * Author : zhuly
 * Date : 2019-05-21
 */
public class RankingBean {
    private String playerId;//运动员id
    private String rankingNum;//排名
    private String countryName;//国家名字简称
    private String country2Name;//运动员2国家名字简称
    private String countryFlagUrl;//运动员1国旗url
    private String countryFlag2Url;//运动员2国旗url
    private String playerName;//运动员名字
    private String player2Name;//运动员2名字
    private String playerUrl;//运动员1的url
    private String player2Url;//运动员2的url
    private String points;//积分
    private String bonus;//奖金
    private String winAndLoss;//胜负
    private int riseOrDrop;//升降

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getRankingNum() {
        return rankingNum;
    }

    public void setRankingNum(String rankingNum) {
        this.rankingNum = rankingNum;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryFlagUrl() {
        return countryFlagUrl;
    }

    public void setCountryFlagUrl(String countryFlagUrl) {
        this.countryFlagUrl = countryFlagUrl;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerUrl() {
        return playerUrl;
    }

    public void setPlayerUrl(String playerUrl) {
        this.playerUrl = playerUrl;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public String getWinAndLoss() {
        return winAndLoss;
    }

    public void setWinAndLoss(String winAndLoss) {
        this.winAndLoss = winAndLoss;
    }

    public int getRiseOrDrop() {
        return riseOrDrop;
    }

    public void setRiseOrDrop(int riseOrDrop) {
        this.riseOrDrop = riseOrDrop;
    }

    public String getCountry2Name() {
        return country2Name;
    }

    public void setCountry2Name(String country2Name) {
        this.country2Name = country2Name;
    }

    public String getCountryFlag2Url() {
        return countryFlag2Url;
    }

    public void setCountryFlag2Url(String countryFlag2Url) {
        this.countryFlag2Url = countryFlag2Url;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    public String getPlayer2Url() {
        return player2Url;
    }

    public void setPlayer2Url(String player2Url) {
        this.player2Url = player2Url;
    }
}
