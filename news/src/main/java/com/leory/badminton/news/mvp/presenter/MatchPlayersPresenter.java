package com.leory.badminton.news.mvp.presenter;

import android.text.TextUtils;

import com.leory.badminton.news.mvp.contract.MatchDetailContract;
import com.leory.badminton.news.mvp.model.bean.MatchPlayerBean;
import com.leory.badminton.news.mvp.model.bean.MatchPlayerHeadBean;
import com.leory.badminton.news.mvp.model.bean.MatchPlayerListBean;
import com.leory.badminton.news.mvp.model.bean.MultiMatchPlayersBean;
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
 * Describe : 参赛运动员 presenter
 * Author : leory
 * Date : 2019-06-10
 */
@FragmentScope
public class MatchPlayersPresenter extends BasePresenter<MatchDetailContract.Model, MatchDetailContract.MatchPlayersView> {
    @Inject
    @Named("player_name")
    HashMap<String, String> playerNameMap;
    @Inject
    @Named("country_name")
    HashMap<String, String> countryNameMap;
    @Inject
    @Named("detail_url")
    String detailUrl;

    @Inject
    public MatchPlayersPresenter(MatchDetailContract.Model model, MatchDetailContract.MatchPlayersView rootView) {
        super(model, rootView);
    }

    public void requestData(String type) {
        if (detailUrl != null) {
            String requestUrl = detailUrl + "players/";
            String tab = getTab(type);
            model.getMatchPlayers(requestUrl, tab)
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
    }

    private void parseHtmlResult(String html) {
        Observable.just(html)

                .flatMap((Function<String, ObservableSource<List<MultiMatchPlayersBean>>>) s -> Observable.just(getMatchPlayersData(html)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(new Observer<List<MultiMatchPlayersBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<MultiMatchPlayersBean> data) {
                        rootView.showPlayersData(data);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private List<MultiMatchPlayersBean> getMatchPlayersData(String html) {
        List<MultiMatchPlayersBean> data = new ArrayList<>();
        if (html != null) {
            Document doc = Jsoup.parse(html);
            Elements classifies = doc.select("div.rankings-content_tabpanel");
            if (classifies != null && classifies.size() == 2) {
                MatchPlayerHeadBean headBean = new MatchPlayerHeadBean();
//                headBean.setName(classifies.get(0).select("h3").first().text());
                headBean.setName("种子选手");
                data.add(new MultiMatchPlayersBean(MultiMatchPlayersBean.TYPE_HEAD, headBean));

                MatchPlayerListBean listBean = new MatchPlayerListBean();
                listBean.setData(getPlayerList(classifies.get(0).select("div.entry-player-pair-wrap")));
                data.add(new MultiMatchPlayersBean(MultiMatchPlayersBean.TYPE_CONTENT, listBean));

                headBean = new MatchPlayerHeadBean();
//                headBean.setName(classifies.get(1).select("h3").first().text());
//                headBean.setSecond(classifies.get(1).select("h5").first().text());
                headBean.setName("所有运动员");
                data.add(new MultiMatchPlayersBean(MultiMatchPlayersBean.TYPE_HEAD, headBean));
                Elements countries = classifies.get(1).select("div.entry-player-country");
                for (Element country : countries) {
                    listBean = new MatchPlayerListBean();
                    listBean.setData(getPlayerList(country.select("div.entry-player-pair-wrap")));
                    data.add(new MultiMatchPlayersBean(MultiMatchPlayersBean.TYPE_CONTENT, listBean));
                }
            }
        }
        return data;
    }

    private List<MatchPlayerBean> getPlayerList(Elements elements) {
        List<MatchPlayerBean> data = new ArrayList<>();
        if (elements != null) {
            for (Element e : elements) {
                Elements players = e.select("a");
                if (players != null) {
                    MatchPlayerBean playerBean = new MatchPlayerBean();
                    if (players.size() > 0) {
                        Element player = players.get(0);
                        playerBean.setHead1(player.select("div.entry-player-image img").first().attr("src"));
                        playerBean.setName1(translatePlayerName(player.select("div.entry-player-name").first().text()));
                        playerBean.setFlag1(player.select("div.entry-player-flag img").first().attr("src"));
                        playerBean.setCountry1(translateCountryName(player.select("div.entry-player-flag span").first().text()));
                    }
                    if (players.size() > 1) {
                        Element player = players.get(1);
                        playerBean.setHead2(player.select("div.entry-player-image img").first().attr("src"));
                        playerBean.setName2(translatePlayerName(player.select("div.entry-player-name").first().text()));
                        playerBean.setFlag2(player.select("div.entry-player-flag img").first().attr("src"));
                        playerBean.setCountry2(translateCountryName(player.select("div.entry-player-flag span").first().text()));
                    }
                    data.add(playerBean);
                }
            }
        }
        return data;
    }

    private String getTab(String type) {
        if ("男单".equals(type)) {
            return "ms";
        } else if ("女单".equals(type)) {
            return "ws";
        } else if ("男双".equals(type)) {
            return "md";
        } else if ("女双".equals(type)) {
            return "wd";
        } else if ("混双".equals(type)) {
            return "xd";
        }
        return "ms";
    }

    private String translatePlayerName(String key) {
        String playerName = key.replaceAll("\\[\\d+\\]", "").trim();
        String value = playerNameMap.get(playerName);
        if (TextUtils.isEmpty(value)) {
            return key;
        } else {
            return key.replace(playerName, value);
        }
    }

    private String translateCountryName(String key) {
        String value = countryNameMap.get(key);
        if (TextUtils.isEmpty(value)) {
            return key;
        } else {
            return key.replace(key, value);
        }
    }
}
