package com.leory.badminton.news.mvp.ui.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.leory.badminton.news.R
import com.leory.badminton.news.mvp.ui.adapter.IndicatorPagerAdapter
import com.leory.commonlib.base.BaseFragment
import com.leory.commonlib.mvp.IPresenter
import com.leory.commonlib.utils.StatusBarUtils
import com.leory.interactions.RouterHub
import kotlinx.android.synthetic.main.fragment_news_main.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView
import java.util.*

/**
 * Describe : 资讯首页fragment
 * Author : zhuly
 * Date : 2019-05-22
 */
@Route(path = RouterHub.NEWS_NEWMAINFRAGMENT)
class NewMainFragment : BaseFragment<IPresenter>() {
    companion object {
        private val CHANNELS = arrayOf("直播", "赛事", "排名", "球星")
    }

    private val fragmentList = ArrayList<Fragment>()
    private val dataList = listOf(*CHANNELS)
    private var indicatorPagerAdapter: IndicatorPagerAdapter? = null


    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_news_main, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {
        StatusBarUtils.setDarkStatusBar(activity, true, R.color.colorPrimary)//进来没有执行onPageSelected回调，这里要设置一下
        fragmentList.add(LiveFragment())
        fragmentList.add(RankingFragment())
        fragmentList.add(RankingFragment())
        fragmentList.add(RankingFragment())
        indicatorPagerAdapter = IndicatorPagerAdapter(childFragmentManager, fragmentList)
        view_pager.adapter = indicatorPagerAdapter
        view_pager.offscreenPageLimit = 4
        initIndicator()
    }


    private fun initIndicator() {
        indicator!!.setBackgroundResource(R.color.colorPrimary)
        val commonNavigator = CommonNavigator(activity)
        commonNavigator.isSkimOver = true
        commonNavigator.adapter = object : CommonNavigatorAdapter() {

            override fun getCount(): Int {
                return dataList?.size ?: 0
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val clipPagerTitleView = ClipPagerTitleView(context)
                clipPagerTitleView.text = dataList[index]
                clipPagerTitleView.textColor = Color.parseColor("#f2c4c4")
                clipPagerTitleView.clipColor = Color.WHITE
                clipPagerTitleView.setOnClickListener { view_pager.currentItem = index }
                return clipPagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                return null
            }
        }
        indicator!!.navigator = commonNavigator
        ViewPagerHelper.bind(indicator, view_pager)
    }


}
