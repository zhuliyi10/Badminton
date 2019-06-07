package com.leory.badminton.news.mvp.contract;

import com.leory.badminton.news.mvp.model.bean.LiveBean;
import com.leory.badminton.news.mvp.model.bean.LiveDetailBean;
import com.leory.commonlib.mvp.IModel;
import com.leory.commonlib.mvp.IView;

import java.util.List;

import io.reactivex.Observable;

/**
 * Describe :直播接口
 * Author : leory
 * Date : 2019-06-03
 */
public class LiveContract {
    public interface View extends IView{
        void showLiveData(LiveBean bean);
        void showLiveDetail(List<LiveDetailBean>data);//显示直播列表
    }
    public interface Model extends IModel{
        Observable<String> getLiveMatch();
        Observable<String> getLiveDetail(String url);
        Observable<String> getLiveUrl(String url);
    }
}
