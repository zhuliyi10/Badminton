package com.zhuliyi.video.di.component;

import com.zhuliyi.commonlib.di.component.AppComponent;
import com.zhuliyi.commonlib.di.scope.ActivityScope;
import com.zhuliyi.video.di.module.VideoModule;
import com.zhuliyi.video.mvp.contract.VideoListContract;
import com.zhuliyi.video.mvp.ui.VideoMainActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Describe : 视频component
 * Author : zhuly
 * Date : 2019-05-14
 */
@ActivityScope
@Component(modules = VideoModule.class,dependencies = AppComponent.class)
public interface VideoComponent {

    void inject(VideoMainActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        VideoComponent.Builder view(VideoListContract.View view);

        VideoComponent.Builder appComponent(AppComponent appComponent);

        VideoComponent build();
    }
}
