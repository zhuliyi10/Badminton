package com.leory.badminton.news.di.component;

import com.leory.badminton.news.mvp.contract.MatchDetailContract;
import com.leory.badminton.news.mvp.model.bean.MatchTabDateBean;
import com.leory.badminton.news.mvp.ui.fragment.MatchDateFragment;
import com.leory.commonlib.di.scope.FragmentScope;

import java.util.List;

import dagger.BindsInstance;
import dagger.Subcomponent;

/**
 * Describe : 赛程component
 * Author : leory
 * Date : 2019-06-06
 */
@Subcomponent
@FragmentScope
public interface MatchDateComponent {
    void inject(MatchDateFragment fragment);

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        MatchDateComponent.Builder view(MatchDetailContract.MatchDateView view);
        @BindsInstance
        MatchDateComponent.Builder tabDates(List<MatchTabDateBean>tabDates);
        MatchDateComponent build();
    }
}
