package com.leory.badminton.news.mvp.ui.activity

import android.os.Bundle
import com.leory.badminton.news.R
import com.leory.commonlib.base.BaseActivity
import com.leory.commonlib.mvp.IPresenter

/**
 * Describe : 赛事首页activity
 * Author : leory
 * Date : 2019-05-23
 */
class NewsMainActivity : BaseActivity<IPresenter>() {


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_news_main
    }

    override fun initData(savedInstanceState: Bundle?) {

    }


}
