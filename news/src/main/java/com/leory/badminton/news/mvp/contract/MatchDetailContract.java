package com.leory.badminton.news.mvp.contract;

import com.leory.badminton.news.mvp.model.bean.MatchDateBean;
import com.leory.badminton.news.mvp.model.bean.MatchInfoBean;
import com.leory.badminton.news.mvp.model.bean.MultiMatchHistoryBean;
import com.leory.badminton.news.mvp.model.bean.MultiMatchPlayersBean;
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

        void showMatchInfo(MatchInfoBean bean);//显示比赛信息

    }

    interface MatchAgainView extends IView {
        void showAgainstView(List<List<AgainstFlowBean>> againstData);//显示对阵数据

        void showMatchSchedule(List<String> tags);
    }

    interface MatchDateView extends IView {
        void showDateData(List<MatchDateBean> data);//显示日期赛事
        void toHistoryDetail(String handOffUrl);//跳到交手战绩
        String getFilterText();//获取过虑文字
    }

    interface MatchHistory extends IView {
        void showHistoryData(List<MultiMatchHistoryBean>data);
    }

    interface MatchPlayersView extends IView{
        void showPlayersData(List<MultiMatchPlayersBean>data);
    }

    interface Model extends IModel {
        Observable<String> getMatchInfo(String url);

        Observable<String> getMatchDetail(String url);

        Observable<String> getMatchDate(String url);
        Observable<String> getMatchDate(String url,String match);

        Observable<String> getMatchHistory(String url);

        Observable<String> getMatchPlayers(String url,String tab);
    }
}
