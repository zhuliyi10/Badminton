package com.leory.badminton.news.mvp.ui.widget.spinner

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
import com.leory.badminton.news.R
import com.leory.commonlib.utils.ScreenUtils
import com.leory.commonlib.widget.morePop.MorePopBean
import kotlinx.android.synthetic.main.item_spinner_pop.view.txt_name
import kotlinx.android.synthetic.main.view_spinner_pop.view.*

/**
 * Describe : 通用选择的弹出框
 * Author : leory
 * Date : 2019-05-19
 */

class SpinnerPopView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
    : FrameLayout(context, attrs) {


    private var listener: OnSelectListener? = null
    private var data: List<String>? = null
    private var selectPos = 0
    private var change = true
    private val adapter: SpinnerPopAdapter by lazy {
        SpinnerPopAdapter(data, change)
    }
    private val window: PopupWindow by lazy {
        val contentView = LayoutInflater.from(context).inflate(R.layout.pop_spinner_list, null, true)
        val rcv = contentView.findViewById<RecyclerView>(R.id.rcv)
        rcv.layoutManager = LinearLayoutManager(context)

        adapter.setSelectPos(selectPos)
        rcv.adapter = adapter
        //这句是为了防止弹出菜单获取焦点之后，点击activity的其他组件没有响应
        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { baseQuickAdapter, view, position ->
            val item = adapter.data[position]
            if (listener != null) {
                window.dismiss()
                adapter.setSelectPos(position)
                setSelectPos(position)
                adapter.notifyDataSetChanged()
                listener!!.onItemClick(position, item)
            }
        }
        PopupWindow(contentView, width, ViewGroup.LayoutParams.WRAP_CONTENT, true).also {
            it.setBackgroundDrawable(BitmapDrawable())
            it.showAsDropDown(this, 0, ScreenUtils.dp2px(context, 8f))
        }
    }
    private var prohibit: Boolean = false//是否禁止弹出
    /**
     * 获取获取的名称
     * @return
     */
    var selectName: String=""
        get() = txt_name.text.toString()


    init {
        LayoutInflater.from(context).inflate(R.layout.view_spinner_pop, this)
        root.setOnClickListener {
            if (!prohibit) {
                popSelectList()
            }
        }
    }

    /**
     * 初始化列表数据，实体类[MorePopBean]
     * 和位置
     *
     * @param data
     */
    fun initData(data: List<String>, selectPos: Int = 0) {
        this.data = data
        setSelectPos(selectPos)
    }

    /**
     * 更新状态
     */
    fun refreshView() {
        adapter.notifyDataSetChanged()
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
        if (data != null && data!!.size > pos) {
            txt_name.text = data!![pos]
        }
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
        window.showAsDropDown(this, 0, ScreenUtils.dp2px(context, 8f))
    }


    /**
     * 是否禁止弹出
     */
    fun setProhibit(prohibit: Boolean) {
        this.prohibit = prohibit
    }

    interface OnSelectListener {
        fun onItemClick(pos: Int, name: String)
    }

    companion object {
        private val TAG = SpinnerPopView::class.java.simpleName
    }
}

