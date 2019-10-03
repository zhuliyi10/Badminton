package com.leory.commonlib.widget.morePop

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout

import com.chad.library.adapter.base.BaseViewHolder
import com.leory.commonlib.R
import com.leory.commonlib.base.BaseAdapter

/**
 * Describe : 选择更多adapter
 * Author : zhuly
 * Date : 2018-10-23
 */

class MorePopAdapter(data: List<MorePopBean>?, private val change: Boolean) : BaseAdapter<MorePopBean>(R.layout.item_more_pop, data) {
    private var selectPos: Int = 0

    fun setSelectPos(selectPos: Int) {
        this.selectPos = selectPos
    }

    override fun convert(helper: BaseViewHolder, item: MorePopBean) {
        val pos = helper.adapterPosition
        val imageView = helper.getView<ImageView>(R.id.image_select)
        if (item.drawRes == 0) {
            imageView.visibility = View.GONE
        } else {
            imageView.visibility = View.VISIBLE
            imageView.setImageResource(item.drawRes)
        }

        if (selectPos == pos && change) {
            helper.setTextColor(R.id.txt_name, Color.parseColor("#007AFF"))
        } else {
            helper.setTextColor(R.id.txt_name, Color.parseColor("#2D323A"))
        }

        val params = helper.itemView.layoutParams as RecyclerView.LayoutParams
        if (!item.isCanClick) {
            params.height = 0
            params.width = 0
            helper.itemView.visibility = View.VISIBLE
        } else {
            params.height = LinearLayout.LayoutParams.WRAP_CONTENT
            params.width = LinearLayout.LayoutParams.MATCH_PARENT
            helper.itemView.visibility = View.VISIBLE
        }
        helper.setText(R.id.txt_name, item.name)
        val line = helper.getView<View>(R.id.line)
        if (pos == data.size - 1) {
            line.visibility = View.GONE
        } else {
            line.visibility = View.VISIBLE
        }

    }
}
