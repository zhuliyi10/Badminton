package com.leory.badminton.news.di.module

import com.leory.badminton.news.di.component.PlayerMatchComponent
import com.leory.badminton.news.mvp.contract.PlayerContract
import com.leory.badminton.news.mvp.model.PlayerModel

import dagger.Binds
import dagger.Module

/**
 * Describe : 运动员module
 * Author : leory
 * Date : 2019-06-11
 */
@Module(subcomponents = [PlayerMatchComponent::class])
abstract class PlayerModule {
    @Binds
    internal abstract fun bindPlayerModel(model: PlayerModel): PlayerContract.Model
}
