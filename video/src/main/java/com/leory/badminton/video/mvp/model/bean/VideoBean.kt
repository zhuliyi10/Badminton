package com.leory.badminton.video.mvp.model.bean

/**
 * Describe : 视频item
 * Author : zhuly
 * Date : 2019-05-15
 */
data class VideoBean(var id: String? = null,
                     var title: String? = null,//标题
                     var videourl: String? = null,//视频url
                     var imgurl: String? = null,//封面图片
                     var playcount: String? = null,//播放次数
                     var totalTimes: String? = null,//时长
                     var width: String? = null,//视频宽度
                     var height: String? = null//视频高度
)