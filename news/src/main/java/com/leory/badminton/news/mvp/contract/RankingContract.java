package com.leory.badminton.news.mvp.contract;

import com.leory.badminton.news.mvp.model.bean.RankingBean;
import com.leory.commonlib.mvp.IModel;
import com.leory.commonlib.mvp.IView;

import java.util.List;

import io.reactivex.Observable;

/**
 * Describe : 排名接口
 * Author : leory
 * Date : 2019-05-19
 */
public interface RankingContract {
    interface View extends IView {
        void startLoadMore();

        void endLoadMore();

        void showRankingData(boolean refresh, List<RankingBean> data);

        void showWeekData(List<String> data);
    }

    interface Model extends IModel {
        Observable<String> getRankingList(String rankingType, String week, int pageNum, int num);
    }
}
