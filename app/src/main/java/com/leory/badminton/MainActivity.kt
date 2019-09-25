package com.leory.badminton

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.leory.badminton.adapter.MainPagerAdapter
import com.leory.badminton.utils.FragmentUtils
import com.leory.badminton.widget.BottomItemView
import com.leory.commonlib.base.BaseActivity
import com.leory.commonlib.mvp.IPresenter
import com.leory.commonlib.utils.StatusBarUtils
import com.leory.interactions.RouterHub
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

@Route(path = RouterHub.APP_MAINACTIVITY)
class MainActivity : BaseActivity<IPresenter>() {


    override fun initView(savedInstanceState: Bundle?) = R.layout.activity_main

    override fun initData(savedInstanceState: Bundle?) {
        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(FragmentUtils.newsFragment)
        fragmentList.add(FragmentUtils.videoFragment)
        fragmentList.add(FragmentUtils.circleFragment)
        fragmentList.add(FragmentUtils.mineFragment)
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {

            }

            override fun onPageSelected(i: Int) {
                if (i == 0) {
                    StatusBarUtils.setDarkStatusBar(this@MainActivity, true, R.color.colorPrimary)
                } else {
                    StatusBarUtils.setDarkStatusBar(this@MainActivity, false, 0)
                }
            }

            override fun onPageScrollStateChanged(i: Int) {

            }
        })
        view_pager.adapter = MainPagerAdapter(supportFragmentManager, fragmentList)
        view_pager.offscreenPageLimit = 4
        item_match.setOnClickListener { onItemClick(item_match) }
        item_video.setOnClickListener { onItemClick(item_video) }
        item_circle.setOnClickListener { onItemClick(item_circle) }
        item_mine.setOnClickListener { onItemClick(item_mine) }
    }

    private fun onItemClick(view: BottomItemView) {
        if (view.isActivate) return
        item_match.isActivate = false
        item_video.isActivate = false
        item_circle.isActivate = false
        item_mine.isActivate = false
        view.isActivate = true
        when (view) {
            item_match -> view_pager.currentItem = 0
            item_video -> view_pager.currentItem = 1
            item_circle -> view_pager.currentItem = 2
            item_mine -> view_pager.currentItem = 3
        }
    }
}
