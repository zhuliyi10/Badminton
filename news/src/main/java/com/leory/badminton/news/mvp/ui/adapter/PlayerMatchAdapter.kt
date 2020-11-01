package com.leory.badminton.news.mvp.ui.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

import com.chad.library.adapter.base.BaseViewHolder
import com.leory.badminton.news.R
import com.leory.badminton.news.mvp.model.bean.PlayerMatchBean
import com.leory.commonlib.base.BaseAdapter
import kotlinx.android.synthetic.main.item_player_match.view.*

/**
 * Describe : 运动员赛果adapter
 * Author : leory
 * Date : 2019-06-11
 */
class PlayerMatchAdapter(data: List<PlayerMatchBean>?) : BaseAdapter<PlayerMatchBean>(R.layout.item_player_match, data) {

    override fun convert(helper: BaseViewHolder, item: PlayerMatchBean) {
        val info = item.matchInfo
        helper.setText(R.id.txt_name, info?.name ?: "")
        helper.setText(R.id.txt_category, info?.category ?: "")
        helper.setText(R.id.txt_time, info?.date ?: "")
        helper.setText(R.id.txt_bonus, info?.bonus ?: "")
        helper.itemView.apply {
            if(info!!.isShort){
                head.visibility=View.GONE
            }else{
                head.visibility=View.VISIBLE
            }
        }

        val rcv = helper.getView<RecyclerView>(R.id.rcv)
        rcv.layoutManager = LinearLayoutManager(mContext)
        rcv.adapter = PlayerMatchRoundAdapter(item.rounds)
    }
}
