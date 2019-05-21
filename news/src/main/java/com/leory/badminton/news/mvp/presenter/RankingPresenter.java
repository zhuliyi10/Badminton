package com.leory.badminton.news.mvp.presenter;

import android.text.TextUtils;

import com.leory.badminton.news.mvp.contract.RankingContract;
import com.leory.badminton.news.mvp.model.bean.RankingBean;
import com.leory.commonlib.di.scope.FragmentScope;
import com.leory.commonlib.http.RxHandlerSubscriber;
import com.leory.commonlib.mvp.BasePresenter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
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
     * @param refresh
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
        requestData(false, refresh, concatType, week);
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
                .subscribe(new RxHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
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
                    String key = option.attr("value");
                    String value = option.text();
                    if (!TextUtils.isEmpty(key)) {
                        weekMap.put(key, value);
                        week.add(value);
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
                    bean.setCountryName(tdList.get(1).select("span").first().text());
                    bean.setCountryFlagUrl(tdList.get(1).select("img").first().attr("src"));
                    bean.setPlayerName(tdList.get(2).select("a").first().text());
                    bean.setPlayerUrl(tdList.get(2).select("a").first().attr("href"));
                    bean.setRiseOrDrop(tdList.get(3).select("span").first().text());
                    bean.setWinAndLoss(tdList.get(4).text());
                    bean.setBonus(tdList.get(5).text());
                    bean.setPoints(tdList.get(6).select("strong").first().text());
                    bean.setPlayerId(tdList.get(7).select("div").first().attr("id"));
                    rankingList.add(bean);
                }

            }


        }
        return rankingList;
    }

}
