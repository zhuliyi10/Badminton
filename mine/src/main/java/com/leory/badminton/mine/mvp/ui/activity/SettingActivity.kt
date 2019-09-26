package com.leory.badminton.mine.mvp.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.leory.badminton.mine.R
import com.leory.badminton.mine.mvp.model.sp.AccountSp
import com.leory.commonlib.base.BaseActivity
import com.leory.commonlib.mvp.IPresenter
import kotlinx.android.synthetic.main.activity_setting.*

/**
 * Describe : 设置页面
 * Author : leory
 * Date : 2019-06-20
 */
class SettingActivity : BaseActivity<IPresenter>() {

    override fun initView(savedInstanceState: Bundle?): Int = R.layout.activity_setting


    override fun initData(savedInstanceState: Bundle?) {
        toolbar.setOnBackListener { finish() }
        btn_logout.setOnClickListener { onViewClicked(it) }
    }


    private fun onViewClicked(view: View) {
        if (view.id == R.id.btn_logout) {
            AccountSp.loginState = false
            finish()
        }
    }

    companion object {

        @JvmStatic
        fun launch(preActivity: Activity) {
            preActivity.startActivity(Intent(preActivity, SettingActivity::class.java))
        }
    }
}
