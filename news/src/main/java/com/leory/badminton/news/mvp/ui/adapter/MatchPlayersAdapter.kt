package com.leory.badminton.news.mvp.ui.adapter

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.leory.badminton.news.R
import com.leory.badminton.news.mvp.model.bean.MatchHistoryBean
import com.leory.badminton.news.mvp.model.bean.MatchHistoryHeadBean
import com.leory.badminton.news.mvp.model.bean.MatchPlayerHeadBean
import com.leory.badminton.news.mvp.model.bean.MatchPlayerListBean
import com.leory.badminton.news.mvp.model.bean.MultiMatchHistoryBean
import com.leory.badminton.news.mvp.model.bean.MultiMatchPlayersBean
import com.leory.commonlib.image.ImageConfig
import com.leory.commonlib.utils.AppUtils

import java.util.ArrayList

/**
 * Describe : 参赛运动员adapter
 * Author : leory
 * Date : 2019-06-10
 */
class MatchPlayersAdapter(data: List<MultiMatchPlayersBean<*>>) : BaseMultiItemQuickAdapter<MultiMatchPlayersBean<*>, BaseViewHolder>(data) {

    init {
        addItemType(MultiMatchHistoryBean.TYPE_HEAD, R.layout.head_match_players_tips)
        addItemType(MultiMatchHistoryBean.TYPE_CONTENT, R.layout.item_match_players_country)
    }

    override fun convert(helper: BaseViewHolder, item: MultiMatchPlayersBean<*>) {
        if (item.itemType == MultiMatchHistoryBean.TYPE_HEAD) {
            val headBean = item.t as MatchPlayerHeadBean
            helper.setText(R.id.txt_name, headBean.name)
            val second = helper.getView<TextView>(R.id.txt_second)
            if (TextUtils.isEmpty(headBean.second)) {
                second.visibility = View.GONE
            } else {
                second.visibility = View.VISIBLE
                second.text = headBean.second
            }

        } else {
            val listBean = item.t as MatchPlayerListBean
            val rcv = helper.getView<RecyclerView>(R.id.rcv_country)
            rcv.layoutManager = GridLayoutManager(mContext, 2)
            rcv.adapter = MatchPlayerAdapter(listBean.data)
        }
    }


}
