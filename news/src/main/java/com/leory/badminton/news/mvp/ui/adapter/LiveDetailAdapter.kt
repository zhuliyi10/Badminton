package com.leory.badminton.news.mvp.ui.adapter

import android.view.View
import com.chad.library.adapter.base.BaseViewHolder
import com.leory.badminton.news.R
import com.leory.badminton.news.mvp.model.bean.LiveDetailBean
import com.leory.commonlib.base.BaseAdapter
import com.leory.commonlib.image.ImageConfig
import com.leory.commonlib.utils.AppUtils

/**
 * Describe : 直播列表
 * Author : leory
 * Date : 2019-06-04
 */
class LiveDetailAdapter(data: List<LiveDetailBean>?) : BaseAdapter<LiveDetailBean>(R.layout.item_live_detail, data) {

    override fun convert(helper: BaseViewHolder, item: LiveDetailBean) {
        helper.setText(R.id.txt_type, item.type)
        helper.setText(R.id.txt_time, item.time)
        helper.setText(R.id.txt_field, item.field)
        helper.setText(R.id.txt_player1, item.player1)
        helper.setText(R.id.txt_player2, item.player2)
        var config = ImageConfig.Builder()
                .imageView(helper.getView(R.id.img_flag1))
                .url(item.flag1)
                .build()
        AppUtils.obtainImageLoader().loadImage(mContext, config)
        config = ImageConfig.Builder()
                .imageView(helper.getView(R.id.img_flag2))
                .url(item.flag2)
                .build()
        AppUtils.obtainImageLoader().loadImage(mContext, config)
        helper.setText(R.id.vs, item.vs)
        helper.setText(R.id.txt_score, item.score)

        if ("男单" == item.type || "女单" == item.type) {
            helper.getView<View>(R.id.player12).visibility = View.GONE
            helper.getView<View>(R.id.player22).visibility = View.GONE
        } else {
            helper.getView<View>(R.id.player12).visibility = View.VISIBLE
            helper.getView<View>(R.id.player22).visibility = View.VISIBLE
            helper.setText(R.id.txt_player12, item.player12)
            helper.setText(R.id.txt_player22, item.player22)
            config = ImageConfig.Builder()
                    .imageView(helper.getView(R.id.img_flag12))
                    .url(item.flag12)
                    .build()
            AppUtils.obtainImageLoader().loadImage(mContext, config)
            config = ImageConfig.Builder()
                    .imageView(helper.getView(R.id.img_flag22))
                    .url(item.flag22)
                    .build()
            AppUtils.obtainImageLoader().loadImage(mContext, config)
        }
    }
}
