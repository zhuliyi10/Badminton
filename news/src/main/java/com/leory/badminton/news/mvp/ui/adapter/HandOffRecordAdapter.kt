package com.leory.badminton.news.mvp.ui.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.leory.badminton.news.R
import com.leory.badminton.news.mvp.model.bean.HandOffBean
import com.leory.commonlib.base.BaseAdapter

/**
 * Describe : 交手记录adapter
 * Author : leory
 * Date : 2019-07-24
 */
class HandOffRecordAdapter(data: List<HandOffBean.Record>?) : BaseAdapter<HandOffBean.Record>(R.layout.item_hand_off_record, data) {

    override fun convert(helper: BaseViewHolder, item: HandOffBean.Record) {
        helper.setText(R.id.txt_time, item.date)
        helper.setText(R.id.txt_match, item.matchName)
        helper.setText(R.id.txt_score, item.score)
        if (item.isLeftWin) {
            helper.setText(R.id.win1, "胜")
            helper.setText(R.id.win2, "负")
        } else {
            helper.setText(R.id.win1, "负")
            helper.setText(R.id.win2, "胜")

        }
    }
}
