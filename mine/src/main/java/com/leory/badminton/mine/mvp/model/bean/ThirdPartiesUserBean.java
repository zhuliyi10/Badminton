package com.leory.badminton.mine.mvp.model.bean;

/**
 * Describe : 第三方登陆用户bean
 * Author : leory
 * Date : 2019-06-20
 */
public class ThirdPartiesUserBean {
    private String uid;//用户唯一标识 uid能否实现Android与iOS平台打通，目前QQ只能实现同APPID下用户ID匹配
    private String name;//用户昵称
    private String gender;//用户性别，该字段会直接返回男女
    private String iconUrl;//用户头像

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
