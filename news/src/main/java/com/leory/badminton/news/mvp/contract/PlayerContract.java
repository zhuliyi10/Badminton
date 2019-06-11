package com.leory.badminton.news.mvp.contract;

import com.leory.badminton.news.mvp.model.bean.PlayerDetailBean;
import com.leory.badminton.news.mvp.model.bean.PlayerMatchBean;
import com.leory.commonlib.mvp.IModel;
import com.leory.commonlib.mvp.IView;

import java.util.List;

import io.reactivex.Observable;

/**
 * Describe : 运动员接口
 * Author : leory
 * Date : 2019-06-11
 */
public interface PlayerContract {
    interface View extends IView {
        void showPlayerDetail(PlayerDetailBean bean);
    }

    interface MatchView extends IView {
        void showMatchData(List<PlayerMatchBean> data);
    }

    interface Model extends IModel {
        Observable<String> getPlayerDetail(String url);

        Observable<String> getPlayerMatches(String url, String year);
    }
}
