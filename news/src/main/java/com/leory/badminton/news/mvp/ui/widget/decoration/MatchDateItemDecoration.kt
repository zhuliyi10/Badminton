package com.leory.badminton.news.mvp.ui.widget.decoration

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

import com.leory.commonlib.utils.ScreenUtils

/**
 * Describe : 为了解决最后一项显示不全
 * Author : leory
 * Date : 2019-06-06
 */
class MatchDateItemDecoration(private val context: Context) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val pos = parent.getChildAdapterPosition(view)
        val childCount = parent.adapter!!.itemCount
        if (pos == childCount - 1) {
            outRect.set(0, 0, 0, ScreenUtils.dp2px(context, 20f))
        } else {
            outRect.set(0, 0, 0, 0)
        }
    }
}
