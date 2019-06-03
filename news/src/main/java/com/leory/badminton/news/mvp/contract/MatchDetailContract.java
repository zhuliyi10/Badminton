package com.leory.badminton.news.mvp.contract;

import com.leory.badminton.news.mvp.model.bean.MatchInfoBean;
import com.leory.badminton.news.mvp.ui.widget.againstFlow.AgainstFlowBean;
import com.leory.commonlib.mvp.IModel;
import com.leory.commonlib.mvp.IView;

import java.util.List;

import io.reactivex.Observable;

/**
 * Describe : 赛事详情接口
 * Author : leory
 * Date : 2019-05-27
 */
public interface MatchDetailContract {
    interface View extends IView {
        void showAgainstView(List<List<AgainstFlowBean>>againstData);//显示对阵数据
        void showMatchInfo(MatchInfoBean bean);//显示比赛信息
        void showMatchSchedule(int size);
    }
    interface Model extends IModel{
        Observable<String> getMatchInfo(String url);
        Observable<String> getMatchDetail(String url);
    }
}
