package com.leory.badminton.news.mvp.ui.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.leory.badminton.news.R
import com.leory.badminton.news.mvp.model.bean.HandOffBean
import com.leory.commonlib.base.BaseAdapter
import kotlinx.android.synthetic.main.item_hand_off_record.view.*

/**
 * Describe : 交手记录adapter
 * Author : leory
 * Date : 2019-07-24
 */
class HandOffRecordAdapter(data: List<HandOffBean.Record>?) : BaseAdapter<HandOffBean.Record>(R.layout.item_hand_off_record, data) {

    override fun convert(helper: BaseViewHolder, item: HandOffBean.Record) {
        helper.itemView.apply {
            txt_time.text = item.date
            txt_match.text = item.matchName
            txt_score.text = item.score

            if (item.isLeftWin) {
                win1.text = "胜"
                win2.text = "负"
            } else {
                win1.text = "负"
                win2.text = "胜"
            }
        }
    }
}
