package com.leory.badminton.news.di.component;

import com.leory.badminton.news.di.module.RankingModule;
import com.leory.badminton.news.mvp.contract.RankingContract;
import com.leory.badminton.news.mvp.ui.fragment.RankingFragment;
import com.leory.commonlib.di.component.AppComponent;
import com.leory.commonlib.di.scope.FragmentScope;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Describe : 排名component
 * Author : leory
 * Date : 2019-05-21
 */
@FragmentScope
@Component(modules = RankingModule.class, dependencies = AppComponent.class)
public interface RankingComponent {
    void inject(RankingFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        RankingComponent.Builder view(RankingContract.View view);

        RankingComponent.Builder appComponent(AppComponent appComponent);

        RankingComponent build();
    }
}
