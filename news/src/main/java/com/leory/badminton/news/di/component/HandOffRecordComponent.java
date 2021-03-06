package com.leory.badminton.news.di.component;

import com.leory.badminton.news.di.module.HandOffRecordModule;
import com.leory.badminton.news.di.module.MatchDetailModule;
import com.leory.badminton.news.mvp.contract.HandOffRecordContract;
import com.leory.badminton.news.mvp.contract.MatchDetailContract;
import com.leory.badminton.news.mvp.model.bean.MatchTabDateBean;
import com.leory.badminton.news.mvp.ui.activity.HandOffRecordActivity;
import com.leory.badminton.news.mvp.ui.activity.MatchDetailActivity;
import com.leory.commonlib.base.delegate.IComponent;
import com.leory.commonlib.di.component.AppComponent;
import com.leory.commonlib.di.scope.ActivityScope;

import java.util.HashMap;
import java.util.List;

import javax.inject.Named;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Describe : 交手记录component
 * Author : leory
 * Date : 2019-07-23
 */
@ActivityScope
@Component(modules = HandOffRecordModule.class, dependencies = AppComponent.class)
public interface HandOffRecordComponent extends IComponent {

    void inject(HandOffRecordActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HandOffRecordComponent.Builder view(HandOffRecordContract.View view);
        @BindsInstance
        HandOffRecordComponent.Builder recordUrl(String recordUrl);

        HandOffRecordComponent.Builder appComponent(AppComponent appComponent);

        HandOffRecordComponent build();
    }
}
