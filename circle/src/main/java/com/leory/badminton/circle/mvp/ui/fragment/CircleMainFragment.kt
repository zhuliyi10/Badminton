package com.leory.badminton.circle.mvp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.alibaba.android.arouter.facade.annotation.Route
import com.leory.badminton.circle.R
import com.leory.commonlib.base.BaseFragment
import com.leory.commonlib.mvp.IPresenter
import com.leory.interactions.RouterHub

/**
 * Describe : 圈子fragment
 * Author : zhuly
 * Date : 2019-05-22
 */

@Route(path = RouterHub.CIRCLE_CIRCLEMAINFRAGMENT)
class CircleMainFragment : BaseFragment<IPresenter>() {

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_circle_main, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {

    }
}
