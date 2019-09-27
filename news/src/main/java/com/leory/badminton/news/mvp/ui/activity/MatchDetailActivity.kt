package com.leory.badminton.news.mvp.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.TextView
import com.leory.badminton.news.R
import com.leory.badminton.news.app.listener.AppBarStateChangeListener
import com.leory.badminton.news.app.utils.FileHashUtils
import com.leory.badminton.news.di.component.DaggerMatchDetailComponent
import com.leory.badminton.news.mvp.contract.MatchDetailContract
import com.leory.badminton.news.mvp.model.bean.MatchInfoBean
import com.leory.badminton.news.mvp.presenter.MatchDetailPresenter
import com.leory.badminton.news.mvp.ui.adapter.TabPagerAdapter
import com.leory.badminton.news.mvp.ui.fragment.MatchAgainstChartFragment
import com.leory.badminton.news.mvp.ui.fragment.MatchDateFragment
import com.leory.badminton.news.mvp.ui.fragment.MatchHistoryFragment
import com.leory.badminton.news.mvp.ui.fragment.MatchPlayersFragment
import com.leory.badminton.news.mvp.ui.widget.MatchTabView
import com.leory.commonlib.base.BaseActivity
import com.leory.commonlib.base.delegate.IComponent
import com.leory.commonlib.di.component.AppComponent
import com.leory.commonlib.utils.ImageUtils
import kotlinx.android.synthetic.main.activity_match_detail1.*
import java.util.*

/**
 * Describe : 赛事详情activity
 * Author : leory
 * Date : 2019-05-23
 */
class MatchDetailActivity : BaseActivity<MatchDetailPresenter>(), MatchDetailContract.View {
    companion object {
        const val KEY_DETAIL_URL = "detail_url"
        const val KEY_MATCH_CLASSIFY = "key_match_classify"

        @JvmStatic
        fun launch(preActivity: Activity, detailUrl: String?, matchClassify: String?) {
            preActivity.startActivity(Intent(preActivity, MatchDetailActivity::class.java).putExtra(KEY_DETAIL_URL, detailUrl).putExtra(KEY_MATCH_CLASSIFY, matchClassify))
        }
    }

    override fun setupActivityComponent(component: IComponent): IComponent {
        val matchDetailComponent = DaggerMatchDetailComponent.builder()
                .view(this)
                .detailUrl(intent.getStringExtra(KEY_DETAIL_URL))
                .matchClassify(intent.getStringExtra(KEY_MATCH_CLASSIFY))
                .playerMap(FileHashUtils.playerName as HashMap<String, String>)
                .countryMap(FileHashUtils.country as HashMap<String, String>)
                .appComponent(component as AppComponent)
                .build()
        matchDetailComponent.inject(this)
        return matchDetailComponent
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_match_detail1
    }

    override fun initData(savedInstanceState: Bundle?) {


        appBarLayout.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
                when (state) {
                    State.EXPANDED -> toolbar.setNavigationIcon(R.mipmap.arrow_left_white)
                    State.COLLAPSED -> toolbar.setNavigationIcon(R.mipmap.arrow_left_gray)
                    else -> toolbar!!.setNavigationIcon(R.mipmap.arrow_left_gray)
                }
            }
        })
        toolbar.setNavigationOnClickListener { finish() }

    }


    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showMessage(message: String) {

    }


    override fun showMatchInfo(bean: MatchInfoBean) {
        progress.visibility = View.GONE
        toolbarLayout.title = bean.matchName
        txt_name.text = bean.matchName
        txt_time.text = bean.matchDate
        txt_site.text = bean.matchSite
        txt_bonus.text = bean.matchBonus

        ImageUtils.loadSvg(this, icon_match, bean.matchIcon)
        ImageUtils.loadImage(this, img_bg, bean.matchBackground)
        initViewPager(bean)

    }

    private fun initViewPager(bean: MatchInfoBean) {
        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(MatchDateFragment.newInstance(bean.tabDateHeads, bean.country))
        fragmentList.add(MatchAgainstChartFragment.newInstance())
        fragmentList.add(MatchHistoryFragment.newInstance(bean.historyUrl))
        fragmentList.add(MatchPlayersFragment.newInstance())
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {

            }

            override fun onPageSelected(i: Int) {
                tab!!.selectPos = i
            }

            override fun onPageScrollStateChanged(i: Int) {

            }
        })
        view_pager.adapter = TabPagerAdapter(supportFragmentManager, fragmentList)
        view_pager.offscreenPageLimit = 4
        val tabs = arrayOf("赛程", "对阵", "历史", "运动员")
        tab.initData(listOf(*tabs))

        tab.setOnChildClickListener(object : MatchTabView.OnChildClickListener {
            override fun onClick(tv: TextView, position: Int) {
                tab.selectPos = position
                view_pager.currentItem = position
            }
        })
        tab.selectPos = 0
    }


}

