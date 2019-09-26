package com.leory.badminton.mine.di.module

import com.leory.badminton.mine.mvp.contract.MineContract
import com.leory.badminton.mine.mvp.model.MineModel

import dagger.Binds
import dagger.Module

/**
 * Describe : 我的Module
 * Author : leory
 * Date : 2019-06-19
 */
@Module
abstract class MineModule {
    @Binds
    internal abstract fun bindMineModel(model: MineModel): MineContract.Model
}
