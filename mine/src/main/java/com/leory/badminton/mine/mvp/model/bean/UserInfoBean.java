package com.leory.badminton.mine.mvp.model.bean;

import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Describe :用户bean
 * Author : leory
 * Date : 2019-06-20
 */
public class UserInfoBean {
    private String uid;//用户唯一标识
    private String wechatUid;//微信Uid
    private String qqUid;//qqUid
    private String name;//用户昵称
    private String gender;//用户性别，该字段会直接返回男女
    private String iconUrl;//用户头像
    private SHARE_MEDIA shareMedia;
    private String desc;//描述
    private String phone;//手机号
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

    public String getWechatUid() {
        return wechatUid;
    }

    public void setWechatUid(String wechatUid) {
        this.wechatUid = wechatUid;
    }

    public String getQqUid() {
        return qqUid;
    }

    public void setQqUid(String qqUid) {
        this.qqUid = qqUid;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public SHARE_MEDIA getShareMedia() {
        return shareMedia;
    }

    public void setShareMedia(SHARE_MEDIA shareMedia) {
        this.shareMedia = shareMedia;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
