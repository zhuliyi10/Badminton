package com.leory.thirdparties.app;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.leory.commonlib.base.delegate.AppLifecycle;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

/**
 * Describe : Application生命周期的回调，在这里实现Module通用的生命周期
 * Author : zhuly
 * Date : 2018-06-12
 */

public class AppLifecycleImpl implements AppLifecycle {
    private static final String TAG = AppLifecycleImpl.class.getSimpleName();

    @Override
    public void attachBaseContext(@NonNull Context base) {
        Log.d(TAG, "attachBaseContext: ");
    }

    @Override
    public void onCreate(@NonNull Application application) {
        Log.d(TAG, "onCreate: ");
        UMConfigure.init(application,"5d0ae57e3fc195fa83000f2b"
                ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
        PlatformConfig.setWeixin("wx6e9c7fbf9cd75e57", "2385868a2cefcea9ba0bae50f61af5fd");
        PlatformConfig.setQQZone("1106244817", "KEYXcAAbcMOAWOooRmh");
    }

    @Override
    public void onTerminate(@NonNull Application application) {
        Log.d(TAG, "onTerminate: ");
    }
}
