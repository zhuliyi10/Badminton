package com.leory.badminton.news.mvp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.leory.badminton.news.R
import com.leory.badminton.news.di.component.MatchDetailComponent
import com.leory.badminton.news.mvp.contract.MatchDetailContract
import com.leory.badminton.news.mvp.presenter.MatchAgainstPresenter
import com.leory.badminton.news.mvp.ui.widget.MatchTabView
import com.leory.badminton.news.mvp.ui.widget.againstFlow.AgainstFlowBean
import com.leory.badminton.news.mvp.ui.widget.spinner.SpinnerPopView
import com.leory.commonlib.base.BaseLazyLoadFragment
import com.leory.commonlib.base.delegate.IComponent
import kotlinx.android.synthetic.main.fragment_match_against_chart.*
import java.util.*

/**
 * Describe : 对阵表
 * Author : leory
 * Date : 2019-06-04
 */

class MatchAgainstChartFragment : BaseLazyLoadFragment<MatchAgainstPresenter>(), MatchDetailContract.MatchAgainView, SpinnerPopView.OnSelectListener {
    companion object {
        @JvmStatic
        fun newInstance(): MatchAgainstChartFragment {
            return MatchAgainstChartFragment()
        }
    }
    private val matchType = arrayOf("男单", "女单", "男双", "女双", "混双")

    override fun lazyLoadData() {
        presenter?.requestData(null)
    }

    override fun setupActivityComponent(component: IComponent): IComponent {
        (component as MatchDetailComponent).buildMatchAgainstComponent()
                .view(this)
                .build()
                .inject(this)
        return super.setupActivityComponent(component)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_match_against_chart, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {
        spinner_type.initData(Arrays.asList(*matchType))
        spinner_type.setOnSelectListener(this)
        if (presenter?.isGroup == true) {
            spinner_type.visibility = View.GONE
        }
    }

    override fun showLoading() {
        progress.visibility = View.VISIBLE
        againstFlow.visibility = View.GONE
    }

    override fun hideLoading() {
        if (progress != null) {
            progress.visibility = View.GONE
            againstFlow!!.visibility = View.VISIBLE
        }
    }

    override fun showMessage(message: String) {

    }

    override fun showAgainstView(againstData: List<List<AgainstFlowBean>>) {
        againstFlow!!.setAgainstData(againstData)
    }

    override fun showMatchSchedule(tags: List<String>?) {
        if (tags != null && tags.isNotEmpty()) {
            scheduleView.initData(tags)
            scheduleView.setOnChildClickListener(object : MatchTabView.OnChildClickListener {
                override fun onClick(tv: TextView, position: Int) {
                    scheduleView.selectPos = position
                    presenter?.selectSchedule(tv.text.toString(), position)
                }
            })
            scheduleView.selectPos = 0
            presenter?.selectSchedule(scheduleView!!.getTextView(0).text.toString(), 0)
        }
    }

    override fun onItemClick(pos: Int, name: String) {
        presenter?.requestData(name)
    }


}
