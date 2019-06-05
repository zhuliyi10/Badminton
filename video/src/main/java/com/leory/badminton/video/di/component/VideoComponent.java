package com.leory.badminton.video.di.component;

import com.leory.badminton.video.mvp.ui.fragment.VideoMainFragment;
import com.leory.commonlib.base.delegate.IComponent;
import com.leory.commonlib.di.component.AppComponent;
import com.leory.commonlib.di.scope.ActivityScope;
import com.leory.badminton.video.di.module.VideoModule;
import com.leory.badminton.video.mvp.contract.VideoListContract;
import com.leory.badminton.video.mvp.ui.VideoMainActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Describe : 视频component
 * Author : zhuly
 * Date : 2019-05-14
 */
@ActivityScope
@Component(modules = VideoModule.class,dependencies = AppComponent.class)
public interface VideoComponent extends IComponent {

    void inject(VideoMainFragment activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        VideoComponent.Builder view(VideoListContract.View view);

        VideoComponent.Builder appComponent(AppComponent appComponent);

        VideoComponent build();
    }
}
