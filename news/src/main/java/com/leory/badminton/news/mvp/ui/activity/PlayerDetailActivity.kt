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
import com.leory.badminton.news.di.component.DaggerPlayerDetailComponent
import com.leory.badminton.news.mvp.contract.PlayerContract
import com.leory.badminton.news.mvp.model.bean.PlayerDetailBean
import com.leory.badminton.news.mvp.model.bean.PlayerInfoBean
import com.leory.badminton.news.mvp.presenter.PlayerDetailPresenter
import com.leory.badminton.news.mvp.ui.adapter.TabPagerAdapter
import com.leory.badminton.news.mvp.ui.fragment.PlayerInfoFragment
import com.leory.badminton.news.mvp.ui.fragment.PlayerMatchFragment
import com.leory.badminton.news.mvp.ui.widget.MatchTabView
import com.leory.commonlib.base.BaseActivity
import com.leory.commonlib.base.delegate.IComponent
import com.leory.commonlib.di.component.AppComponent
import com.leory.commonlib.di.scope.ActivityScope
import com.leory.commonlib.utils.ImageUtils
import kotlinx.android.synthetic.main.activity_player_detail.*
import java.util.*

/**
 * Describe : 运动员详情
 * Author : leory
 * Date : 2019-06-10
 */
@ActivityScope
class PlayerDetailActivity : BaseActivity<PlayerDetailPresenter>(), PlayerContract.View {
    companion object {
        private val KEY_PLAYER_URL = "key_player_url"
        @JvmStatic
        fun launch(preActivity: Activity, url: String?) {
            preActivity.startActivity(Intent(preActivity, PlayerDetailActivity::class.java).putExtra(KEY_PLAYER_URL, url))
        }
    }

    override fun setupActivityComponent(component: IComponent): IComponent {
        val playerDetailComponent = DaggerPlayerDetailComponent.builder()
                .appComponent(component as AppComponent)
                .view(this)
                .playerUrl(intent.getStringExtra(KEY_PLAYER_URL))
                .build()
        playerDetailComponent.inject(this)
        return playerDetailComponent
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_player_detail
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
        progress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress.visibility = View.GONE
    }

    override fun showMessage(message: String) {

    }

    override fun showPlayerDetail(bean: PlayerDetailBean) {
        ImageUtils.loadImage(this, img_head, bean.head)
        ImageUtils.loadImage(this, img_flag, bean.flag)
        toolbarLayout!!.title = bean.name
        ranking_num.text = bean.rankNum
        match_type.text = bean.type
        txt_wins.text = bean.winNum
        txt_age.text = bean.age
        if (bean.rankNum2 != null) {
            ranking2.visibility = View.VISIBLE
            ranking_num2.text = bean.rankNum2
            match_type2.text = bean.type2
        } else {
            ranking2.visibility = View.GONE
        }
        initViewPager(bean.infoBean)
    }

    private fun initViewPager(infoBean: PlayerInfoBean?) {
        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(PlayerInfoFragment.newInstance(infoBean))
        fragmentList.add(PlayerMatchFragment.newInstance())
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {

            }

            override fun onPageSelected(i: Int) {
                tab.selectPos = i
            }

            override fun onPageScrollStateChanged(i: Int) {

            }
        })
        view_pager.adapter = TabPagerAdapter(supportFragmentManager, fragmentList)
        view_pager.offscreenPageLimit = 2
        val tabs = arrayOf("个人资料", "赛果", "历史排名")
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
