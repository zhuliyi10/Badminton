package com.leory.badminton.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Describe : 首页PagerAdapter
 * Author : zhuly
 * Date : 2019-05-22
 */
class MainPagerAdapter(fm: FragmentManager, private val fragments: List<Fragment>) : FragmentPagerAdapter(fm) {

    override fun getItem(i: Int): Fragment = fragments[i]

    override fun getCount(): Int = fragments.size
}
