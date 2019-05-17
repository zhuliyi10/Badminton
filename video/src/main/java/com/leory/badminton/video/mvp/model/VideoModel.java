package com.leory.badminton.video.mvp.model;

import com.leory.commonlib.di.scope.ActivityScope;
import com.leory.commonlib.http.IRepositoryManager;
import com.leory.commonlib.mvp.BaseModel;
import com.leory.badminton.video.mvp.contract.VideoListContract;
import com.leory.badminton.video.mvp.model.api.VideoApi;
import com.leory.badminton.video.mvp.model.bean.VideoBaseResponse;
import com.leory.badminton.video.mvp.model.bean.VideoListBean;

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
    public Observable<VideoBaseResponse<VideoListBean>> getVideoList(int num) {
        return repositoryManager.obtainRetrofitService(VideoApi.class)
                .getVideoList(url, key, num, rnd, _csrf);
    }
}
