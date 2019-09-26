package com.leory.badminton.video.mvp.model.bean

/**
 * Describe : 视频列表
 * Author : zhuly
 * Date : 2019-05-15
 */
data class VideoListBean(var totalcount: Int = 0, var list: MutableList<VideoBean>? = null)
