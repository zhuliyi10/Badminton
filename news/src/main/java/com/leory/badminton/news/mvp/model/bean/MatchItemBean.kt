package com.leory.badminton.news.mvp.model.bean

/**
 * Describe : 赛事列表item
 * Author : zhuly
 * Date : 2019-05-20
 */
class MatchItemBean {
    var countryFlagUrl: String? = null//国旗url
    var countryName: String? = null//国家名字简称
    var cityName: String? = null//城市名称
    var matchDay: String? = null//赛事日期
    var matchName: String? = null//赛事名称
    var matchUrl: String? = null//赛事url
    var matchClassify: String? = null//赛事分类
    var matchBonus: String? = null//赛事奖金

    var bgColor: Int = 0//背景颜色
}
