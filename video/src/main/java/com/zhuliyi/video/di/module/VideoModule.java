package com.zhuliyi.video.di.module;

import com.zhuliyi.video.mvp.contract.VideoListContract;
import com.zhuliyi.video.mvp.model.VideoModel;

import dagger.Binds;
import dagger.Module;

/**
 * Describe : video module
 * Author : zhuly
 * Date : 2019-05-14
 */
@Module
public abstract class VideoModule {
    @Binds
    abstract VideoListContract.Model bindVideoModel(VideoModel model);
}
