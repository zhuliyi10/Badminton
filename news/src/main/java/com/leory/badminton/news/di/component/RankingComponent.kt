package com.leory.badminton.news.di.component

import com.leory.badminton.news.di.module.RankingModule
import com.leory.badminton.news.mvp.contract.RankingContract
import com.leory.badminton.news.mvp.ui.fragment.RankingFragment
import com.leory.commonlib.di.component.AppComponent
import com.leory.commonlib.di.scope.FragmentScope

import dagger.BindsInstance
import dagger.Component

/**
 * Describe : 排名component
 * Author : leory
 * Date : 2019-05-21
 */
@FragmentScope
@Component(modules = [RankingModule::class], dependencies = [AppComponent::class])
interface RankingComponent {
    fun inject(fragment: RankingFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun view(view: RankingContract.View): Builder

        fun appComponent(appComponent: AppComponent): Builder

        fun build(): RankingComponent
    }
}
