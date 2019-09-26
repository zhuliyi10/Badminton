package com.leory.badminton.mine.mvp.model.bean

/**
 * Describe : 第三方登陆用户bean
 * Author : leory
 * Date : 2019-06-20
 */
data class ThirdPartiesUserBean(
        val uid: String? = null,//用户唯一标识 uid能否实现Android与iOS平台打通，目前QQ只能实现同APPID下用户ID匹配
        val name: String? = null,//用户昵称
        val gender: String? = null,//用户性别，该字段会直接返回男女
        val iconUrl: String? = null//用户头像
)
