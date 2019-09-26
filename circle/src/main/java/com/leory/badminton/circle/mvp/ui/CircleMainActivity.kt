package com.leory.badminton.circle.mvp.ui

import android.os.Bundle

import com.leory.badminton.circle.R
import com.leory.commonlib.base.BaseActivity
import com.leory.commonlib.mvp.IPresenter

/**
 * Describe : 圈子activity
 * Author : zhuly
 * Date : 2019-05-22
 */
class CircleMainActivity : BaseActivity<IPresenter>() {

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_circle_main
    }

    override fun initData(savedInstanceState: Bundle?) {

    }
}
