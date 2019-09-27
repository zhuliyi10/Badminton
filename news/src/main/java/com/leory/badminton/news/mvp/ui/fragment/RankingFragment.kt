package com.leory.badminton.news.mvp.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leory.badminton.news.R
import com.leory.badminton.news.di.component.DaggerRankingComponent
import com.leory.badminton.news.mvp.contract.RankingContract
import com.leory.badminton.news.mvp.model.bean.RankingBean
import com.leory.badminton.news.mvp.presenter.RankingPresenter
import com.leory.badminton.news.mvp.ui.activity.PlayerDetailActivity
import com.leory.badminton.news.mvp.ui.adapter.RankingAdapter
import com.leory.badminton.news.mvp.ui.widget.spinner.SpinnerPopView
import com.leory.commonlib.base.BaseLazyLoadFragment
import com.leory.commonlib.base.delegate.IComponent
import com.leory.commonlib.di.component.AppComponent
import com.leory.commonlib.utils.ToastUtils
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import kotlinx.android.synthetic.main.fragment_ranking.*
import java.util.*

/**
 * Describe :排名fragment
 * Author : leory
 * Date : 2019-05-19
 */
class RankingFragment : BaseLazyLoadFragment<RankingPresenter>(), RankingContract.View, OnLoadMoreListener {
    companion object {
        @JvmField
        val RANKING_TYPES = arrayOf("男单", "女单", "男双", "女双", "混双")
    }

    private lateinit var adapter: RankingAdapter


    override fun setupActivityComponent(component: IComponent): IComponent {
        DaggerRankingComponent.builder()
                .appComponent(component as AppComponent)
                .view(this)
                .build()
                .inject(this)
        return super.setupActivityComponent(component)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_ranking, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {
        rcv.layoutManager = LinearLayoutManager(context)
        adapter = RankingAdapter(ArrayList())
        rcv.adapter = adapter
        adapter.setOnItemChildClickListener { _, view, position ->
            val rankingBean = adapter.data[position]
            if (view.id == R.id.player_name) {
                PlayerDetailActivity.launch(activity!!, rankingBean.playerUrl)
            } else if (view.id == R.id.player2_name) {
                PlayerDetailActivity.launch(activity!!, rankingBean.player2Url)
            }
        }
        refreshLayout.setOnLoadMoreListener(this)
        refreshLayout.setEnableRefresh(false)
        refreshLayout.setEnableLoadMore(false)
    }

    override fun lazyLoadData() {
        presenter?.firstInit()
    }

    override fun startLoadMore() {

    }

    override fun endLoadMore() {
        refreshLayout!!.finishLoadMore()
    }

    override fun showRankingData(refresh: Boolean, data: List<RankingBean>) {
        if (refresh) {
            adapter.setNewData(data)
        } else {
            adapter.addData(data)
        }
    }

    override fun showWeekData(data: List<String>) {
        activity!!.runOnUiThread {
            spinner_week.initData(data)
            spinner_week.setOnSelectListener(object : SpinnerPopView.OnSelectListener {
                override fun onItemClick(pos: Int, name: String) {
                    presenter?.selectData(true, spinner_type.selectName, name)
                }
            })
            spinner_type.initData(listOf(*RANKING_TYPES))
            spinner_type.setOnSelectListener(object : SpinnerPopView.OnSelectListener {
                override fun onItemClick(pos: Int, name: String) {
                    presenter?.selectData(true, name, spinner_week.selectName)
                }
            })
            refreshLayout.setEnableLoadMore(true)
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

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        presenter?.selectData(false, spinner_type.selectName, spinner_week.selectName)
    }


}
