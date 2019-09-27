package com.leory.badminton.news.di.component

import com.leory.badminton.news.di.module.HandOffRecordModule
import com.leory.badminton.news.di.module.MatchDetailModule
import com.leory.badminton.news.mvp.contract.HandOffRecordContract
import com.leory.badminton.news.mvp.contract.MatchDetailContract
import com.leory.badminton.news.mvp.model.bean.MatchTabDateBean
import com.leory.badminton.news.mvp.ui.activity.HandOffRecordActivity
import com.leory.badminton.news.mvp.ui.activity.MatchDetailActivity
import com.leory.commonlib.base.delegate.IComponent
import com.leory.commonlib.di.component.AppComponent
import com.leory.commonlib.di.scope.ActivityScope

import java.util.HashMap

import javax.inject.Named

import dagger.BindsInstance
import dagger.Component

/**
 * Describe : 交手记录component
 * Author : leory
 * Date : 2019-07-23
 */
@ActivityScope
@Component(modules = [HandOffRecordModule::class], dependencies = [AppComponent::class])
interface HandOffRecordComponent : IComponent {

    fun inject(activity: HandOffRecordActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun view(view: HandOffRecordContract.View): Builder

        @BindsInstance
        fun recordUrl(recordUrl: String): Builder

        fun appComponent(appComponent: AppComponent): Builder

        fun build(): HandOffRecordComponent
    }
}
