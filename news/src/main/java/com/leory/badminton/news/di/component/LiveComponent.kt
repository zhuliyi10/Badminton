package com.leory.badminton.news.di.component

import com.leory.badminton.news.di.module.LiveModule
import com.leory.badminton.news.di.module.MatchModule
import com.leory.badminton.news.mvp.contract.LiveContract
import com.leory.badminton.news.mvp.contract.MatchContract
import com.leory.badminton.news.mvp.ui.fragment.LiveFragment
import com.leory.badminton.news.mvp.ui.fragment.MatchFragment
import com.leory.commonlib.di.component.AppComponent
import com.leory.commonlib.di.scope.FragmentScope

import dagger.BindsInstance
import dagger.Component

/**
 * Describe : 直播component
 * Author : leory
 * Date : 2019-06-03
 */
@FragmentScope
@Component(modules = [LiveModule::class], dependencies = [AppComponent::class])
interface LiveComponent {
    fun inject(fragment: LiveFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun view(view: LiveContract.View): Builder

        fun appComponent(appComponent: AppComponent): Builder

        fun build(): LiveComponent
    }
}
