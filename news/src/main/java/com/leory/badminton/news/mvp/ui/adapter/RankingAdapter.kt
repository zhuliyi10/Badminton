package com.leory.badminton.news.mvp.ui.adapter

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.View

import com.chad.library.adapter.base.BaseViewHolder
import com.leory.badminton.news.R
import com.leory.commonlib.utils.ImageUtils
import com.leory.badminton.news.mvp.model.bean.RankingBean
import com.leory.commonlib.base.BaseAdapter

/**
 * Describe : 排名adapter
 * Author : zhuly
 * Date : 2019-05-21
 */
class RankingAdapter(data: List<RankingBean>?) : BaseAdapter<RankingBean>(R.layout.item_ranking, data) {

    override fun convert(helper: BaseViewHolder, item: RankingBean) {
        helper.addOnClickListener(R.id.player_name)
        helper.addOnClickListener(R.id.player2_name)
        helper.setText(R.id.randing_num, item.rankingNum)
        helper.setText(R.id.country_name, item.countryName)
        ImageUtils.loadImage(mContext, helper.getView(R.id.img_country_flag), item.countryFlagUrl)
        helper.setText(R.id.player_name, item.playerName)
        helper.setText(R.id.points, item.points)
        if (item.riseOrDrop > 0) {
            helper.setText(R.id.txt_raise, "+" + item.riseOrDrop)
            helper.setTextColor(R.id.txt_raise, Color.RED)
        } else if (item.riseOrDrop == 0) {
            helper.setText(R.id.txt_raise, "" + item.riseOrDrop)
            helper.setTextColor(R.id.txt_raise, ContextCompat.getColor(mContext, R.color.txt_gray))
        } else {
            helper.setText(R.id.txt_raise, "" + item.riseOrDrop)
            helper.setTextColor(R.id.txt_raise, Color.GREEN)
        }
        if (helper.adapterPosition % 2 == 0) {
            helper.itemView.setBackgroundResource(R.color.white)
        } else {
            helper.itemView.setBackgroundResource(R.color.bg_gray)
        }

        if (TextUtils.isEmpty(item.player2Name)) {
            helper.getView<View>(R.id.player2_name).visibility = View.GONE
            helper.getView<View>(R.id.country2).visibility = View.GONE
        } else {
            helper.getView<View>(R.id.player2_name).visibility = View.VISIBLE
            helper.getView<View>(R.id.country2).visibility = View.VISIBLE
            helper.setText(R.id.country_name2, item.country2Name)
            ImageUtils.loadImage(mContext, helper.getView(R.id.img_country_flag2), item.countryFlag2Url)
            helper.setText(R.id.player2_name, item.player2Name)
        }
    }
}
