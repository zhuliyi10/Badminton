package com.zhuliyi.video.di.module;

import com.zhuliyi.commonlib.di.scope.ActivityScope;
import com.zhuliyi.commonlib.widget.morePop.MorePopBean;
import com.zhuliyi.video.mvp.contract.VideoListContract;
import com.zhuliyi.video.mvp.model.VideoModel;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Describe : video module
 * Author : zhuly
 * Date : 2019-05-14
 */
@Module
public abstract class VideoModule {
    @Binds
    abstract VideoListContract.Model bindVideoModel(VideoModel model);

    @ActivityScope
    @Provides
    static List<MorePopBean> provideMorePopBeans() {
        List<MorePopBean> list = new ArrayList<>();
        list.add(new MorePopBean("全部"));
        list.add(new MorePopBean("少于1分钟"));
        list.add(new MorePopBean("大于1分钟"));
        list.add(new MorePopBean("小于10分钟"));
        list.add(new MorePopBean("大于10分钟"));
        list.add(new MorePopBean("小于30分钟"));
        list.add(new MorePopBean("大于30分钟"));
        return list;
    }

}
