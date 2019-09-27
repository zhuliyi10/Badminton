package com.leory.badminton.news.mvp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leory.badminton.news.R
import com.leory.badminton.news.mvp.model.bean.PlayerInfoBean
import com.leory.commonlib.base.BaseLazyLoadFragment
import com.leory.commonlib.di.scope.FragmentScope
import com.leory.commonlib.mvp.IPresenter
import kotlinx.android.synthetic.main.fragment_player_info.*

/**
 * Describe : 运动员个人资料
 * Author : leory
 * Date : 2019-06-11
 */
@FragmentScope
class PlayerInfoFragment : BaseLazyLoadFragment<IPresenter>() {

    override fun lazyLoadData() {
        val infoBean = arguments?.let {
            it.getSerializable(KEY_PLAYER_INFO)?.let { it -> it as PlayerInfoBean } ?: null
        }
        if (infoBean != null) {
            txt_stats.text = infoBean.stats
        }
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_player_info, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    companion object {
        private val KEY_PLAYER_INFO = "key_player_info"
        @JvmStatic
        fun newInstance(bean: PlayerInfoBean?): PlayerInfoFragment {
            val fragment = PlayerInfoFragment()
            val args = Bundle()
            args.putSerializable(KEY_PLAYER_INFO, bean)
            fragment.arguments = args
            return fragment
        }
    }
}
