package com.leory.commonBusiness.http

/**
 * Describe : 基本的bean
 * Author : leory
 * Date : 2019-07-12
 */
class BaseBusinessBean<T> {
    var message: String? = null
    var code: Int = 0
    var data: T? = null

}
