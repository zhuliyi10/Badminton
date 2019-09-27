package com.leory.badminton.news.mvp.model.bean

/**
 * Describe : 比赛日期的bean
 * Author : leory
 * Date : 2019-06-06
 */
class MatchDateBean {
    var matchId: String? = null
    var type: String? = null//类型
    var time: String? = null//时间

    var field: String? = null//场地
    var player1: String? = null
    var player1Url: String? = null
    var player12: String? = null
    var player12Url: String? = null
    var flag1: String? = null
    var flag12: String? = null
    var player2: String? = null
    var player2Url: String? = null
    var player22: String? = null
    var player22Url: String? = null
    var flag2: String? = null
    var flag22: String? = null
    var vs: String? = null//"-"
    var duration: String? = null//时长
    var score: String? = null//比分
}
