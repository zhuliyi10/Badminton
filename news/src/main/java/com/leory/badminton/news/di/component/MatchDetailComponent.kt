package com.leory.badminton.news.di.component

import com.leory.badminton.news.di.module.MatchDetailModule
import com.leory.badminton.news.mvp.contract.MatchDetailContract
import com.leory.badminton.news.mvp.ui.activity.MatchDetailActivity
import com.leory.commonlib.base.delegate.IComponent
import com.leory.commonlib.di.component.AppComponent
import com.leory.commonlib.di.scope.ActivityScope

import java.util.HashMap

import javax.inject.Named

import dagger.BindsInstance
import dagger.Component

/**
 * Describe : 赛事详情component
 * Author : leory
 * Date : 2019-05-27
 */
@ActivityScope
@Component(modules = [MatchDetailModule::class], dependencies = [AppComponent::class])
interface MatchDetailComponent : IComponent {

    fun buildMatchAgainstComponent(): MatchAgainstComponent.Builder

    fun buildMatchDateComponent(): MatchDateComponent.Builder

    fun buildMatchHistoryComponent(): MatchHistoryComponent.Builder
    fun buildMatchPlayersComponent(): MatchPlayersComponent.Builder
    fun inject(activity: MatchDetailActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun view(view: MatchDetailContract.View): Builder

        @BindsInstance
        fun detailUrl(@Named("detail_url") detailUrl: String): Builder

        @BindsInstance
        fun matchClassify(@Named("match_classify") matchClassify: String): Builder

        @BindsInstance
        fun playerMap(@Named("player_name") playerName: HashMap<String, String>): Builder

        @BindsInstance
        fun countryMap(@Named("country_name") countryName: HashMap<String, String>): Builder

        fun appComponent(appComponent: AppComponent): Builder

        fun build(): MatchDetailComponent
    }
}
