package com.leory.badminton.news.mvp.ui.adapter

import android.view.View

import com.chad.library.adapter.base.BaseViewHolder
import com.leory.badminton.news.R
import com.leory.badminton.news.mvp.model.bean.MatchDateBean
import com.leory.commonlib.base.BaseAdapter
import com.leory.commonlib.utils.ImageUtils
import kotlinx.android.synthetic.main.item_match_date.view.*

/**
 * Describe : 赛事赛程adapter
 * Author : leory
 * Date : 2019-06-06
 */
class MatchDateAdapter(data: List<MatchDateBean>?) : BaseAdapter<MatchDateBean>(R.layout.item_match_date, data) {

    override fun convert(helper: BaseViewHolder, item: MatchDateBean) {
        val position = helper.adapterPosition

        helper.setText(R.id.txt_type, item.type)
        helper.setText(R.id.txt_time, item.time)
        helper.setText(R.id.txt_court, item.field)
        helper.setText(R.id.txt_duration, item.duration)
        helper.setText(R.id.txt_player1, item.player1)
        helper.setText(R.id.txt_player2, item.player2)
        ImageUtils.loadImage(mContext, helper.getView(R.id.img_flag1), item.flag1)
        ImageUtils.loadImage(mContext, helper.getView(R.id.img_flag2), item.flag2)
        helper.setText(R.id.vs, item.vs)
        helper.setText(R.id.txt_score, item.score)

        helper.itemView.apply {

            if(item.score.isNullOrEmpty()){
                txt_score.visibility=View.GONE
            }else{
                txt_score.visibility=View.VISIBLE
            }
            if (position > 1 && item.field == data[position - 2].field) {
                txt_court.visibility = View.GONE
            } else {
                txt_court.visibility = View.VISIBLE
            }
        }

        if ("男单" == item.type || "女单" == item.type) {
            helper.getView<View>(R.id.player12).visibility = View.GONE
            helper.getView<View>(R.id.player22).visibility = View.GONE
        } else {
            helper.getView<View>(R.id.player12).visibility = View.VISIBLE
            helper.getView<View>(R.id.player22).visibility = View.VISIBLE
            helper.setText(R.id.txt_player12, item.player12)
            helper.setText(R.id.txt_player22, item.player22)
            ImageUtils.loadImage(mContext, helper.getView(R.id.img_flag12), item.flag12)
            ImageUtils.loadImage(mContext, helper.getView(R.id.img_flag22), item.flag22)
        }
    }
}
