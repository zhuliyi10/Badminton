package com.leory.badminton.news.mvp.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.leory.badminton.news.R
import com.leory.badminton.news.di.component.DaggerMatchComponent
import com.leory.badminton.news.mvp.contract.MatchContract
import com.leory.badminton.news.mvp.model.bean.MatchItemSection
import com.leory.badminton.news.mvp.presenter.MatchPresenter
import com.leory.badminton.news.mvp.ui.activity.MatchDetailActivity
import com.leory.badminton.news.mvp.ui.adapter.MatchSectionAdapter
import com.leory.badminton.news.mvp.ui.widget.spinner.SpinnerPopView
import com.leory.commonlib.base.BaseLazyLoadFragment
import com.leory.commonlib.base.delegate.IComponent
import com.leory.commonlib.di.component.AppComponent
import com.leory.commonlib.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment_match.*
import java.util.*

/**
 * Describe :赛事fragment
 * Author : leory
 * Date : 2019-05-19
 */
class MatchFragment : BaseLazyLoadFragment<MatchPresenter>(), MatchContract.View {


    private lateinit var adapter: MatchSectionAdapter

    override fun setupActivityComponent(component: IComponent): IComponent {
        DaggerMatchComponent.builder()
                .view(this)
                .appComponent(component as AppComponent)
                .build()
                .inject(this)
        return super.setupActivityComponent(component)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_match, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {
        val years = arrayOf("2020", "2019", "2018", "2017", "2016", "2015", "2014")
        spinner_year.initData(Arrays.asList(*years), 1)
        spinner_year.setOnSelectListener(object : SpinnerPopView.OnSelectListener {
            override fun onItemClick(pos: Int, name: String) {
                presenter?.requestData(name, spinner_finish.selectName)
            }
        })
        val finishes = arrayOf("全部", "已完成", "剩余")
        spinner_finish.initData(listOf(*finishes), 2)
        spinner_finish.setOnSelectListener(object : SpinnerPopView.OnSelectListener {
            override fun onItemClick(pos: Int, name: String) {
                presenter?.requestData(spinner_finish.selectName, name)
            }
        })
        rcv.layoutManager = LinearLayoutManager(context)
        adapter = MatchSectionAdapter(ArrayList())
        rcv.adapter = adapter
        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { baseQuickAdapter, view, position ->
            if (!adapter.data[position].isHeader) {
                val matchUrl = adapter.data[position].t.matchUrl
                val matchClassify = adapter.data[position].t.matchClassify
                matchUrl?.let { MatchDetailActivity.launch(activity!!, matchUrl, matchClassify) }

            }
        }

    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
        rcv.visibility = View.GONE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
        rcv.visibility = View.VISIBLE
    }

    override fun showMessage(message: String) {
        ToastUtils.showShort(message)
    }

    override fun showMatchData(data: List<MatchItemSection>) {
        adapter.setNewData(data)
    }

    override fun lazyLoadData() {
        adapter.addHeaderView(LayoutInflater.from(activity).inflate(R.layout.head_match, rcv, false))
        presenter?.requestData(spinner_year.selectName, spinner_finish.selectName)
    }
}
