package com.leory.badminton.mine.mvp.ui.widget

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.leory.badminton.mine.R
import com.leory.commonlib.utils.ImageUtils
import kotlinx.android.synthetic.main.view_item.view.*

/**
 * Describe : 自定义左右内容视图
 * Author : leory
 * Date : 2019-6-19
 */

class ItemView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        FrameLayout(context, attrs, defStyleAttr) {



    init {
        init(context, attrs, defStyleAttr)
    }

    /**
     * 右边文字
     *
     * @param text
     */
    var rightText: String?
        get() = txt_right.text.toString().trim { it <= ' ' }
        set(text) = if (!TextUtils.isEmpty(text)) {
            txt_right.text = text
            txt_right.visibility = View.VISIBLE
        } else {
            txt_right.visibility = View.GONE
        }


    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemView, defStyleAttr, 0)
        val leftText = typedArray.getString(R.styleable.ItemView_left_text)
        val leftSecondText = typedArray.getString(R.styleable.ItemView_left_second_text)
        var rightText = typedArray.getString(R.styleable.ItemView_right_text)
        val leftTextColor = typedArray.getColor(R.styleable.ItemView_left_text_color, -1)
        val leftTextSecondColor = typedArray.getColor(R.styleable.ItemView_left_second_text_color, -1)
        val rightTextColor = typedArray.getColor(R.styleable.ItemView_right_text_color, -1)
        val showRightArrow = typedArray.getBoolean(R.styleable.ItemView_show_right_arrow, false)
        val showDivider = typedArray.getBoolean(R.styleable.ItemView_show_divider, false)
        val iconLeft = typedArray.getResourceId(R.styleable.ItemView_icon_left, -1)
        val iconRight = typedArray.getResourceId(R.styleable.ItemView_icon_right, -1)
        typedArray.recycle()
        LayoutInflater.from(getContext()).inflate(R.layout.view_item, this)

        setLeftText(leftText)
        setLeftSecondText(leftSecondText)
        this.rightText = rightText
        setLeftTextColor(leftTextColor)
        setLeftSecondTextColor(leftTextSecondColor)
        setRightTextColor(rightTextColor)
        showRightArrow(showRightArrow)
        showDivider(showDivider)

        setLeftIcon(iconLeft)
        setRightIcon(iconRight)

    }

    /**
     * 设置左边文字
     *
     * @param text
     */
    fun setLeftText(text: String?) {
        if (!TextUtils.isEmpty(text)) {
            txt_left.text = text
            txt_left.visibility = View.VISIBLE
        } else {
            txt_left.visibility = View.GONE
        }
    }

    /**
     * 设置左边文字的颜色
     *
     * @param color
     */
    fun setLeftTextColor(color: Int) {
        if (color != -1) {
            txt_left.setTextColor(color)
        }
    }

    /**
     * 设置左边次要文字
     *
     * @param text
     */
    fun setLeftSecondText(text: String?) {
        if (!TextUtils.isEmpty(text)) {
            txt_left_second.text = text
            txt_left_second.visibility = View.VISIBLE
        } else {
            txt_left_second.visibility = View.GONE
        }
    }

    /**
     * 设置左边次要文字的颜色
     *
     * @param color
     */
    fun setLeftSecondTextColor(color: Int) {
        if (color != -1) {
            txt_left_second.setTextColor(color)
        }
    }

    /**
     * 设置右边文字的颜色
     *
     * @param color
     */
    fun setRightTextColor(color: Int) {
        if (color != -1) {
            txt_right.setTextColor(color)
        }
    }

    /**
     * 是否显示右箭头
     *
     * @param isShow
     */
    fun showRightArrow(isShow: Boolean) {
        if (isShow) {
            arrow_right.visibility = View.VISIBLE
        } else {
            arrow_right.visibility = View.GONE
        }
    }

    /**
     * 显示下划线
     *
     * @param isShow
     */
    fun showDivider(isShow: Boolean) {
        if (isShow) {
            divider.visibility = View.VISIBLE
        } else {
            divider.visibility = View.GONE
        }
    }

    /**
     * 设置左边Icon
     *
     * @param resId
     */
    fun setLeftIcon(resId: Int) {
        if (resId != -1) {
            icon_left.visibility = View.VISIBLE
            icon_left.setImageResource(resId)
        } else {
            icon_left.visibility = View.GONE
        }
    }

    /**
     * 设置右边Icon
     *
     * @param resId
     */
    fun setRightIcon(resId: Int) {
        if (resId != -1) {
            icon_right.visibility = View.VISIBLE
            icon_right.setImageResource(resId)
        } else {
            icon_right.visibility = View.GONE
        }
    }

    /**
     * 设置左边图片的url
     *
     * @param url
     */
    fun setLeftIconUrl(url: String) {
        icon_left.visibility = View.VISIBLE
        ImageUtils.loadImage(context, icon_left, url)
    }

    /**
     * 设置右边图片的url
     *
     * @param url
     */
    fun setRightIconUrl(url: String) {
        icon_right.visibility = View.VISIBLE
        ImageUtils.loadImage(context, icon_right, url)
    }
}
