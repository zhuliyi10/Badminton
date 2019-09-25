package com.leory.badminton.mine.mvp.ui.activity

import android.os.Bundle
import com.leory.badminton.mine.R
import com.leory.commonlib.base.BaseActivity
import com.leory.commonlib.mvp.IPresenter

/**
 * Describe : 圈子activity
 * Author : zhuly
 * Date : 2019-05-22
 */
class MineMainActivity : BaseActivity<IPresenter>() {


    override fun initView(savedInstanceState: Bundle?): Int = R.layout.activity_mine_main


    override fun initData(savedInstanceState: Bundle?) {

    }
}
