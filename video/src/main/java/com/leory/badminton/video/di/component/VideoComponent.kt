package com.leory.badminton.video.di.component

import com.leory.badminton.video.di.module.VideoModule
import com.leory.badminton.video.mvp.contract.VideoListContract
import com.leory.badminton.video.mvp.ui.fragment.VideoMainFragment
import com.leory.commonlib.base.delegate.IComponent
import com.leory.commonlib.di.component.AppComponent
import com.leory.commonlib.di.scope.ActivityScope
import dagger.BindsInstance
import dagger.Component

/**
 * Describe : 视频component
 * Author : zhuly
 * Date : 2019-05-14
 */
@ActivityScope
@Component(modules = [VideoModule::class], dependencies = [AppComponent::class])
interface VideoComponent : IComponent {

    fun inject(activity: VideoMainFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun view(view: VideoListContract.View): Builder

        fun appComponent(appComponent: AppComponent): Builder

        fun build(): VideoComponent
    }
}
