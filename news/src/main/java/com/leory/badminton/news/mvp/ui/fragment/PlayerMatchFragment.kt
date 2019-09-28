package com.leory.badminton.news.mvp.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leory.badminton.news.R
import com.leory.badminton.news.di.component.PlayerDetailComponent
import com.leory.badminton.news.mvp.contract.PlayerContract
import com.leory.badminton.news.mvp.model.bean.PlayerMatchBean
import com.leory.badminton.news.mvp.presenter.PlayerMatchPresenter
import com.leory.badminton.news.mvp.ui.adapter.PlayerMatchAdapter
import com.leory.badminton.news.mvp.ui.widget.decoration.MatchDateItemDecoration
import com.leory.commonlib.base.BaseLazyLoadFragment
import com.leory.commonlib.base.delegate.IComponent
import com.leory.commonlib.di.scope.FragmentScope
import kotlinx.android.synthetic.main.fragment_player_match.*
import java.util.*

/**
 * Describe : 运动员比赛结果
 * Author : leory
 * Date : 2019-06-11
 */
@FragmentScope
class PlayerMatchFragment : BaseLazyLoadFragment<PlayerMatchPresenter>(), PlayerContract.MatchView {
    companion object {
        @JvmStatic
        fun newInstance(): PlayerMatchFragment {
            return PlayerMatchFragment()
        }
    }

    val adapter: PlayerMatchAdapter by lazy {
        PlayerMatchAdapter(ArrayList())
    }

    override fun setupActivityComponent(component: IComponent): IComponent {
        (component as PlayerDetailComponent).buildPlayerMatchComponent()
                .view(this)
                .build()
                .inject(this)
        return super.setupActivityComponent(component)
    }

    override fun lazyLoadData() {
        presenter?.requestData(null)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_player_match, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {
        rcv.layoutManager = LinearLayoutManager(context)
        rcv.addItemDecoration(MatchDateItemDecoration(context!!))
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

    override fun showMatchData(data: List<PlayerMatchBean>) {
        adapter.setNewData(data)
    }


}
