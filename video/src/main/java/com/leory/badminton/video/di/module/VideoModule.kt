package com.leory.badminton.video.di.module

import com.leory.badminton.video.mvp.contract.VideoListContract
import com.leory.badminton.video.mvp.model.VideoModel
import com.leory.commonlib.di.scope.ActivityScope
import com.leory.commonlib.widget.morePop.MorePopBean
import dagger.Binds
import dagger.Module
import dagger.Provides

/**
 * Describe : video module
 * Author : zhuly
 * Date : 2019-05-14
 */
@Module
abstract class VideoBindModule {
    @Binds
    abstract fun bindVideoModel(model: VideoModel): VideoListContract.Model

}


@Module(includes = [VideoBindModule::class])
class VideoModule {
//    @ActivityScope
//    @Provides
//    fun provideMorePopBeans(): List<MorePopBean> {
//        val list = ArrayList<MorePopBean>()
//        list.add(MorePopBean("全部"))
//        list.add(MorePopBean("少于1分钟"))
//        list.add(MorePopBean("大于1分钟"))
//        list.add(MorePopBean("小于10分钟"))
//        list.add(MorePopBean("大于10分钟"))
//        list.add(MorePopBean("小于30分钟"))
//        list.add(MorePopBean("大于30分钟"))
//        return list
//    }

}