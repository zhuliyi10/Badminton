package com.leory.commonlib.di.component;

import android.app.Application;

import com.leory.commonlib.base.delegate.ActivityLifecycleImpl;
import com.leory.commonlib.base.delegate.AppDelegate;
import com.leory.commonlib.base.delegate.IComponent;
import com.leory.commonlib.di.module.AppModule;
import com.leory.commonlib.di.module.ClientModule;
import com.leory.commonlib.di.module.GlobalConfigModule;
import com.leory.commonlib.http.IRepositoryManager;
import com.leory.commonlib.image.ImageLoader;

import java.io.File;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * Describe : 可通过  拿到此接口的实现类
 * 拥有此接口的实现类即可调用对应的方法拿到 Dagger 提供的对应实例
 * Author : zhuly
 * Date : 2018-06-12
 */
@Singleton
@Component(modules = {AppModule.class, ClientModule.class, GlobalConfigModule.class})
public interface AppComponent extends IComponent {

    //图片管理器,用于加载图片的管理类,默认使用 Glide ,使用策略模式,可在运行时替换框架
    ImageLoader imageLoader();

    /**
     * 用于管理网络请求层, 以及数据缓存层
     *
     * @return {@link IRepositoryManager}
     */
    IRepositoryManager repositoryManager();

    //缓存文件根目录(RxCache 和 Glide 的缓存都已经作为子文件夹放在这个根目录下),应该将所有缓存都放到这个根目录下,便于管理和清理,可在 GlobalConfigModule 里配置
    File cacheFile();

    OkHttpClient okHttpClient();

    /**
     * 注入到 app代理
     *
     * @param delegate
     */
    void inject(AppDelegate delegate);

    /**
     * 注入到 Activity实现类
     *
     * @param activityLifecycle
     */
    void inject(ActivityLifecycleImpl activityLifecycle);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        Builder globalConfigModule(GlobalConfigModule globalConfigModule);

        AppComponent build();
    }
}
