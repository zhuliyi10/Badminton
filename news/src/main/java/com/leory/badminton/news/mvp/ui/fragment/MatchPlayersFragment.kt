package com.leory.badminton.news.mvp.ui.fragment

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.leory.badminton.news.R
import com.leory.badminton.news.di.component.MatchDetailComponent
import com.leory.badminton.news.mvp.contract.MatchDetailContract
import com.leory.badminton.news.mvp.model.bean.MultiMatchPlayersBean
import com.leory.badminton.news.mvp.presenter.MatchPlayersPresenter
import com.leory.badminton.news.mvp.ui.adapter.MatchPlayersAdapter
import com.leory.badminton.news.mvp.ui.widget.MatchTabView
import com.leory.badminton.news.mvp.ui.widget.decoration.MatchDateItemDecoration
import com.leory.commonlib.base.BaseLazyLoadFragment
import com.leory.commonlib.base.delegate.IComponent
import kotlinx.android.synthetic.main.fragment_match_players.*
import java.util.*

/**
 * Describe : 参赛运动员
 * Author : leory
 * Date : 2019-06-10
 */
class MatchPlayersFragment : BaseLazyLoadFragment<MatchPlayersPresenter>(), MatchDetailContract.MatchPlayersView {
    companion object {
        @JvmStatic
        fun newInstance(): MatchPlayersFragment {
            return MatchPlayersFragment()
        }
    }

    lateinit var tab: MatchTabView
    lateinit var adapter: MatchPlayersAdapter

    override fun setupActivityComponent(component: IComponent): IComponent {
        (component as MatchDetailComponent).buildMatchPlayersComponent()
                .view(this)
                .build()
                .inject(this)
        return super.setupActivityComponent(component)
    }

    override fun lazyLoadData() {
        val types = arrayOf("男单", "女单", "男双", "女双", "混双")
        tab.initData(listOf(*types))
        tab.setOnChildClickListener(object : MatchTabView.OnChildClickListener {
            override fun onClick(tv: TextView, position: Int) {
                tab.selectPos = position
                presenter?.requestData(types[position])
            }
        })

        tab.selectPos = 0
        presenter?.requestData(types[0])
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_match_players, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {
        rcv.layoutManager = LinearLayoutManager(context)
        rcv.addItemDecoration(MatchDateItemDecoration(context!!))
        val head = LayoutInflater.from(context).inflate(R.layout.head_match_players, null) as ConstraintLayout
        tab = head.findViewById(R.id.tab)
        adapter = MatchPlayersAdapter(ArrayList<MultiMatchPlayersBean<*>>())
        rcv.adapter = adapter
        adapter.addHeaderView(head)
    }


    override fun showLoading() {
        progress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress.visibility = View.GONE
    }

    override fun showMessage(message: String) {

    }

    override fun showPlayersData(data: List<MultiMatchPlayersBean<*>>) {
        adapter!!.setNewData(data)
    }


}
