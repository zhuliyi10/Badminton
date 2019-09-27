package com.leory.badminton.news.mvp.ui.widget.spinner

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView

import com.chad.library.adapter.base.BaseViewHolder
import com.leory.badminton.news.R
import com.leory.commonlib.base.BaseAdapter

/**
 * Describe : 下拉菜单adapter
 * Author : zhuly
 * Date : 2018-10-23
 */

class SpinnerPopAdapter(data: List<String>?, private val change: Boolean) : BaseAdapter<String>(R.layout.item_spinner_pop, data) {
    private var selectPos: Int = 0

    fun setSelectPos(selectPos: Int) {
        this.selectPos = selectPos
    }

    override fun convert(helper: BaseViewHolder, item: String) {
        val pos = helper.adapterPosition
        val imageView = helper.getView<ImageView>(R.id.image_select)

        if (selectPos == pos && change) {
            helper.setTextColor(R.id.txt_name, Color.parseColor("#007AFF"))
        } else {
            helper.setTextColor(R.id.txt_name, Color.parseColor("#2D323A"))
        }
        helper.setText(R.id.txt_name, item)
        val line = helper.getView<View>(R.id.line)
        if (pos == data.size - 1) {
            line.visibility = View.GONE
        } else {
            line.visibility = View.VISIBLE
        }

    }
}
