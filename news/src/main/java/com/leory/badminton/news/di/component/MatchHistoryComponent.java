package com.leory.badminton.news.di.component;

import com.leory.badminton.news.mvp.contract.MatchDetailContract;
import com.leory.badminton.news.mvp.ui.fragment.MatchHistoryFragment;
import com.leory.commonlib.di.scope.FragmentScope;

import javax.inject.Named;

import dagger.BindsInstance;
import dagger.Subcomponent;

/**
 * Describe : 历史赛事component
 * Author : leory
 * Date : 2019-06-07
 */

@Subcomponent
@FragmentScope
public interface MatchHistoryComponent {
    void inject(MatchHistoryFragment fragment);

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        MatchHistoryComponent.Builder view(MatchDetailContract.MatchHistory view);
        @BindsInstance
        MatchHistoryComponent.Builder historyUrl(@Named("history_url") String historyUrl);
        MatchHistoryComponent build();
    }
}
