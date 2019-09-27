package com.leory.badminton.news.mvp.ui.widget

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Describe : 禁止左右滑动
 * Author : leory
 * Date : 2019-05-22
 */
class ScrollViewPager : ViewPager {
    private var scrollable = false

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    private fun setScrollablel(scrollable: Boolean) {
        this.scrollable = scrollable
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item, scrollable)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return scrollable
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return scrollable
    }
}
