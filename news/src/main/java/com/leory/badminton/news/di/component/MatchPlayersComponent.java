package com.leory.badminton.news.di.component;

import com.leory.badminton.news.mvp.contract.MatchDetailContract;
import com.leory.badminton.news.mvp.ui.fragment.MatchPlayersFragment;
import com.leory.commonlib.di.scope.FragmentScope;

import dagger.BindsInstance;
import dagger.Subcomponent;

/**
 * Describe : 参赛运动员component
 * Author : leory
 * Date : 2019-06-10
 */

@Subcomponent
@FragmentScope
public interface MatchPlayersComponent {
    void inject(MatchPlayersFragment fragment);

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        MatchPlayersComponent.Builder view(MatchDetailContract.MatchPlayersView view);

        MatchPlayersComponent build();
    }
}
