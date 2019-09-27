package com.leory.badminton.news.di.component

import com.leory.badminton.news.di.module.PlayerModule
import com.leory.badminton.news.mvp.contract.PlayerContract
import com.leory.badminton.news.mvp.ui.activity.PlayerDetailActivity
import com.leory.commonlib.base.delegate.IComponent
import com.leory.commonlib.di.component.AppComponent
import com.leory.commonlib.di.scope.ActivityScope

import javax.inject.Named

import dagger.BindsInstance
import dagger.Component

/**
 * Describe : 运动员详情component
 * Author : leory
 * Date : 2019-06-11
 */
@ActivityScope
@Component(modules = [PlayerModule::class], dependencies = [AppComponent::class])
interface PlayerDetailComponent : IComponent {
    fun inject(activity: PlayerDetailActivity)
    fun buildPlayerMatchComponent(): PlayerMatchComponent.Builder
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun view(view: PlayerContract.View): Builder

        @BindsInstance
        fun playerUrl(@Named("player_url") playerUrl: String): Builder

        fun appComponent(appComponent: AppComponent): Builder

        fun build(): PlayerDetailComponent
    }
}
