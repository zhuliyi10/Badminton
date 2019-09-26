package com.leory.badminton.mine.mvp.model.bean

import com.umeng.socialize.bean.SHARE_MEDIA

/**
 * Describe :用户bean
 * Author : leory
 * Date : 2019-06-20
 */
data class UserInfoBean(var uid: String? = null,//用户唯一标识
                        var wechatUid: String? = null,//微信Uid
                        var qqUid: String? = null,//qqUid
                        var name: String? = null,//用户昵称
                        var gender: String? = null,//用户性别，该字段会直接返回男女
                        var iconUrl: String? = null,//用户头像
                        var shareMedia: SHARE_MEDIA? = null,
                        var desc: String? = null,//描述
                        var phone: String? = null//手机号
)


