package com.leory.commonlib.widget.morePop

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.PopupWindow
import com.chad.library.adapter.base.BaseQuickAdapter
import com.leory.commonlib.R
import com.leory.commonlib.utils.ScreenUtils
import kotlinx.android.synthetic.main.view_more_pop.view.*

/**
 * Describe : 通用选择的弹出框
 * Author : zhuly
 * Date : 2018-10-23
 */

class MorePopView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {
    private val window: PopupWindow by lazy {
        val contentView = LayoutInflater.from(context).inflate(R.layout.layout_more_pop, null, true)
        val rcv = contentView.findViewById<RecyclerView>(R.id.rcv)
        rcv.layoutManager = LinearLayoutManager(context)
        adapter.setSelectPos(selectPos)
        rcv.adapter = adapter
        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { baseQuickAdapter, view, position ->
            val bean = adapter!!.data[position]
            if (listener != null && bean.isCanClick) {
                window.dismiss()
                adapter.setSelectPos(position)
                adapter.notifyDataSetChanged()
                listener!!.onItemClick(position, bean.name)
            }
        }
        PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)
                .also {
                    //这句是为了防止弹出菜单获取焦点之后，点击activity的其他组件没有响应
                    it.setBackgroundDrawable(BitmapDrawable())
                    it.showAsDropDown(image_more, ScreenUtils.dp2px(context, 4f), ScreenUtils.dp2px(context, -8f))
                }
    }
    private val adapter: MorePopAdapter by lazy {
        MorePopAdapter(data, change)
    }
    private var clickListener: OnClickListener? = null
    private var listener: OnSelectListener? = null
    private var data: List<MorePopBean>? = null
    private var selectPos = 0
    private var change = false
    private var prohibit: Boolean = false//是否禁止弹出


    init {
        init()
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.view_more_pop, this)
        image_more.setOnClickListener { onViewClicked() }
    }

    /**
     * 初始化列表数据，实体类[MorePopBean]
     *
     * @param data
     */
    fun initData(data: List<MorePopBean>) {
        this.data = data
    }

    /**
     * 更新状态
     */
    fun refreshView() {
        if (adapter != null) {
            adapter!!.notifyDataSetChanged()
        }
    }

    /**
     * 设置是否点击后改变状态
     *
     * @param change 默认false不改变
     */
    fun setSelectStateChange(change: Boolean) {
        this.change = change
    }

    /**
     * 设置选择的位置，默认为0
     */
    fun setSelectPos(pos: Int) {
        this.selectPos = pos
    }

    /**
     * 点击回调
     *
     * @param listener
     */
    fun setOnSelectListener(listener: OnSelectListener) {
        this.listener = listener
    }

    private fun popSelectList() {
        window.showAsDropDown(image_more, ScreenUtils.dp2px(context, 4f), ScreenUtils.dp2px(context, -8f))

    }

    fun onViewClicked() {
        if (clickListener != null) {
            clickListener!!.onClick(image_more)
        }
        if (!prohibit) {
            popSelectList()
        }
    }

    fun setClickListener(onClickListener: OnClickListener) {
        this.clickListener = onClickListener
    }

    /**
     * 是否禁止弹出
     */
    fun setProhibit(prohibit: Boolean) {
        this.prohibit = prohibit
    }

    interface OnSelectListener {
        fun onItemClick(pos: Int, name: String?)
    }

    companion object {
        private val TAG = MorePopView::class.java.simpleName
    }
}
