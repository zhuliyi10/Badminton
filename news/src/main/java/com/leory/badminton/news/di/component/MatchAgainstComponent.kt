package com.leory.badminton.news.di.component

import com.leory.badminton.news.mvp.contract.MatchDetailContract
import com.leory.badminton.news.mvp.ui.fragment.MatchAgainstChartFragment
import com.leory.commonlib.di.scope.FragmentScope

import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent

/**
 * Describe : 赛事对阵component
 * Author : leory
 * Date : 2019-05-27
 */

@Subcomponent
@FragmentScope
interface MatchAgainstComponent {
    fun inject(fragment: MatchAgainstChartFragment)

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun view(view: MatchDetailContract.MatchAgainView): Builder

        fun build(): MatchAgainstComponent
    }
}
