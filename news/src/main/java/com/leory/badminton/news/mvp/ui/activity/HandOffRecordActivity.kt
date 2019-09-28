package com.leory.badminton.news.mvp.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import com.leory.badminton.news.R
import com.leory.badminton.news.di.component.DaggerHandOffRecordComponent
import com.leory.badminton.news.mvp.contract.HandOffRecordContract
import com.leory.badminton.news.mvp.model.bean.HandOffBean
import com.leory.badminton.news.mvp.presenter.HandOffRecordPresenter
import com.leory.badminton.news.mvp.ui.adapter.HandOffRecordAdapter
import com.leory.commonlib.base.BaseActivity
import com.leory.commonlib.base.delegate.IComponent
import com.leory.commonlib.di.component.AppComponent
import com.leory.commonlib.di.scope.ActivityScope
import com.leory.commonlib.utils.ImageUtils
import com.leory.commonlib.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_hand_off.*
import kotlinx.android.synthetic.main.head_hand_off.*
import java.util.*

/**
 * Describe : 交手记录
 * Author : leory
 * Date : 2019-07-23
 */
@ActivityScope
class HandOffRecordActivity : BaseActivity<HandOffRecordPresenter>(), HandOffRecordContract.View {

    companion object {

        private const val KEY_HAND_OFF_URL = "key_hand_off_url"

        @JvmStatic
        fun launch(preActivity: Activity, handOffUrl: String?) {
            preActivity.startActivity(Intent(preActivity, HandOffRecordActivity::class.java).putExtra(KEY_HAND_OFF_URL, handOffUrl))
        }
    }

    val adapter: HandOffRecordAdapter by lazy { HandOffRecordAdapter(ArrayList()) }

    override fun setupActivityComponent(component: IComponent): IComponent? {
        DaggerHandOffRecordComponent.builder()
                .view(this)
                .recordUrl(intent.getStringExtra(KEY_HAND_OFF_URL))
                .appComponent(component as AppComponent)
                .build()
                .inject(this)
        return null
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_hand_off
    }

    override fun initData(savedInstanceState: Bundle?) {
        toolbar.setOnBackListener { finish() }
        rcv.layoutManager = LinearLayoutManager(this)
        rcv.adapter = adapter
        val head = LayoutInflater.from(this).inflate(R.layout.head_hand_off, null)
        adapter.addHeaderView(head)
    }

    override fun showMessage(message: String) {
        ToastUtils.showShort(message)
    }

    override fun showLoading() {
        loading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loading.visibility = View.GONE
    }

    override fun showHandOffView(bean: HandOffBean) {
        if (TextUtils.isEmpty(bean.player12Name)) {
            player12.visibility = View.GONE
        }
        if (TextUtils.isEmpty(bean.player22Name)) {
            player22.visibility = View.GONE
        }
        player11_name.text = bean.player1Name
        player12_name.text = bean.player12Name
        player21_name.text = bean.player2Name
        player22_name.text = bean.player22Name
        score1.text = bean.player1Win
        score2.text = bean.player2Win
        player1_rank.text = bean.player1Ranking
        player2_rank.text = bean.player2Ranking
        ImageUtils.loadImage(this, head11, bean.player1HeadUrl)
        ImageUtils.loadImage(this, head12, bean.player12HeadUrl)
        ImageUtils.loadImage(this, head21, bean.player2HeadUrl)
        ImageUtils.loadImage(this, head22, bean.player22HeadUrl)
        ImageUtils.loadImage(this, flag11, bean.player1Flag)
        ImageUtils.loadImage(this, flag12, bean.player12Flag)
        ImageUtils.loadImage(this, flag21, bean.player2Flag)
        ImageUtils.loadImage(this, flag22, bean.player22Flag)
        adapter.setNewData(bean.recordList)

    }


}
