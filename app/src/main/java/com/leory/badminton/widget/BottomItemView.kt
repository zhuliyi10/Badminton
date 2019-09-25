package com.leory.badminton.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import butterknife.ButterKnife
import com.leory.badminton.R
import kotlinx.android.synthetic.main.view_bottom_item.view.*

/**
 * Describe : 底部选择器
 * Author : leory
 * Date : 2019-05-22
 */
class BottomItemView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        FrameLayout(context, attrs, defStyleAttr) {

    private var normalRes: Int = 0
    private var activateRes: Int = 0
    private var normalTextColor: Int = 0
    private var activateTextColor: Int = 0
    private var text: String? = null
    private var activate: Boolean = false


    init {
        init(context, attrs, defStyleAttr)

    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BottomItemView, defStyleAttr, 0)
        normalRes = typedArray.getResourceId(R.styleable.BottomItemView_normal_res, -1)
        activateRes = typedArray.getResourceId(R.styleable.BottomItemView_activate_res, -1)
        normalTextColor = typedArray.getColor(R.styleable.BottomItemView_normal_text_color, -1)
        activateTextColor = typedArray.getColor(R.styleable.BottomItemView_activate_text_color, -1)
        text = typedArray.getString(R.styleable.BottomItemView_text)
        activate = typedArray.getBoolean(R.styleable.BottomItemView_activate, false)
        typedArray.recycle()
        val root = LayoutInflater.from(getContext()).inflate(R.layout.view_bottom_item, this)
        ButterKnife.bind(this, root)
        initView()
    }

    private fun initView() {
        text_name.text = text
        isActivate = activate
    }

    /**
     * 是否激活
     *
     * @param activate
     */
    var isActivate: Boolean
        get() = activate
        set(activate) {
            this.activate = activate
            if (activate) {
                icon.setImageResource(activateRes)
                text_name.setTextColor(activateTextColor)
            } else {
                icon.setImageResource(normalRes)
                text_name.setTextColor(normalTextColor)
            }
        }


}
