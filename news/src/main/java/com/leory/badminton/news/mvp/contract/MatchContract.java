package com.leory.badminton.news.mvp.contract;

import com.leory.badminton.news.mvp.model.bean.MatchItemSection;
import com.leory.commonlib.mvp.IModel;
import com.leory.commonlib.mvp.IView;

import org.jsoup.nodes.Document;

import java.util.List;

import io.reactivex.Observable;

/**
 * Describe : 赛事接口
 * Author : leory
 * Date : 2019-05-19
 */
public interface MatchContract {
    interface View extends IView {
        void showMatchData(List<MatchItemSection>data);
    }

    interface Model extends IModel {
        Observable<String> getMatchList(String ryear, String rstate);
    }
}
