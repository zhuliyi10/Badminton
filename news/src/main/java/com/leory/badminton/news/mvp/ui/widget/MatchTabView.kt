package com.leory.badminton.news.mvp.ui.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.leory.badminton.news.R
import com.leory.commonlib.utils.ScreenUtils

/**
 * Describe : 比赛自定义view
 * Author : leory
 * Date : 2019-06-03
 */
class MatchTabView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {
    private var listener: OnChildClickListener? = null
    private var itemWidth: Int = 0
    private var textSize: Int = 0
    private var textColor: ColorStateList? = null
    private var bgColor: Drawable? = null
    var selectPos: Int
        get() {
            for (i in 0 until childCount) {
                val tv = getChildAt(i) as TextView
                if (tv.isSelected) {
                    return i
                }
            }
            return 0
        }
        set(pos) {
            for (i in 0 until childCount) {
                val tv = getChildAt(i) as TextView
                tv.isSelected = i == pos
            }
        }

    private val selectView: View?
        get() {
            for (i in 0 until childCount) {
                val tv = getChildAt(i) as TextView
                if (tv.isSelected) return tv
            }
            return null
        }

    private val textView: TextView
        get() {
            val textView = TextView(context)
            if (textColor != null) {
                textView.setTextColor(textColor)
            }
            if (bgColor != null) {
                textView.background = bgColor
            }

            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
            textView.gravity = Gravity.CENTER
            return textView
        }

    init {
        initView(context, attrs, defStyleAttr)
    }

    private fun initView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MatchTabView, defStyleAttr, 0)
        itemWidth = typedArray.getDimensionPixelSize(R.styleable.MatchTabView_item_width, ScreenUtils.dp2px(context, 50f))
        textSize = typedArray.getDimensionPixelSize(R.styleable.MatchTabView_text_size, ScreenUtils.dp2px(context, 12f))
        textColor = typedArray.getColorStateList(R.styleable.MatchTabView_text_color)
        bgColor = typedArray.getDrawable(R.styleable.MatchTabView_bg_color)
        typedArray.recycle()
    }

    fun initData(data: List<String>) {
        removeAllViews()
        val count = data.size
        for (i in 0 until count) {
            addTextView(data[i], itemWidth)
        }

        for (i in 0 until childCount) {
            val tv = getChildAt(i) as TextView
            tv.setOnClickListener {
                if (listener != null && tv != selectView) {
                    listener!!.onClick(tv, i)
                }
            }
        }
    }

    fun getTextView(pos: Int): TextView {

        return getChildAt(pos) as TextView
    }

    private fun addTextView(text: String, width: Int) {
        val textView = textView
        textView.text = text
        val lp = LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT)
        addView(textView, lp)
    }

    fun setOnChildClickListener(listener: OnChildClickListener) {
        this.listener = listener
    }

    interface OnChildClickListener {
        fun onClick(tv: TextView, position: Int)
    }
}
