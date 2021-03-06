package com.leory.badminton.mine.di.component;

import com.leory.badminton.mine.di.module.LoginModule;
import com.leory.badminton.mine.mvp.contract.LoginContract;
import com.leory.badminton.mine.mvp.ui.activity.LoginActivity;
import com.leory.commonlib.base.delegate.IComponent;
import com.leory.commonlib.di.component.AppComponent;
import com.leory.commonlib.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Describe : 我的component
 * Author : leory
 * Date : 2019-06-19
 */
@ActivityScope
@Component(modules = LoginModule.class, dependencies = AppComponent.class)
public interface LoginComponent extends IComponent {
    void inject(LoginActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        LoginComponent.Builder view(LoginContract.View view);

        LoginComponent.Builder appComponent(AppComponent appComponent);

        LoginComponent build();
    }
}
