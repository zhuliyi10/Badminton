package com.leory.badminton.news.di.component;

import com.leory.badminton.news.mvp.contract.PlayerContract;
import com.leory.badminton.news.mvp.ui.fragment.PlayerMatchFragment;
import com.leory.commonlib.di.scope.FragmentScope;

import dagger.BindsInstance;
import dagger.Subcomponent;

/**
 * Describe : 运动员赛果
 * Author : leory
 * Date : 2019-06-11
 */
@Subcomponent
@FragmentScope
public interface PlayerMatchComponent {
    void inject(PlayerMatchFragment fragment);


    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        PlayerMatchComponent.Builder view(PlayerContract.MatchView view);

        PlayerMatchComponent build();
    }
}
