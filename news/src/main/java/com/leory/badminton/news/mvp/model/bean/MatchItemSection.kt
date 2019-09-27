package com.leory.badminton.news.mvp.model.bean

import com.chad.library.adapter.base.entity.SectionEntity

/**
 * Describe : 带有头部的bean
 * Author : leory
 * Date : 2019-05-20
 */
class MatchItemSection : SectionEntity<MatchItemBean> {
    constructor(isHeader: Boolean, header: String) : super(isHeader, header) {}

    constructor(matchItemBean: MatchItemBean) : super(matchItemBean) {}
}
