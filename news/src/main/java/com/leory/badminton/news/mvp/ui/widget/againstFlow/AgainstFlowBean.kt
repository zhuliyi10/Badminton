package com.leory.badminton.news.mvp.ui.widget.againstFlow

import android.graphics.Bitmap

/**
 * Describe : 对阵数据
 * Author : leory
 * Date : 2019-05-24
 */
class AgainstFlowBean {
    var isDouble: Boolean = false//是否双打
    var name1: String? = null//名字1
    var icon1: String? = null//图片1
    var name2: String? = null//名字2
    var icon2: String? = null//图片2
    var score: String? = null//分数比
    var bitmap1: Bitmap? = null
    var bitmap2: Bitmap? = null
}
