package com.leory.badminton.news.mvp.presenter;

import android.text.TextUtils;

import com.leory.badminton.news.mvp.contract.MatchDetailContract;
import com.leory.badminton.news.mvp.model.MatchDetailModel;
import com.leory.badminton.news.mvp.model.bean.MatchHistoryBean;
import com.leory.badminton.news.mvp.model.bean.MatchHistoryHeadBean;
import com.leory.badminton.news.mvp.model.bean.MultiMatchHistoryBean;
import com.leory.commonlib.di.scope.FragmentScope;
import com.leory.commonlib.http.RxHandlerSubscriber;
import com.leory.commonlib.mvp.BasePresenter;
import com.leory.commonlib.utils.RxLifecycleUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Describe : 历史赛事presenter
 * Author : leory
 * Date : 2019-06-07
 */
@FragmentScope
public class MatchHistoryPresenter extends BasePresenter<MatchDetailModel, MatchDetailContract.MatchHistory> {
    @Inject
    @Named("history_url")
    String historyUrl;
    @Inject
    @Named("player_name")
    HashMap<String, String> playerNameMap;
    @Inject
    public MatchHistoryPresenter(MatchDetailModel model, MatchDetailContract.MatchHistory rootView) {
        super(model, rootView);
    }

    public void requestData() {
        model.getMatchHistory(historyUrl)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> rootView.showLoading()).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> rootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(new RxHandlerSubscriber<String>() {

                    @Override
                    public void onNext(String o) {
                        parseHtmlResult(o);
                    }

                });
    }

    private void parseHtmlResult(String html) {
        Observable.just(html)

                .flatMap((Function<String, ObservableSource<List<MultiMatchHistoryBean>>>) s -> Observable.just(getMatchHistoryData(html)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(new Observer<List<MultiMatchHistoryBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<MultiMatchHistoryBean> data) {
                        rootView.showHistoryData(data);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private List<MultiMatchHistoryBean> getMatchHistoryData(String html) {
        List<MultiMatchHistoryBean> data = new ArrayList<>();
        if (html != null) {
            Document doc = Jsoup.parse(html);
            Elements box = doc.select("div.box-historical");
            if (box != null) {
                for (Element item : box) {
                    MatchHistoryHeadBean headBean = new MatchHistoryHeadBean();
                    headBean.setYear(item.select("h3").first().text());
                    headBean.setMatchName(item.select("h5").first().text());
                    headBean.setDetailUrl(item.select("a").first().attr("href"));
                    data.add(new MultiMatchHistoryBean(MultiMatchHistoryBean.TYPE_HEAD, headBean));

                    Elements list = item.select("div#list-historical >div");
                    if (list != null && list.size() == 5) {
                        Element info = list.get(0);
                        MatchHistoryBean historyBean = new MatchHistoryBean();
                        historyBean.setMsHead(info.select("div.image img").first().attr("src"));
                        historyBean.setMsFlag(info.select("div.description img").first().attr("src"));
                        historyBean.setMsName(translatePlayerName(info.select("div.description a").first().text()));
                        info = list.get(1);
                        historyBean.setWsHead(info.select("div.image img").first().attr("src"));
                        historyBean.setWsFlag(info.select("div.description img").first().attr("src"));
                        historyBean.setWsName(translatePlayerName(info.select("div.description a").first().text()));
                        info = list.get(2);
                        historyBean.setMd1Head(info.select("div.item-double-1 div.image img").first().attr("src"));
                        historyBean.setMd1Flag(info.select("div.item-double-1 div.description img").first().attr("src"));
                        historyBean.setMd1Name(translatePlayerName(info.select("div.item-double-1 div.description a").first().text()));
                        historyBean.setMd2Head(info.select("div.item-double-2 div.image img").first().attr("src"));
                        historyBean.setMd2Flag(info.select("div.item-double-2 div.description img").first().attr("src"));
                        historyBean.setMd2Name(translatePlayerName(info.select("div.item-double-2 div.description a").first().text()));
                        info = list.get(3);
                        historyBean.setWd1Head(info.select("div.item-double-1 div.image img").first().attr("src"));
                        historyBean.setWd1Flag(info.select("div.item-double-1 div.description img").first().attr("src"));
                        historyBean.setWd1Name(translatePlayerName(info.select("div.item-double-1 div.description a").first().text()));
                        historyBean.setWd2Head(info.select("div.item-double-2 div.image img").first().attr("src"));
                        historyBean.setWd2Flag(info.select("div.item-double-2 div.description img").first().attr("src"));
                        historyBean.setWd2Name(translatePlayerName(info.select("div.item-double-2 div.description a").first().text()));
                        info = list.get(4);
                        historyBean.setXd1Head(info.select("div.item-double-1 div.image img").first().attr("src"));
                        historyBean.setXd1Flag(info.select("div.item-double-1 div.description img").first().attr("src"));
                        historyBean.setXd1Name(translatePlayerName(info.select("div.item-double-1 div.description a").first().text()));
                        historyBean.setXd2Head(info.select("div.item-double-2 div.image img").first().attr("src"));
                        historyBean.setXd2Flag(info.select("div.item-double-2 div.description img").first().attr("src"));
                        historyBean.setXd2Name(translatePlayerName(info.select("div.item-double-2 div.description a").first().text()));
                        data.add(new MultiMatchHistoryBean(MultiMatchHistoryBean.TYPE_CONTENT, historyBean));
                    }

                }
            }
        }

        return data;
    }

    private String translatePlayerName(String key) {
        String playerName=key.replaceAll("\\[\\d+\\]","").trim();
        String value = playerNameMap.get(playerName);
        if (TextUtils.isEmpty(value)) {
            return key;
        } else {
            return key.replace(playerName,value);
        }
    }
}
