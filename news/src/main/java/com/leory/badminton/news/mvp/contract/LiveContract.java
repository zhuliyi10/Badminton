package com.leory.badminton.news.mvp.contract;

import com.leory.badminton.news.mvp.model.bean.LiveBean;
import com.leory.commonlib.mvp.IModel;
import com.leory.commonlib.mvp.IView;

import io.reactivex.Observable;

/**
 * Describe :直播接口
 * Author : leory
 * Date : 2019-06-03
 */
public class LiveContract {
    public interface View extends IView{
        void showLiveData(LiveBean bean);
    }
    public interface Model extends IModel{
        Observable<String> getLiveMatch();
    }
}
