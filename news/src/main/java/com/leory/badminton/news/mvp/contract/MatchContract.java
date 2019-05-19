package com.leory.badminton.news.mvp.contract;

import com.leory.commonlib.mvp.IModel;
import com.leory.commonlib.mvp.IView;

import org.jsoup.nodes.Document;

import io.reactivex.Observable;

/**
 * Describe : 赛事接口
 * Author : leory
 * Date : 2019-05-19
 */
public interface MatchContract {
    interface View extends IView {

    }

    interface Model extends IModel {
        Observable<Object> getMatchList(String ryear, String rstate);
    }
}
