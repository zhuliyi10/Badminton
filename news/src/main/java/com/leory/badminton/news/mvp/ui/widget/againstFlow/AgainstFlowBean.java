package com.leory.badminton.news.mvp.ui.widget.againstFlow;

/**
 * Describe : 对阵数据
 * Author : leory
 * Date : 2019-05-24
 */
public class AgainstFlowBean {
    private boolean isDouble;//是否双打
    private String name1;//名字1
    private String icon1;//图片1
    private String name2;//名字2
    private String icon2;//图片2
    private String score;//分数比

    public boolean isDouble() {
        return isDouble;
    }

    public void setDouble(boolean aDouble) {
        isDouble = aDouble;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getIcon1() {
        return icon1;
    }

    public void setIcon1(String icon1) {
        this.icon1 = icon1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getIcon2() {
        return icon2;
    }

    public void setIcon2(String icon2) {
        this.icon2 = icon2;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
