package com.leory.badminton.news.mvp.ui.adapter

import android.view.View
import android.widget.LinearLayout

import com.chad.library.adapter.base.BaseViewHolder
import com.leory.badminton.news.R
import com.leory.badminton.news.mvp.model.bean.PlayerMatchBean
import com.leory.commonlib.base.BaseAdapter
import com.leory.commonlib.utils.ImageUtils

/**
 * Describe : 运动员赛果比赛adapter
 * Author : leory
 * Date : 2019-06-11
 */
class PlayerMatchRoundAdapter(data: List<PlayerMatchBean.ResultRound>?) : BaseAdapter<PlayerMatchBean.ResultRound>(R.layout.item_player_match_round, data) {

    override fun convert(helper: BaseViewHolder, item: PlayerMatchBean.ResultRound) {
        helper.setText(R.id.txt_round, item.round)
        helper.setText(R.id.txt_duration, item.duration)
        helper.setText(R.id.txt_player1, item.player1)
        helper.setText(R.id.txt_player2, item.player2)
        ImageUtils.loadImage(mContext, helper.getView(R.id.img_flag1), item.flag1)
        ImageUtils.loadImage(mContext, helper.getView(R.id.img_flag2), item.flag2)
        helper.setText(R.id.vs, "vs")
        helper.setText(R.id.txt_score, item.score)

        val player12 = helper.getView<LinearLayout>(R.id.player12)
        val player22 = helper.getView<LinearLayout>(R.id.player22)
        if (item.player12 == null) {
            player12.visibility = View.GONE
            player22.visibility = View.GONE
        } else {
            player12.visibility = View.VISIBLE
            player22.visibility = View.VISIBLE
            helper.setText(R.id.txt_player12, item.player12)
            helper.setText(R.id.txt_player22, item.player22)
            ImageUtils.loadImage(mContext, helper.getView(R.id.img_flag12), item.flag12)
            ImageUtils.loadImage(mContext, helper.getView(R.id.img_flag22), item.flag22)
        }
    }
}
