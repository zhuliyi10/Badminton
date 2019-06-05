package com.leory.badminton.news.di.component;

import com.leory.badminton.news.mvp.contract.MatchDetailContract;
import com.leory.badminton.news.mvp.ui.fragment.MatchAgainstChartFragment;
import com.leory.commonlib.di.scope.FragmentScope;

import dagger.BindsInstance;
import dagger.Component;
import dagger.Subcomponent;

/**
 * Describe : 赛事对阵component
 * Author : leory
 * Date : 2019-05-27
 */

@Subcomponent
@FragmentScope
public interface MatchAgainstComponent {
    void inject(MatchAgainstChartFragment fragment);

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        MatchAgainstComponent.Builder view(MatchDetailContract.MatchAgainView view);
        MatchAgainstComponent build();
    }
}
