package com.leory.badminton.news.mvp.model.bean

/**
 * Describe : 比赛信息
 * Author : leory
 * Date : 2019-05-27
 */
class MatchInfoBean {
    var matchBackground: String? = null//背景
    var matchName: String? = null//比赛名称
    var matchDate: String? = null//比赛日期
    var matchSite: String? = null//比赛地点
    var matchBonus: String? = null//比赛奖金
    var matchIcon: String? = null//比赛图标
    var tabDateHeads: List<MatchTabDateBean>? = null//赛程日期头部
    var historyUrl: String? = null//历史url
    var country: String? = null//国家
}
