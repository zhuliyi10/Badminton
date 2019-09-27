package com.leory.badminton.news.mvp.ui.fragment

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.leory.badminton.news.R
import com.leory.badminton.news.di.component.MatchDetailComponent
import com.leory.badminton.news.mvp.contract.MatchDetailContract
import com.leory.badminton.news.mvp.model.bean.MatchDateBean
import com.leory.badminton.news.mvp.model.bean.MatchTabDateBean
import com.leory.badminton.news.mvp.presenter.MatchDatePresenter
import com.leory.badminton.news.mvp.ui.activity.HandOffRecordActivity
import com.leory.badminton.news.mvp.ui.adapter.MatchDateAdapter
import com.leory.badminton.news.mvp.ui.widget.MatchTabView
import com.leory.badminton.news.mvp.ui.widget.decoration.MatchDateItemDecoration
import com.leory.commonlib.base.BaseLazyLoadFragment
import com.leory.commonlib.base.delegate.IComponent
import kotlinx.android.synthetic.main.fragment_match_date.*
import java.io.Serializable
import java.util.*
import javax.inject.Inject

/**
 * Describe : 比赛赛程fragment
 * Author : leory
 * Date : 2019-06-06
 */
class MatchDateFragment : BaseLazyLoadFragment<MatchDatePresenter>(), MatchDetailContract.MatchDateView {

    @Inject
    lateinit var dateBeans: MutableList<MatchTabDateBean>
    lateinit var tabDate: MatchTabView
    private lateinit var dateAdapter: MatchDateAdapter
    private lateinit var txt_filter: TextView
    private lateinit var txt_state: TextView
    override fun setupActivityComponent(component: IComponent): IComponent {
        (component as MatchDetailComponent).buildMatchDateComponent()
                .view(this)
                .tabDates(arguments?.getSerializable(KEY_TAB_DATE) as MutableList<MatchTabDateBean>)
                .country(arguments?.getString(KEY_COUNTRY) ?: "")
                .build()
                .inject(this)
        return super.setupActivityComponent(component)
    }

    override fun lazyLoadData() {
        if (dateBeans != null && dateBeans.isNotEmpty()) {
            val names = ArrayList<String>()
            for (bean in dateBeans) {
                bean.name?.apply { names.add(this) }
            }
            tabDate.initData(names)
            tabDate.setOnChildClickListener(object : MatchTabView.OnChildClickListener {
                override fun onClick(tv: TextView, position: Int) {
                    tabDate.selectPos = position
                    presenter?.requestPosition(position, null)
                }
            })

            tabDate.selectPos = 0
            presenter?.requestPosition(0, null)
        }

    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_match_date, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {
        rcv.layoutManager = LinearLayoutManager(context)
        rcv.addItemDecoration(MatchDateItemDecoration(context!!))
        dateAdapter = MatchDateAdapter(ArrayList())
        rcv.adapter = dateAdapter
        dateAdapter.setOnItemClickListener { _, _, position ->
            txt_filter.text = "全部"
            txt_state.text = "场地"
            presenter?.requestPosition(tabDate.selectPos, dateAdapter.data[position].matchId)
        }
        val head = LayoutInflater.from(context).inflate(R.layout.head_match_date, null) as ConstraintLayout
        txt_filter = head.findViewById(R.id.txt_filter)
        txt_state = head.findViewById(R.id.txt_state)
        dateAdapter.addHeaderView(head)

        txt_filter.setOnClickListener {
            if (txt_filter.text.toString() == "国羽") {
                txt_filter.text = "全部"
            } else {
                txt_filter.text = "国羽"
            }
            presenter?.filter(txt_filter.text.toString(), txt_state.text.toString())
        }
        txt_state.setOnClickListener {
            if (txt_state.text.toString() == "场地") {
                txt_state.text = "时间"
            } else {
                txt_state.text = "场地"
            }
            presenter?.filter(txt_filter.text.toString(), txt_state.text.toString())
        }
        tabDate = head.findViewById(R.id.tab_date)

    }

    override fun showLoading() {
        progress.visibility = View.VISIBLE
        rcv.visibility = View.GONE
    }

    override fun hideLoading() {
        progress.visibility = View.GONE
        rcv.visibility = View.VISIBLE
    }

    override fun showMessage(message: String) {

    }

    override fun showDateData(data: List<MatchDateBean>) {
        dateAdapter.setNewData(data)
    }

    override fun toHistoryDetail(handOffUrl: String?) {
        if (!TextUtils.isEmpty(handOffUrl)) {
            HandOffRecordActivity.launch(activity!!, handOffUrl)
        }
    }

    override val filterText: String by lazy { txt_state.text.toString() }

    companion object {
        private const val KEY_TAB_DATE = "key_tab_date"
        private const val KEY_COUNTRY = "key_country"
        @JvmStatic
        fun newInstance(dateBeans: List<MatchTabDateBean>?, country: String?): MatchDateFragment {
            val fragment = MatchDateFragment()
            val args = Bundle()
            args.putString(KEY_COUNTRY, country)
            args.putSerializable(KEY_TAB_DATE, dateBeans as Serializable)
            fragment.arguments = args
            return fragment
        }
    }
}
