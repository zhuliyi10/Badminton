package com.leory.badminton.video.mvp.ui

import android.os.Bundle

import com.leory.badminton.video.R
import com.leory.commonlib.base.BaseActivity
import com.leory.commonlib.mvp.IPresenter

/**
 * Describe : 视频主页
 * Author : zhuly
 * Date : 2018-06-14
 */


class VideoMainActivity : BaseActivity<IPresenter>() {


    override fun initView(savedInstanceState: Bundle?): Int = R.layout.activity_video_main


    override fun initData(savedInstanceState: Bundle?) {

    }

}
