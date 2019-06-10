package com.leory.badminton.news.di.component;

import com.leory.badminton.news.di.module.MatchDetailModule;
import com.leory.badminton.news.mvp.contract.MatchDetailContract;
import com.leory.badminton.news.mvp.ui.activity.MatchDetailActivity;
import com.leory.commonlib.base.delegate.IComponent;
import com.leory.commonlib.di.component.AppComponent;
import com.leory.commonlib.di.scope.ActivityScope;

import javax.inject.Named;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Describe : 赛事详情component
 * Author : leory
 * Date : 2019-05-27
 */
@ActivityScope
@Component(modules = MatchDetailModule.class, dependencies = AppComponent.class)
public interface MatchDetailComponent extends IComponent {

    MatchAgainstComponent.Builder buildMatchAgainstComponent();

    MatchDateComponent.Builder buildMatchDateComponent();

    MatchHistoryComponent.Builder buildMatchHistoryComponent();
    MatchPlayersComponent.Builder  buildMatchPlayersComponent();
    void inject(MatchDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MatchDetailComponent.Builder view(MatchDetailContract.View view);

        @BindsInstance
        MatchDetailComponent.Builder detailUrl(@Named("detail_url") String detailUrl);

        @BindsInstance
        MatchDetailComponent.Builder matchClassify(@Named("match_classify") String matchClassify);

        MatchDetailComponent.Builder appComponent(AppComponent appComponent);

        MatchDetailComponent build();
    }
}
