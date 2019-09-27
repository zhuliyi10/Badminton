package com.leory.badminton.news.mvp.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.ButterKnife
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
import java.util.*

/**
 * Describe : 交手记录
 * Author : leory
 * Date : 2019-07-23
 */
@ActivityScope
class HandOffRecordActivity : BaseActivity<HandOffRecordPresenter>(), HandOffRecordContract.View {

    lateinit var player11: View
    lateinit var player12: View
    lateinit var player21: View
    lateinit var player22: View
    lateinit var head11: ImageView
    lateinit var head12: ImageView
    lateinit var head21: ImageView
    lateinit var head22: ImageView
    lateinit var flag11: ImageView
    lateinit var flag12: ImageView
    lateinit var flag21: ImageView
    lateinit var flag22: ImageView
    lateinit var name11: TextView
    lateinit var name12: TextView
    lateinit var name21: TextView
    lateinit var name22: TextView
    lateinit var ranking1: TextView
    lateinit var ranking2: TextView
    lateinit var score1: TextView
    lateinit var score2: TextView


    lateinit var adapter: HandOffRecordAdapter

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
        adapter = HandOffRecordAdapter(ArrayList<HandOffBean.Record>())
        rcv.adapter = adapter
        val head = LayoutInflater.from(this).inflate(R.layout.head_hand_off, null)
        player11 = head.findViewById(R.id.player11)
        player12 = head.findViewById(R.id.player12)
        player21 = head.findViewById(R.id.player21)
        player22 = head.findViewById(R.id.player22)
        head11 = head.findViewById(R.id.head11)
        head12 = head.findViewById(R.id.head12)
        head21 = head.findViewById(R.id.head21)
        head22 = head.findViewById(R.id.head22)
        flag11 = head.findViewById(R.id.flag11)
        flag12 = head.findViewById(R.id.flag12)
        flag21 = head.findViewById(R.id.flag21)
        flag22 = head.findViewById(R.id.flag22)
        name11 = head.findViewById(R.id.player11_name)
        name12 = head.findViewById(R.id.player12_name)
        name21 = head.findViewById(R.id.player21_name)
        name22 = head.findViewById(R.id.player22_name)
        ranking1 = head.findViewById(R.id.player1_rank)
        ranking2 = head.findViewById(R.id.player2_rank)
        score1 = head.findViewById(R.id.score1)
        score2 = head.findViewById(R.id.score2)
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
        name11.text = bean.player1Name
        name12.text = bean.player12Name
        name21.text = bean.player2Name
        name22.text = bean.player22Name
        score1.text = bean.player1Win
        score2.text = bean.player2Win
        ranking1.text = bean.player1Ranking
        ranking2.text = bean.player2Ranking
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this)
    }

    companion object {

        private const val KEY_HAND_OFF_URL = "key_hand_off_url"

        @JvmStatic
        fun launch(preActivity: Activity, handOffUrl: String?) {
            preActivity.startActivity(Intent(preActivity, HandOffRecordActivity::class.java).putExtra(KEY_HAND_OFF_URL, handOffUrl))
        }
    }
}
