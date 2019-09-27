package com.leory.badminton.news.mvp.ui.adapter

import android.widget.ImageView

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.leory.badminton.news.R
import com.leory.badminton.news.mvp.model.bean.MatchHistoryBean
import com.leory.badminton.news.mvp.model.bean.MatchHistoryHeadBean
import com.leory.badminton.news.mvp.model.bean.MultiMatchHistoryBean
import com.leory.commonlib.image.ImageConfig
import com.leory.commonlib.utils.AppUtils

/**
 * Describe : 历史赛事adapter
 * Author : leory
 * Date : 2019-06-07
 */
class MatchHistoryAdapter(data: List<MultiMatchHistoryBean<*>>) : BaseMultiItemQuickAdapter<MultiMatchHistoryBean<*>, BaseViewHolder>(data) {

    init {
        addItemType(MultiMatchHistoryBean.TYPE_HEAD, R.layout.head_match_history)
        addItemType(MultiMatchHistoryBean.TYPE_CONTENT, R.layout.item_match_history)
    }

    override fun convert(helper: BaseViewHolder, item: MultiMatchHistoryBean<*>) {
        if (item.itemType == MultiMatchHistoryBean.TYPE_HEAD) {
            val headBean = item.t as MatchHistoryHeadBean
            helper.setText(R.id.year, headBean.year)
            helper.setText(R.id.match_name, headBean.matchName)
        } else {
            val bean = item.t as MatchHistoryBean
            loadImg(helper.getView(R.id.img_ms), bean.msHead)
            loadImg(helper.getView(R.id.ms_flag), bean.msFlag)
            helper.setText(R.id.ms_name, bean.msName)

            loadImg(helper.getView(R.id.img_ws), bean.wsHead)
            loadImg(helper.getView(R.id.ws_flag), bean.wsFlag)
            helper.setText(R.id.ws_name, bean.wsName)

            loadImg(helper.getView(R.id.img_md1), bean.md1Head)
            loadImg(helper.getView(R.id.md1_flag), bean.md1Flag)
            helper.setText(R.id.md1_name, bean.md1Name)
            loadImg(helper.getView(R.id.img_md2), bean.md2Head)
            loadImg(helper.getView(R.id.md2_flag), bean.md2Flag)
            helper.setText(R.id.md2_name, bean.md2Name)

            loadImg(helper.getView(R.id.img_wd1), bean.wd1Head)
            loadImg(helper.getView(R.id.wd1_flag), bean.wd1Flag)
            helper.setText(R.id.wd1_name, bean.wd1Name)
            loadImg(helper.getView(R.id.img_wd2), bean.wd2Head)
            loadImg(helper.getView(R.id.wd2_flag), bean.wd2Flag)
            helper.setText(R.id.wd2_name, bean.wd2Name)

            loadImg(helper.getView(R.id.img_xd1), bean.xd1Head)
            loadImg(helper.getView(R.id.xd1_flag), bean.xd1Flag)
            helper.setText(R.id.xd1_name, bean.xd1Name)
            loadImg(helper.getView(R.id.img_xd2), bean.xd2Head)
            loadImg(helper.getView(R.id.xd2_flag), bean.xd2Flag)
            helper.setText(R.id.xd2_name, bean.xd2Name)
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
