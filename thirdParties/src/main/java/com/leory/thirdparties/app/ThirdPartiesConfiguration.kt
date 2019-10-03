package com.leory.thirdparties.app

import android.app.Application
import android.content.Context

import com.leory.commonlib.app.ConfigModule
import com.leory.commonlib.base.delegate.AppLifecycle
import com.leory.commonlib.di.module.GlobalConfigModule

/**
 * Describe : [ThirdPartiesConfiguration] 含有有每个组件都可公用的配置信息, 每个组件的 AndroidManifest 都应该声明此 ConfigModule
 * Author : leory
 * Date : 2019-06-20
 */

class ThirdPartiesConfiguration : ConfigModule {


    override fun applyOptions(context: Context, builder: GlobalConfigModule.Builder) {}

    override fun injectAppLifecycle(context: Context, lifecycles: MutableList<AppLifecycle>) {
        lifecycles.add(AppLifecycleImpl())
    }

    override fun injectActivityLifecycle(context: Context, lifecycles: List<Application.ActivityLifecycleCallbacks>) {}

}
