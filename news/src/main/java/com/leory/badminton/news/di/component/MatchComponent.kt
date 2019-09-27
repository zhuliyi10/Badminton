package com.leory.badminton.news.di.component

import com.leory.badminton.news.di.module.MatchModule
import com.leory.badminton.news.mvp.contract.MatchContract
import com.leory.badminton.news.mvp.ui.fragment.MatchFragment
import com.leory.commonlib.di.component.AppComponent
import com.leory.commonlib.di.scope.FragmentScope

import dagger.BindsInstance
import dagger.Component

/**
 * Describe : 赛事component
 * Author : leory
 * Date : 2019-05-19
 */
@FragmentScope
@Component(modules = [MatchModule::class], dependencies = [AppComponent::class])
interface MatchComponent {
    fun inject(activity: MatchFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun view(view: MatchContract.View): Builder

        fun appComponent(appComponent: AppComponent): Builder
        fun build(): MatchComponent
    }
}
