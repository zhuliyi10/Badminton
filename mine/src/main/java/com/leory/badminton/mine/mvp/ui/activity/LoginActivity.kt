package com.leory.badminton.mine.mvp.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.leory.badminton.mine.R
import com.leory.badminton.mine.di.component.DaggerLoginComponent
import com.leory.badminton.mine.mvp.contract.LoginContract
import com.leory.badminton.mine.mvp.model.bean.UserInfoBean
import com.leory.badminton.mine.mvp.model.sp.AccountSp
import com.leory.badminton.mine.mvp.presenter.LoginPresenter
import com.leory.commonlib.base.BaseActivity
import com.leory.commonlib.base.delegate.IComponent
import com.leory.commonlib.di.component.AppComponent
import com.leory.commonlib.di.scope.ActivityScope
import com.leory.commonlib.utils.LogUtils
import com.leory.commonlib.utils.ToastUtils
import com.umeng.socialize.UMAuthListener
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.bean.SHARE_MEDIA
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Describe : 登陆activity
 * Author : leory
 * Date : 2019-06-19
 */
@ActivityScope
class LoginActivity : BaseActivity<LoginPresenter>(), LoginContract.View {

    override fun setupActivityComponent(component: IComponent): IComponent? {
        DaggerLoginComponent.builder().appComponent(component as AppComponent)
                .view(this)
                .build()
                .inject(this)
        return null
    }

    override fun initView(savedInstanceState: Bundle?): Int = R.layout.activity_login


    override fun initData(savedInstanceState: Bundle?) {
        toolbar.setOnBackListener { finish() }
        wechat.setOnClickListener { onViewClicked(it) }
        qq.setOnClickListener { onViewClicked(it) }
        btn_login.setOnClickListener { onViewClicked(it) }
    }

    override fun showMessage(message: String) {
        ToastUtils.showShort(message)
    }

    override fun loginSuccess(bean: UserInfoBean) {
        AccountSp.userInfoBean = bean
        AccountSp.loginState = true
        showMessage("登陆成功")
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        UMShareAPI.get(this).release()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        UMShareAPI.get(this).onSaveInstanceState(outState)
    }

    private fun onViewClicked(view: View) {
        when {
            view.id == R.id.wechat -> getThirdPartiesInfo(SHARE_MEDIA.WEIXIN)
            view.id == R.id.qq -> getThirdPartiesInfo(SHARE_MEDIA.QQ)
            view.id == R.id.btn_login -> {
                val phone = et_phone.text.toString().trim { it <= ' ' }
                val pwd = et_pwd.text.toString().trim { it <= ' ' }
                when {
                    TextUtils.isEmpty(phone) -> showMessage("请输入正常的手机号码")
                    TextUtils.isEmpty(pwd) -> showMessage("请输入密码")
                    else -> presenter!!.login(phone, pwd)
                }

            }
        }
    }

    private fun getThirdPartiesInfo(share_media: SHARE_MEDIA) {
        UMShareAPI.get(this).getPlatformInfo(this, share_media, object : UMAuthListener {
            override fun onStart(share_media: SHARE_MEDIA) {
                LogUtils.d(TAG, share_media.toString())
            }

            override fun onComplete(share_media: SHARE_MEDIA, i: Int, map: Map<String, String>) {
                LogUtils.d(TAG, map.toString())
                var bean = AccountSp.userInfoBean
                if (bean == null) {
                    bean = UserInfoBean()
                    bean.wechatUid = map["uid"]
                    bean.name = map["name"]
                    bean.gender = map["gender"]
                    bean.iconUrl = map["iconurl"]
                }
                if (share_media == SHARE_MEDIA.QQ) {
                    bean.qqUid = map["uid"]
                } else if (share_media == SHARE_MEDIA.WEIXIN) {
                    bean.wechatUid = map["uid"]
                }
                bean.shareMedia = share_media
                AccountSp.userInfoBean = bean
                AccountSp.loginState = true
                showMessage("登陆成功")
                finish()
            }

            override fun onError(share_media: SHARE_MEDIA, i: Int, throwable: Throwable) {
                LogUtils.d(TAG, throwable.toString())
            }

            override fun onCancel(share_media: SHARE_MEDIA, i: Int) {
                LogUtils.d(TAG, "取消了")
            }
        })
    }


    companion object {

        @JvmStatic
        fun launch(preActivity: Activity) {
            preActivity.startActivity(Intent(preActivity, LoginActivity::class.java))
        }
    }

}
