package com.leory.badminton.news.mvp.model.bean

/**
 * Describe : 交手bean
 * Author : leroy
 * Date : 2019-07-24
 */
class HandOffBean {
    var player1HeadUrl: String? = null
    var player1Flag: String? = null
    var player1Name: String? = null
    var player12HeadUrl: String? = null
    var player12Flag: String? = null
    var player12Name: String? = null
    var player1Ranking: String? = null
    var player1Win: String? = null

    var player2HeadUrl: String? = null
    var player2Flag: String? = null
    var player2Name: String? = null
    var player22HeadUrl: String? = null
    var player22Flag: String? = null
    var player22Name: String? = null
    var player2Ranking: String? = null
    var player2Win: String? = null

    var recordList: List<Record>? = null

    class Record {
        var date: String? = null
        var matchName: String? = null
        var score: String? = null
        var isLeftWin: Boolean = false
    }
}
