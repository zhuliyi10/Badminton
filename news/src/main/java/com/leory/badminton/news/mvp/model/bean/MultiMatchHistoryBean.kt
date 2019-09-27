package com.leory.badminton.news.mvp.model.bean

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * Describe : multi 历史赛事bean
 * Author : leory
 * Date : 2019-06-07
 */
class MultiMatchHistoryBean<T>(private val itemType: Int, var t: T?) : MultiItemEntity {

    override fun getItemType(): Int {
        return itemType
    }

    companion object {
        val TYPE_HEAD = 1
        val TYPE_CONTENT = 2
    }
}
