package com.zhuliyi.commonlib.di.module;

import com.zhuliyi.commonlib.http.IRepositoryManager;
import com.zhuliyi.commonlib.http.RepositoryManager;

import dagger.Binds;
import dagger.Module;

/**
 * Describe :提供一些框架必须的实例的 {@link dagger.Module}
 * Author : zhuly
 * Date : 2018-06-12
 */

@Module
public abstract class AppModule {
    @Binds
    abstract IRepositoryManager bindRepositoryManager(RepositoryManager repositoryManager);
}
