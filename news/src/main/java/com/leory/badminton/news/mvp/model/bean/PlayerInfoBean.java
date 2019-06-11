package com.leory.badminton.news.mvp.model.bean;

import java.io.Serializable;

/**
 * Describe : 运动员个人资料bean
 * Author : leory
 * Date : 2019-06-11
 */
public class PlayerInfoBean implements Serializable {
    private String stats;

    public String getStats() {
        return stats;
    }

    public void setStats(String stats) {
        this.stats = stats;
    }
}
