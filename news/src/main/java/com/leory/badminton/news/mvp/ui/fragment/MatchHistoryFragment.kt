package com.leory.badminton.news.mvp.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leory.badminton.news.R
import com.leory.badminton.news.di.component.MatchDetailComponent
import com.leory.badminton.news.mvp.contract.MatchDetailContract
import com.leory.badminton.news.mvp.model.bean.MultiMatchHistoryBean
import com.leory.badminton.news.mvp.presenter.MatchHistoryPresenter
import com.leory.badminton.news.mvp.ui.adapter.MatchHistoryAdapter
import com.leory.badminton.news.mvp.ui.widget.decoration.MatchDateItemDecoration
import com.leory.commonlib.base.BaseLazyLoadFragment
import com.leory.commonlib.base.delegate.IComponent
import kotlinx.android.synthetic.main.fragment_match_history.*
import java.util.*

/**
 * Describe : 历史比赛fragment
 * Author : leory
 * Date : 2019-06-06
 */
class MatchHistoryFragment : BaseLazyLoadFragment<MatchHistoryPresenter>(), MatchDetailContract.MatchHistory {
    companion object {
        private const val KEY_HISTORY_URL = "key_history_url"

        @JvmStatic
        fun newInstance(historyUrl: String): MatchHistoryFragment {
            val fragment = MatchHistoryFragment()
            val args = Bundle()
            args.putString(KEY_HISTORY_URL, historyUrl)
            fragment.arguments = args
            return fragment
        }
    }


    private lateinit var adapter: MatchHistoryAdapter

    override fun setupActivityComponent(component: IComponent): IComponent {
        (component as MatchDetailComponent).buildMatchHistoryComponent()
                .view(this)
                .historyUrl(arguments?.getString(KEY_HISTORY_URL))
                .build()
                .inject(this)
        return super.setupActivityComponent(component)
    }

    override fun lazyLoadData() {
        presenter?.requestData()
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_match_history, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {
        rcv.layoutManager = LinearLayoutManager(context)
        rcv.addItemDecoration(MatchDateItemDecoration(context))
        adapter = MatchHistoryAdapter(ArrayList<MultiMatchHistoryBean<*>>())
        rcv.adapter = adapter
    }

    override fun showLoading() {
        progress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress.visibility = View.GONE
    }

    override fun showMessage(message: String) {

    }


    override fun showHistoryData(data: List<MultiMatchHistoryBean<*>>) {
        adapter.setNewData(data)
    }


}
