package com.leory.badminton.news.mvp.presenter;

import android.text.TextUtils;

import com.leory.badminton.news.app.utils.FileHashUtils;
import com.leory.badminton.news.mvp.contract.HandOffRecordContract;
import com.leory.badminton.news.mvp.model.bean.HandOffBean;
import com.leory.badminton.news.mvp.model.bean.MatchInfoBean;
import com.leory.commonlib.di.scope.ActivityScope;
import com.leory.commonlib.mvp.BasePresenter;
import com.leory.commonlib.utils.LogUtils;
import com.leory.commonlib.utils.RxLifecycleUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * Describe : 交手记录presenter
 * Author : leory
 * Date : 2019-07-23
 */
@ActivityScope
public class HandOffRecordPresenter extends BasePresenter<HandOffRecordContract.Model, HandOffRecordContract.View> {

    private String handOffUrl;
    private HashMap<String, String> playerNameMap;
    @Inject
    public HandOffRecordPresenter(HandOffRecordContract.Model model, HandOffRecordContract.View rootView, String handOffUrl) {
        super(model, rootView);
        this.handOffUrl = handOffUrl;
        requestData();
    }

    private void requestData() {
        model.getHandOffRecords(handOffUrl)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> rootView.showLoading()).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> rootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            HttpException httpException = (HttpException) e;
                            if (httpException.code() == 404) {
                                try {
                                    String response = httpException.response().errorBody().string();
                                    parseHtmlResult(response);
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }

                            }
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void parseHtmlResult(String html) {
        Observable.just(html)

                .flatMap((Function<String, ObservableSource<HandOffBean>>) s -> Observable.just(getHandOffData(html)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(new Observer<HandOffBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HandOffBean bean) {
                        rootView.showHandOffView(bean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d(TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private HandOffBean getHandOffData(String html) {
        HandOffBean bean = new HandOffBean();
        if (html != null) {
            Document doc = Jsoup.parse(html);
            Element headElement = doc.select("div.head-to-head").first();
            Elements player1s = headElement.select("div.info-player1");
            if (player1s != null) {
                if (player1s.size() > 0) {
                    bean.setPlayer1HeadUrl(player1s.get(0).select("div.img-player img").attr("src"));
                    bean.setPlayer1Flag(player1s.get(0).select("div.flag img").attr("src"));
                    bean.setPlayer1Name(translatePlayerName(player1s.get(0).select("div.name a").text()));
                }
                if (player1s.size() > 1) {
                    bean.setPlayer12HeadUrl(player1s.get(1).select("div.img-player img").attr("src"));
                    bean.setPlayer12Flag(player1s.get(1).select("div.flag img").attr("src"));
                    bean.setPlayer12Name(translatePlayerName(player1s.get(1).select("div.name a").text()));
                }
            }
            Elements player2s = headElement.select("div.info-player2");
            if (player2s != null) {
                if (player2s.size() > 0) {
                    bean.setPlayer2HeadUrl(player2s.get(0).select("div.img-player img").attr("src"));
                    bean.setPlayer2Flag(player2s.get(0).select("div.flag img").attr("src"));
                    bean.setPlayer2Name(translatePlayerName(player2s.get(0).select("div.name a").text()));
                }
                if (player2s.size() > 1) {
                    bean.setPlayer22HeadUrl(player2s.get(1).select("div.img-player img").attr("src"));
                    bean.setPlayer22Flag(player2s.get(1).select("div.flag img").attr("src"));
                    bean.setPlayer22Name(translatePlayerName(player2s.get(1).select("div.name a").text()));
                }
            }
            Elements rankings=headElement.select("div.title-rank");
            if(rankings!=null&&rankings.size()==2){
                bean.setPlayer1Ranking(rankings.get(0).text().replace("RANKED","排名"));
                bean.setPlayer2Ranking(rankings.get(1).text().replace("RANKED","排名"));
            }
            Elements scores=headElement.select("div.h2h-score");
            if(scores!=null&&scores.size()==2){
                bean.setPlayer1Win(scores.get(0).text());
                bean.setPlayer2Win(scores.get(1).text());
            }
            Elements records=headElement.select("div.h2h-result-row");
            if(records!=null){
                List<HandOffBean.Record>recordList=new ArrayList<>();
                for (Element record:records){
                    HandOffBean.Record recordBean=new HandOffBean.Record();
                    recordBean.setDate(record.select("div.h2h_result_date").text());
                    recordBean.setMatchName(record.select("div.tmt-name").text());
                    recordBean.setScore(record.select("span.score").text());
                    String result=record.select("div.player-result-score").text().trim();
                    if(result.contains("W")){
                        recordBean.setLeftWin(true);
                    }else {
                        recordBean.setLeftWin(false);
                    }
                    recordList.add(recordBean);
                }
                bean.setRecordList(recordList);
                int player1Win=0,player2Win=0;
                for (HandOffBean.Record record:recordList){
                    if(record.isLeftWin()){
                        player1Win++;
                    }else {
                        player2Win++;
                    }
                }
                bean.setPlayer1Win(String.valueOf(player1Win));
                bean.setPlayer2Win(String.valueOf(player2Win));
            }
        }
        return bean;
    }

    private String translatePlayerName(String key) {
        if(playerNameMap==null){
            playerNameMap= FileHashUtils.getPlayerName();
        }
        String playerName = key.replaceAll("\\[\\d+\\]", "").trim();
        String value = playerNameMap.get(playerName);
        if (TextUtils.isEmpty(value)) {
            return key;
        } else {
            return key.replace(playerName, value);
        }
    }
}
