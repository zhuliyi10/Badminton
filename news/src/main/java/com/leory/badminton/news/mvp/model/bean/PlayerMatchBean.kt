package com.leory.badminton.news.mvp.model.bean

/**
 * Describe : 运动员赛果item
 * Author : leory
 * Date : 2019-06-11
 */
class PlayerMatchBean {
    var matchInfo: MatchInfo? = null
    var rounds: List<ResultRound>? = null

    class MatchInfo {
        var matchUrl: String? = null
        var name: String? = null
        var category: String? = null
        var date: String? = null
        var bonus: String? = null
    }

    class ResultRound {
        var round: String? = null//第几轮
        var player1: String? = null
        var player12: String? = null
        var flag1: String? = null
        var flag12: String? = null
        var player2: String? = null
        var player22: String? = null
        var flag2: String? = null
        var flag22: String? = null
        var vs: String? = null//"-"
        var duration: String? = null//时长
        var score: String? = null//比分
    }
}
