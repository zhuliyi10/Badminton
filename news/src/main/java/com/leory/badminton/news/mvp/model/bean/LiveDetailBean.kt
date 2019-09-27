package com.leory.badminton.news.mvp.model.bean

/**
 * Describe : 直播详情
 * Author : leory
 * Date : 2019-06-04
 */
class LiveDetailBean {
    var type: String? = null//类型
    var time: String? = null//时间

    var field: String? = null//场地

    var player1: String? = null
    var player12: String? = null
    var flag1: String? = null
    var flag12: String? = null
    var player2: String? = null
    var player22: String? = null
    var flag2: String? = null
    var flag22: String? = null
    var vs: String? = null//"-"
    var score: String? = null
    var leftDot: String? = null
    var rightDot: String? = null

    var detailUrl: String? = null
}
