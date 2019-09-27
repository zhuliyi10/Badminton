package com.leory.badminton.news.mvp.ui.adapter

import android.view.View

import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.leory.badminton.news.R
import com.leory.badminton.news.mvp.model.bean.MatchItemBean
import com.leory.badminton.news.mvp.model.bean.MatchItemSection
import com.leory.badminton.news.mvp.ui.activity.MatchDetailActivity

/**
 * Describe : 赛事列表adapter
 * Author : leory
 * Date : 2019-05-20
 */
class MatchSectionAdapter(data: List<MatchItemSection>) : BaseSectionQuickAdapter<MatchItemSection, BaseViewHolder>(R.layout.item_match, R.layout.head_match_month, data) {

    override fun convertHead(helper: BaseViewHolder, item: MatchItemSection) {
        helper.setText(R.id.txt_head, item.header)
    }

    override fun convert(helper: BaseViewHolder, item: MatchItemSection) {
        val bean = item.t
        helper.setText(R.id.match_name, bean.matchName)
        helper.setText(R.id.match_day, bean.matchDay)
        helper.setText(R.id.match_classify, bean.matchClassify)
        helper.itemView.setBackgroundColor(bean.bgColor)
    }
}
