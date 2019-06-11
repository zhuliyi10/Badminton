package com.leory.badminton.news.di.component;

import com.leory.badminton.news.di.module.PlayerModule;
import com.leory.badminton.news.mvp.contract.PlayerContract;
import com.leory.badminton.news.mvp.ui.activity.PlayerDetailActivity;
import com.leory.commonlib.base.delegate.IComponent;
import com.leory.commonlib.di.component.AppComponent;
import com.leory.commonlib.di.scope.ActivityScope;

import javax.inject.Named;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Describe : 运动员详情component
 * Author : leory
 * Date : 2019-06-11
 */
@ActivityScope
@Component(modules = PlayerModule.class, dependencies = AppComponent.class)
public interface PlayerDetailComponent extends IComponent {
    void inject(PlayerDetailActivity activity);
    PlayerMatchComponent.Builder buildPlayerMatchComponent();
    @Component.Builder
    interface Builder {
        @BindsInstance
        PlayerDetailComponent.Builder view(PlayerContract.View view);

        @BindsInstance
        PlayerDetailComponent.Builder playerUrl(@Named("player_url") String playerUrl);

        PlayerDetailComponent.Builder appComponent(AppComponent appComponent);

        PlayerDetailComponent build();
    }
}
