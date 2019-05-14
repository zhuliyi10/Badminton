package com.zhuliyi.video.mvp.model;

import com.zhuliyi.commonlib.di.scope.ActivityScope;
import com.zhuliyi.commonlib.http.IRepositoryManager;
import com.zhuliyi.commonlib.mvp.BaseModel;
import com.zhuliyi.video.mvp.contract.VideoListContract;
import com.zhuliyi.video.mvp.model.api.VideoApi;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Describe : 视频的数据操作类
 * Author : zhuly
 * Date : 2019-05-14
 */
@ActivityScope
public class VideoModel extends BaseModel implements VideoListContract.Model {
    private static String url = "http://v.zhibo.tv/ajax/appnesting/search";
    private static String rnd = "0.19737283560050511";
    private static String key = "羽毛球";
    private static String _csrf = "WE9xeFlXc1g1IysfChMQMjwjJDJrMiQXB3o3CAE4FS40eBIMK28GLQ==";
    @Inject
    public VideoModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<Object> getVideoList(int num) {
        return repositoryManager.obtainRetrofitService(VideoApi.class)
                .getVideoList(url, key, num, rnd, _csrf);
    }
}
