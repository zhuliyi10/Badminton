package com.leory.badminton.video.mvp.model.bean;

import java.util.List;

/**
 * Describe : 视频列表
 * Author : zhuly
 * Date : 2019-05-15
 */
public class VideoListBean {
    private int totalcount;
    private List<VideoBean>list;

    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }

    public List<VideoBean> getList() {
        return list;
    }

    public void setList(List<VideoBean> list) {
        this.list = list;
    }
}
