package com.leory.badminton.news.di.module

import com.leory.badminton.news.di.component.MatchAgainstComponent
import com.leory.badminton.news.di.component.MatchDateComponent
import com.leory.badminton.news.di.component.MatchHistoryComponent
import com.leory.badminton.news.di.component.MatchPlayersComponent
import com.leory.badminton.news.mvp.contract.MatchDetailContract
import com.leory.badminton.news.mvp.model.MatchDetailModel

import dagger.Binds
import dagger.Module

/**
 * Describe : 赛事module
 * Author : leory
 * Date : 2019-05-19
 */
@Module(subcomponents = [MatchAgainstComponent::class, MatchDateComponent::class, MatchHistoryComponent::class, MatchPlayersComponent::class])
abstract class MatchDetailModule {
    @Binds
    internal abstract fun bindMatchModel(model: MatchDetailModel): MatchDetailContract.Model

}
