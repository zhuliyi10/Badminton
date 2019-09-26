package com.leory.badminton.mine.di.component

import com.leory.badminton.mine.di.module.MineModule
import com.leory.badminton.mine.mvp.contract.MineContract
import com.leory.badminton.mine.mvp.ui.fragment.MineMainFragment
import com.leory.commonlib.base.delegate.IComponent
import com.leory.commonlib.di.component.AppComponent
import com.leory.commonlib.di.scope.ActivityScope

import dagger.BindsInstance
import dagger.Component

/**
 * Describe : 我的component
 * Author : leory
 * Date : 2019-06-19
 */
@ActivityScope
@Component(modules = [MineModule::class], dependencies = [AppComponent::class])
interface MineComponent : IComponent {
    fun inject(fragment: MineMainFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun view(view: MineContract.View): Builder

        fun appComponent(appComponent: AppComponent): Builder

        fun build(): MineComponent
    }
}
