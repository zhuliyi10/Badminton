package com.leory.badminton.news.mvp.model.bean;

/**
 * Describe : 运动员详情bean
 * Author : leory
 * Date : 2019-06-11
 */
public class PlayerDetailBean {
    private String head;
    private String flag;
    private String name;
    private String rankNum;
    private String rankNum2;
    private String type;
    private String type2;
    private String winNum;
    private String age;

    private PlayerInfoBean infoBean;

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRankNum() {
        return rankNum;
    }

    public void setRankNum(String rankNum) {
        this.rankNum = rankNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWinNum() {
        return winNum;
    }

    public void setWinNum(String winNum) {
        this.winNum = winNum;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getRankNum2() {
        return rankNum2;
    }

    public void setRankNum2(String rankNum2) {
        this.rankNum2 = rankNum2;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public PlayerInfoBean getInfoBean() {
        return infoBean;
    }

    public void setInfoBean(PlayerInfoBean infoBean) {
        this.infoBean = infoBean;
    }
}
