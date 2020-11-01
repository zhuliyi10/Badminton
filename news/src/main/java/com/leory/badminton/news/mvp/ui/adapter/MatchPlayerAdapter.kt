package com.leory.badminton.news.mvp.ui.adapter


import android.app.Activity
import android.view.View
import android.widget.ImageView

import com.chad.library.adapter.base.BaseViewHolder
import com.leory.badminton.news.R
import com.leory.badminton.news.mvp.model.bean.MatchPlayerBean
import com.leory.badminton.news.mvp.ui.activity.PlayerDetailActivity
import com.leory.commonlib.base.BaseAdapter
import com.leory.commonlib.image.ImageConfig
import com.leory.commonlib.utils.AppUtils
import kotlinx.android.synthetic.main.item_match_player.view.*

/**
 * Describe : 参赛运动员adapter
 * Author : leory
 * Date : 2019-06-10
 */
class MatchPlayerAdapter(data: List<MatchPlayerBean>?) : BaseAdapter<MatchPlayerBean>(R.layout.item_match_player, data) {

    override fun convert(helper: BaseViewHolder, item: MatchPlayerBean) {
        helper.setText(R.id.name1, item.name1)
        helper.setText(R.id.country1, item.country1)
        loadImg(helper.getView(R.id.head1), item.head1)
        loadImg(helper.getView(R.id.flag1), item.flag1)
        if (item.name2 != null) {
            helper.getView<View>(R.id.player2).visibility = View.VISIBLE
            helper.setText(R.id.name2, item.name2)
            helper.setText(R.id.country2, item.country2)
            loadImg(helper.getView(R.id.head2), item.head2)
            loadImg(helper.getView(R.id.flag2), item.flag2)
        } else {
            helper.getView<View>(R.id.player2).visibility = View.GONE
        }

        helper.itemView.apply {
            player1.setOnClickListener {
                PlayerDetailActivity.launch(mContext as Activity,item.url1)
            }
            player2.setOnClickListener {
                PlayerDetailActivity.launch(mContext as Activity,item.url2)
            }
        }
    }

    private fun loadImg(imageView: ImageView, url: String?) {
        val config = ImageConfig.Builder()
                .imageView(imageView)
                .url(url)
                .build()
        AppUtils.obtainImageLoader().loadImage(mContext, config)
    }
}
