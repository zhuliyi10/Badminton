package com.leory.commonlib.widget.morePop

/**
 * Describe : 弹出框bean
 * Author : zhuly
 * Date : 2018-10-23
 */

class MorePopBean @JvmOverloads constructor(
        /**
         * 资源id
         */
        var drawRes: Int,
        /**
         * 标签名称
         */
        var name: String?,
        /**
         * 能否点击，默认true
         */
        var isCanClick: Boolean = true) {

    constructor(name: String) : this(0, name) {}
}
