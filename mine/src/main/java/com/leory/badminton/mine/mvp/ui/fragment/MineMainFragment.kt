package com.leory.badminton.mine.mvp.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.leory.badminton.mine.R
import com.leory.badminton.mine.di.component.DaggerMineComponent
import com.leory.badminton.mine.mvp.contract.MineContract
import com.leory.badminton.mine.mvp.model.bean.UserInfoBean
import com.leory.badminton.mine.mvp.presenter.MinePresenter
import com.leory.badminton.mine.mvp.ui.activity.LoginActivity
import com.leory.badminton.mine.mvp.ui.activity.SettingActivity
import com.leory.commonlib.base.BaseFragment
import com.leory.commonlib.base.delegate.IComponent
import com.leory.commonlib.di.component.AppComponent
import com.leory.commonlib.utils.ImageUtils
import com.leory.interactions.RouterHub
import kotlinx.android.synthetic.main.fragment_mine_main.*

/**
 * Describe : 圈子fragment
 * Author : zhuly
 * Date : 2019-05-22
 */

@Route(path = RouterHub.MINE_MINEMAINFRAGMENT)
class MineMainFragment : BaseFragment<MinePresenter>(), MineContract.View {

    override fun setupActivityComponent(component: IComponent): IComponent {
        DaggerMineComponent.builder()
                .appComponent(component as AppComponent)
                .view(this)
                .build()
                .inject(this)
        return super.setupActivityComponent(component)
    }

    override fun onResume() {
        super.onResume()
        presenter!!.updateLoginState()
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_mine_main, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {
        btn_login.setOnClickListener { onViewClicked(it) }
        setting.setOnClickListener { onViewClicked(it) }
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showMessage(message: String) {

    }

    override fun showLoginState(isLogin: Boolean, bean: UserInfoBean?) {
        info.visibility = if (isLogin) View.VISIBLE else View.GONE
        login.visibility = if (isLogin) View.GONE else View.VISIBLE
        if (isLogin && bean != null) {
            ImageUtils.loadImage(context, img_head, bean.iconUrl)
            txt_name.text = bean.name
            txt_desc.text = if (TextUtils.isEmpty(bean.desc)) "这个羽毛球爱好者很懒，什么都没留下..." else bean.desc
            if (TextUtils.isEmpty(bean.phone)) {
                txt_account.text = "绑定手机号"
            } else {
                txt_account.text = "账号：" + bean.phone
            }
        }
    }


    fun onViewClicked(view: View) {
        if (view.id == R.id.btn_login) {
            LoginActivity.launch(activity!!)
        } else if (view.id == R.id.setting) {
            SettingActivity.launch(activity!!)
        }
    }

}
