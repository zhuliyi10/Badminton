package com.leory.badminton.news.mvp.presenter;

import android.text.TextUtils;

import com.leory.badminton.news.app.utils.FileHashUtils;
import com.leory.badminton.news.mvp.contract.RankingContract;
import com.leory.badminton.news.mvp.model.bean.RankingBean;
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
import java.util.LinkedHashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.leory.badminton.news.mvp.ui.fragment.RankingFragment.RANKING_TYPES;

/**
 * Describe : 排名presenter
 * Author : leory
 * Date : 2019-05-21
 */
@FragmentScope
public class RankingPresenter extends BasePresenter<RankingContract.Model, RankingContract.View> {
    private int lastPage = 0;
    private int pageNum = 25;
    private LinkedHashMap<String, String> weekMap = new LinkedHashMap<>();
    private HashMap<String, String> countryMap;
    private HashMap<String, String> playerNameMap;

    @Inject
    public RankingPresenter(RankingContract.Model model, RankingContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 首次执行
     */
    public void firstInit() {
        requestData(true, true, null, null);
    }

    /**
     * 选择数据
     *
     * @param rankingType
     * @param week
     */
    public void selectData(boolean refresh, String rankingType, String week) {
        String concatType = null;
        if (RANKING_TYPES[0].equals(rankingType)) {//男单
            concatType = "6/men-s-singles/";
        } else if (RANKING_TYPES[1].equals(rankingType)) {//女单
            concatType = "7/women-s-singles/";
        } else if (RANKING_TYPES[2].equals(rankingType)) {//男双
            concatType = "8/men-s-doubles/";
        } else if (RANKING_TYPES[3].equals(rankingType)) {//女双
            concatType = "9/women-s-doubles/";
        } else if (RANKING_TYPES[4].equals(rankingType)) {//混双
            concatType = "10/mixed-doubles/";
        }
        String concatWeek = weekMap.get(week);
        if (concatWeek != null) concatWeek = concatWeek.replace("--", "/");
        requestData(false, refresh, concatType, concatWeek);
    }

    /**
     * 请求数据
     *
     * @param refresh
     * @param rankingType
     * @param week
     */
    private void requestData(boolean first, boolean refresh, String rankingType, String week) {
        if (refresh) lastPage = 0;
        model.getRankingList(rankingType, week, pageNum, lastPage + 1)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if (refresh) {
                        rootView.showLoading();//显示下拉刷新的进度条
                    } else {
                        rootView.startLoadMore();//显示上拉加载更多的进度条
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (refresh)
                        rootView.hideLoading();//隐藏下拉刷新的进度条
                    else
                        rootView.endLoadMore();//隐藏上拉加载更多的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(new RxHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        lastPage++;
                        parseHtmlResult(first, refresh, s);
                    }
                });
    }


    /**
     * 解析html
     *
     * @param html
     */
    private void parseHtmlResult(boolean first, boolean refresh, String html) {

        Observable.just(html)

                .flatMap((Function<String, ObservableSource<List<RankingBean>>>) s -> Observable.just(getRankingData(first, html)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(new Observer<List<RankingBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<RankingBean> matchItemSections) {
                        rootView.showRankingData(refresh, matchItemSections);
                    }

                    @Override
                    public void onError(Throwable e) {
                        rootView.showRankingData(refresh, new ArrayList<>());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private List<RankingBean> getRankingData(boolean first, String html) {
        List<RankingBean> rankingList = new ArrayList<>();
        if (html != null) {
            Document doc = Jsoup.parse(html);
            if (first) {
                Elements weekOptions = doc.select("select#ranking-week").first().select("option");
                List<String> week = new ArrayList<>();
                for (Element option : weekOptions) {
                    String value = option.attr("value");
                    String key = option.text();
                    if (!TextUtils.isEmpty(key)) {
                        weekMap.put(key, value);
                        week.add(key);
                    }
                }
                rootView.showWeekData(week);

            }
            Elements trList = doc.select("tr");
            for (int i = 1; i < trList.size(); i = i + 2) {
                Element tr = trList.get(i);
                Elements tdList = tr.select("td");
                if (tdList.size() == 8) {
                    RankingBean bean = new RankingBean();
                    bean.setRankingNum(tdList.get(0).text());
                    Elements countries = tdList.get(1).select("div.country");
                    if (countries.size() > 0) {
                        bean.setCountryName(translateCountry(countries.get(0).select("span").first().text()));
                        bean.setCountryFlagUrl(countries.get(0).select("img").first().attr("src"));
                    }
                    if (countries.size() > 1) {
                        bean.setCountry2Name(translateCountry(countries.get(1).select("span").first().text()));
                        bean.setCountryFlag2Url(countries.get(1).select("img").first().attr("src"));
                    }
                    Elements players = tdList.get(2).select("div.player span");
                    if (players.size() > 0) {
                        bean.setPlayerName(translatePlayerName(players.get(0).select("a").first().text()));
                        bean.setPlayerUrl(players.get(0).select("a").first().attr("href"));
                    }
                    if (players.size() > 1) {
                        bean.setPlayer2Name(translatePlayerName(players.get(1).select("a").first().text()));
                        bean.setPlayer2Url(players.get(1).select("a").first().attr("href"));
                    }
                    bean.setRiseOrDrop(Integer.parseInt(tdList.get(3).select("span").first().text()));
                    Element arrowElement=tdList.get(3).select("img").first();
                    if(arrowElement!=null){
                        if(arrowElement.attr("src").contains("arrow-down")){//为负
                            bean.setRiseOrDrop(0-bean.getRiseOrDrop());
                        }
                    }
                    bean.setWinAndLoss(tdList.get(4).text());
                    bean.setBonus(tdList.get(5).text());
                    String point = tdList.get(6).select("strong").first().text();
                    if (point != null) {
                        String[] points = point.split("/");
                        if (points.length > 0) {
                            bean.setPoints(points[0].trim());
                        }
                    }

                    bean.setPlayerId(tdList.get(7).select("div").first().attr("id"));
                    rankingList.add(bean);
                }

            }


        }
        return rankingList;
    }

    /**
     * 翻译国家
     *
     * @param key
     * @return
     */
    private String translateCountry(String key) {
        if (countryMap == null) {
            countryMap = FileHashUtils.getCountry();
        }
        String value = countryMap.get(key);
        if (TextUtils.isEmpty(value)) {
            return key;
        } else {
            return value;
        }
    }

    /**
     * 翻译运动员名字
     *
     * @param key
     * @return
     */
    private String translatePlayerName(String key) {
        if (playerNameMap == null) {
            playerNameMap = FileHashUtils.getPlayerName();
        }
        String value = playerNameMap.get(key);
        if (TextUtils.isEmpty(value)) {
            return key;
        } else {
            return value;
        }
    }
}
