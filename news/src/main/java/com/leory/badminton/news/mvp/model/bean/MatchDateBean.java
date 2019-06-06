package com.leory.badminton.news.mvp.model.bean;

/**
 * Describe : 比赛日期的bean
 * Author : leory
 * Date : 2019-06-06
 */
public class MatchDateBean {
    private String type;//类型
    private String time;//时间

    private String field;//场地
    private String player1;
    private String player12;
    private String flag1;
    private String flag12;
    private String player2;
    private String player22;
    private String flag2;
    private String flag22;
    private String vs;//"-"
    private String duration;//时长
    private String score;//比分

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer12() {
        return player12;
    }

    public void setPlayer12(String player12) {
        this.player12 = player12;
    }

    public String getFlag1() {
        return flag1;
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }

    public String getFlag12() {
        return flag12;
    }

    public void setFlag12(String flag12) {
        this.flag12 = flag12;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public String getPlayer22() {
        return player22;
    }

    public void setPlayer22(String player22) {
        this.player22 = player22;
    }

    public String getFlag2() {
        return flag2;
    }

    public void setFlag2(String flag2) {
        this.flag2 = flag2;
    }

    public String getFlag22() {
        return flag22;
    }

    public void setFlag22(String flag22) {
        this.flag22 = flag22;
    }

    public String getVs() {
        return vs;
    }

    public void setVs(String vs) {
        this.vs = vs;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
