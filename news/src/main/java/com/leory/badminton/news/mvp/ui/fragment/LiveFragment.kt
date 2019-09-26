package com.leory.badminton.news.mvp.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leory.badminton.news.R
import com.leory.badminton.news.di.component.DaggerLiveComponent
import com.leory.badminton.news.mvp.contract.LiveContract
import com.leory.badminton.news.mvp.model.bean.LiveBean
import com.leory.badminton.news.mvp.model.bean.LiveDetailBean
import com.leory.badminton.news.mvp.presenter.LivePresenter
import com.leory.badminton.news.mvp.ui.activity.MatchDetailActivity
import com.leory.badminton.news.mvp.ui.adapter.LiveDetailAdapter
import com.leory.commonlib.base.BaseLazyLoadFragment
import com.leory.commonlib.base.delegate.IComponent
import com.leory.commonlib.di.component.AppComponent
import com.leory.commonlib.di.scope.FragmentScope
import com.leory.commonlib.utils.ImageUtils
import com.leory.commonlib.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment_live.*
import java.util.*

/**
 * Describe :直播fragment
 * Author : leory
 * Date : 2019-05-19
 */
@FragmentScope
class LiveFragment : BaseLazyLoadFragment<LivePresenter>(), LiveContract.View {


    private lateinit var liveDetailAdapter: LiveDetailAdapter


    override fun setupActivityComponent(component: IComponent): IComponent {
        DaggerLiveComponent.builder()
                .view(this)
                .appComponent(component as AppComponent)
                .build()
                .inject(this)
        return super.setupActivityComponent(component)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_live, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {
        rcv.layoutManager = LinearLayoutManager(context)
        liveDetailAdapter = LiveDetailAdapter(ArrayList())
        rcv.adapter = liveDetailAdapter
    }

    override fun lazyLoadData() {
        presenter?.requestData()
    }

    override fun showLoading() {
        progress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress.visibility = View.GONE
    }

    override fun showMessage(message: String) {
        ToastUtils.showShort(message)
    }

    override fun showLiveData(bean: LiveBean) {
        match_name.text = bean.matchName
        match_date.text = bean.matchDate
        txt_city.text = bean.city
        txt_country.text = bean.country
        ImageUtils.loadImage(context, image_flag, bean.countryFlag)
        ImageUtils.loadSvg(context, match_icon, bean.matchIcon)
        item_live.setOnClickListener { MatchDetailActivity.launch(activity!!, bean.detailUrl, "") }
    }

    override fun showLiveDetail(data: List<LiveDetailBean>) {
        txt_next_live.text = "直播中"
        liveDetailAdapter.setNewData(data)
    }

}
