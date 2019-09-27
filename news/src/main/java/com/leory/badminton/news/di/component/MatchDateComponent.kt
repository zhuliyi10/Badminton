package com.leory.badminton.news.di.component

import com.leory.badminton.news.mvp.contract.MatchDetailContract
import com.leory.badminton.news.mvp.model.bean.MatchTabDateBean
import com.leory.badminton.news.mvp.ui.fragment.MatchDateFragment
import com.leory.commonlib.di.scope.FragmentScope
import dagger.BindsInstance
import dagger.Subcomponent
import javax.inject.Named

/**
 * Describe : 赛程component
 * Author : leory
 * Date : 2019-06-06
 */
@Subcomponent
@FragmentScope
interface MatchDateComponent {
    fun inject(fragment: MatchDateFragment)

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun view(view: MatchDetailContract.MatchDateView): Builder

        @BindsInstance
        fun tabDates(tabDates: MutableList<MatchTabDateBean>): Builder

        @BindsInstance
        fun country(@Named("country") country: String): Builder

        fun build(): MatchDateComponent
    }
}
