package com.zhuliyi.commonlib.di.module;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhuliyi.commonlib.http.GlobalHttpHandler;
import com.zhuliyi.commonlib.http.log.LogInterceptor;
import com.zhuliyi.commonlib.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Describe : 提供一些三方库客户端实例的 {@link dagger.Module}
 * Author : zhuly
 * Date : 2018-06-12
 */
@Module
public abstract class ClientModule {
    private static final int TIME_OUT = 10;//s

    @Singleton
    @Provides
    static Retrofit provideRetrofit(Application application, @Nullable RetrofitConfiguration configuration, Retrofit.Builder builder, OkHttpClient client, HttpUrl httpUrl, Gson gson) {
        builder.baseUrl(httpUrl)
                .client(client);
        if (configuration != null) {
            configuration.configRetrofit(application, builder);
        }
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson));
        return builder.build();
    }

    @Singleton
    @Provides
    static OkHttpClient provideClient(Application application, OkHttpClient.Builder builder,Interceptor interceptor, @Nullable List<Interceptor> interceptors,  @Nullable final OkHttpConfiguration configuration, @Nullable GlobalHttpHandler handler) {
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(interceptor);
        if (handler != null) {
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    return chain.proceed(handler.onHttpRequestBefore(chain, chain.request()));
                }
            });
        }
        if (interceptors != null) {
            for (Interceptor interceptor1 : interceptors) {
                builder.addInterceptor(interceptor1);
            }
        }
        if (configuration != null) {
            configuration.configOkHttp(application, builder);
        }
        return builder.build();
    }
    @Binds
    abstract Interceptor bindInterceptor(LogInterceptor interceptor);

    @Singleton
    @Provides
    static Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    static OkHttpClient.Builder provideClientBuilder() {
        return new OkHttpClient.Builder();
    }

    /**
     * 提供 {@link RxCache}
     *
     * @param application    {@link Application}
     * @param configuration  {@link RxCacheConfiguration}
     * @param cacheDirectory RxCache 缓存路径
     * @param gson           {@link Gson}
     * @return {@link RxCache}
     */
    @Singleton
    @Provides
    static RxCache provideRxCache(Application application, @Nullable RxCacheConfiguration configuration
            , @Named("RxCacheDirectory") File cacheDirectory, Gson gson) {
        RxCache.Builder builder = new RxCache.Builder();
        RxCache rxCache = null;
        if (configuration != null) {
            rxCache = configuration.configRxCache(application, builder);
        }
        if (rxCache != null) return rxCache;
        return builder
                .persistence(cacheDirectory, new GsonSpeaker(gson));
    }

    /**
     * 需要单独给 {@link RxCache} 提供子缓存文件
     *
     * @param cacheDir 框架缓存文件
     * @return {@link File}
     */
    @Singleton
    @Provides
    @Named("RxCacheDirectory")
    static File provideRxCacheDirectory(File cacheDir) {
        File cacheDirectory = new File(cacheDir, "RxCache");
        return FileUtils.makeDirs(cacheDirectory);
    }

    @Singleton
    @Provides
    static Gson provideGson(Application application, @Nullable GsonConfiguration configuration) {
        GsonBuilder builder = new GsonBuilder();
        if (configuration != null) {
            configuration.configGson(application, builder);
        }
        return builder.create();
    }

    public interface GsonConfiguration {
        void configGson(Context context, GsonBuilder builder);
    }

    public interface RetrofitConfiguration {
        void configRetrofit(Context context, Retrofit.Builder builder);
    }

    public interface OkHttpConfiguration {
        void configOkHttp(Context context, OkHttpClient.Builder builder);
    }

    /**
     * {@link RxCache} 自定义配置接口
     */
    public interface RxCacheConfiguration {
        /**
         * 若想自定义 RxCache 的缓存文件夹或者解析方式, 如改成 FastJson
         * 请 {@code return rxCacheBuilder.persistence(cacheDirectory, new FastJsonSpeaker());}, 否则请 {@code return null;}
         *
         * @param context {@link Context}
         * @param builder {@link RxCache.Builder}
         * @return {@link RxCache}
         */
        RxCache configRxCache(@NonNull Context context, @NonNull RxCache.Builder builder);
    }
}
