package com.leory.badminton.news.di.component

import com.leory.badminton.news.mvp.contract.MatchDetailContract
import com.leory.badminton.news.mvp.ui.fragment.MatchHistoryFragment
import com.leory.commonlib.di.scope.FragmentScope

import javax.inject.Named

import dagger.BindsInstance
import dagger.Subcomponent

/**
 * Describe : 历史赛事component
 * Author : leory
 * Date : 2019-06-07
 */

@Subcomponent
@FragmentScope
interface MatchHistoryComponent {
    fun inject(fragment: MatchHistoryFragment)

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun view(view: MatchDetailContract.MatchHistory): Builder

        @BindsInstance
        fun historyUrl(@Named("history_url") historyUrl: String): Builder

        fun build(): MatchHistoryComponent
    }
}
