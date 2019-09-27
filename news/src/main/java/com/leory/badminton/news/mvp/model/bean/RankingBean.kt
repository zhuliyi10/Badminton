package com.leory.badminton.news.mvp.model.bean

/**
 * Describe : 排名的bean
 * Author : zhuly
 * Date : 2019-05-21
 */
class RankingBean {
    var playerId: String? = null//运动员id
    var rankingNum: String? = null//排名
    var countryName: String? = null//国家名字简称
    var country2Name: String? = null//运动员2国家名字简称
    var countryFlagUrl: String? = null//运动员1国旗url
    var countryFlag2Url: String? = null//运动员2国旗url
    var playerName: String? = null//运动员名字
    var player2Name: String? = null//运动员2名字
    var playerUrl: String? = null//运动员1的url
    var player2Url: String? = null//运动员2的url
    var points: String? = null//积分
    var bonus: String? = null//奖金
    var winAndLoss: String? = null//胜负
    var riseOrDrop: Int = 0//升降
}
