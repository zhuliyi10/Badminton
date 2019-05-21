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
    private String countryFlagUrl;//国旗url
    private String playerName;//运动员名字
    private String playerUrl;//运动员的url
    private String points;//积分
    private String bonus;//奖金
    private String winAndLoss;//胜负
    private String riseOrDrop;//升降

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

    public String getRiseOrDrop() {
        return riseOrDrop;
    }

    public void setRiseOrDrop(String riseOrDrop) {
        this.riseOrDrop = riseOrDrop;
    }
}
