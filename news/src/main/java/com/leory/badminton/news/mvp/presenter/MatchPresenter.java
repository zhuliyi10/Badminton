package com.leory.badminton.news.mvp.presenter;

import com.leory.badminton.news.mvp.contract.MatchContract;
import com.leory.commonlib.di.scope.FragmentScope;
import com.leory.commonlib.http.RxHandlerSubscriber;
import com.leory.commonlib.mvp.BasePresenter;
import com.leory.commonlib.utils.LogUtils;
import com.leory.commonlib.utils.RxUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import javax.inject.Inject;

/**
 * Describe :
 * Author : leory
 * Date : 2019-05-19
 */
@FragmentScope
public class MatchPresenter extends BasePresenter<MatchContract.Model,MatchContract.View> {

    @Inject
    public MatchPresenter(MatchContract.Model model, MatchContract.View rootView) {
        super(model, rootView);
        requestData();
    }

    private void requestData(){
        model.getMatchList("2019","all")
                .compose(RxUtils.applySchedulers(rootView))
                .subscribe(new RxHandlerSubscriber<Object>() {

                    @Override
                    public void onNext(Object o) {
                        LogUtils.d(TAG,o.toString());
                    }
                });

//        try {
//            Document document = Jsoup.connect("https://bwfbadminton.cn/calendar/2019/all/?ajax=bwfresultlanding&ryear=2019&rstate=all&category_id=")
//                    .timeout(10000)
//                    .get();
//            Elements noteList = document.select("ul.note-list");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
