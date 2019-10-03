package com.leory.thirdparties.app

import android.app.Application
import android.content.Context
import android.util.Log

import com.leory.commonlib.base.delegate.AppLifecycle
import com.umeng.commonsdk.UMConfigure
import com.umeng.socialize.PlatformConfig

/**
 * Describe : Application生命周期的回调，在这里实现Module通用的生命周期
 * Author : zhuly
 * Date : 2018-06-12
 */

class AppLifecycleImpl : AppLifecycle {

    override fun attachBaseContext(base: Context) {
        Log.d(TAG, "attachBaseContext: ")
    }

    override fun onCreate(application: Application) {
        Log.d(TAG, "onCreate: ")
        UMConfigure.init(application, "5d0ae57e3fc195fa83000f2b", "umeng", UMConfigure.DEVICE_TYPE_PHONE, "")
        PlatformConfig.setWeixin("wx6e9c7fbf9cd75e57", "2385868a2cefcea9ba0bae50f61af5fd")
        PlatformConfig.setQQZone("1106244817", "KEYXcAAbcMOAWOooRmh")
    }

    override fun onTerminate(application: Application) {
        Log.d(TAG, "onTerminate: ")
    }

    companion object {
        private val TAG = AppLifecycleImpl::class.java.simpleName
    }
}
