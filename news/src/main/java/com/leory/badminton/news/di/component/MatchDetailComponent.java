package com.leory.badminton.news.di.component;

import com.leory.badminton.news.di.module.MatchDetailModule;
import com.leory.badminton.news.mvp.contract.MatchDetailContract;
import com.leory.badminton.news.mvp.ui.activity.MatchDetailActivity;
import com.leory.commonlib.di.component.AppComponent;
import com.leory.commonlib.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Describe : 赛事详情component
 * Author : leory
 * Date : 2019-05-27
 */
@ActivityScope
@Component(modules = MatchDetailModule.class, dependencies = AppComponent.class)
public interface MatchDetailComponent {
    void inject(MatchDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MatchDetailComponent.Builder view(MatchDetailContract.View view);

        @BindsInstance
        MatchDetailComponent.Builder detailUrl(String detailUrl);

        MatchDetailComponent.Builder appComponent(AppComponent appComponent);

        MatchDetailComponent build();
    }
}
