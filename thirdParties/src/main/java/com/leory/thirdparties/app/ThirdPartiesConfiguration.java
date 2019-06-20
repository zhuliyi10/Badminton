package com.leory.thirdparties.app;

import android.app.Application;
import android.content.Context;

import com.leory.commonlib.app.ConfigModule;
import com.leory.commonlib.base.delegate.AppLifecycle;
import com.leory.commonlib.di.module.GlobalConfigModule;

import java.util.List;

/**
 * Describe : {@link ThirdPartiesConfiguration} 含有有每个组件都可公用的配置信息, 每个组件的 AndroidManifest 都应该声明此 ConfigModule
 * Author : leory
 * Date : 2019-06-20
 */

public class ThirdPartiesConfiguration implements ConfigModule {


    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {
    }

    @Override
    public void injectAppLifecycle(Context context, List<AppLifecycle> lifecycles) {
        lifecycles.add(new AppLifecycleImpl());
    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles) {
    }

}
