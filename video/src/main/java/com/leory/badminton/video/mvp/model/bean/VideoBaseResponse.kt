package com.leory.badminton.video.mvp.model.bean

import java.io.Serializable

/**
 * Describe : baseResponse
 * Author : zhuly
 * Date : 2019-05-15
 */
data class VideoBaseResponse<T>(var status: String? = null,
                                var data: T? = null) : Serializable {


    val isSuccess: Boolean
        get() = "200" == status
}
