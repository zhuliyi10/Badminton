package com.leory.badminton.news.di.component;

import com.leory.badminton.news.di.module.MatchModule;
import com.leory.badminton.news.mvp.contract.MatchContract;
import com.leory.badminton.news.mvp.ui.fragment.MatchFragment;
import com.leory.commonlib.di.component.AppComponent;
import com.leory.commonlib.di.scope.FragmentScope;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Describe : 赛事component
 * Author : leory
 * Date : 2019-05-19
 */
@FragmentScope
@Component(modules = MatchModule.class, dependencies = AppComponent.class)
public interface MatchComponent {
    void inject(MatchFragment activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MatchComponent.Builder view(MatchContract.View view);

        MatchComponent.Builder appComponent(AppComponent appComponent);
        MatchComponent build();
    }
}
