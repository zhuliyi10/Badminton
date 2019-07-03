package com.leory.badminton.news.mvp.presenter;

import android.text.TextUtils;

import com.leory.badminton.news.app.utils.FileHashUtils;
import com.leory.badminton.news.mvp.contract.MatchDetailContract;
import com.leory.badminton.news.mvp.model.bean.MatchInfoBean;
import com.leory.badminton.news.mvp.model.bean.MatchTabDateBean;
import com.leory.commonlib.di.scope.ActivityScope;
import com.leory.commonlib.http.RxHandlerSubscriber;
import com.leory.commonlib.mvp.BasePresenter;
import com.leory.commonlib.utils.LogUtils;
import com.leory.commonlib.utils.RxLifecycleUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
 * Describe : 赛事详情presenter
 * Author : zhuly
 * Date : 2019-05-27
 */
@ActivityScope
public class MatchDetailPresenter extends BasePresenter<MatchDetailContract.Model, MatchDetailContract.View> {


    String detailUrl;

    String matchClassify;
    private HashMap<String, String> matchNameMap;
    private HashMap<String, String> MonthMap;

    @Inject
    public MatchDetailPresenter(MatchDetailContract.Model model, MatchDetailContract.View rootView, @Named("detail_url") String detailUrl, @Named("match_classify") String matchClassify) {
        super(model, rootView);
        this.detailUrl = detailUrl;
        this.matchClassify = matchClassify;
        requestMatchInfo();
    }

    private void requestMatchInfo() {
        if (detailUrl != null) {
            String requestUrl = detailUrl + "results/podium/";
            if (isGroup()) {
                requestUrl = detailUrl + "podium";
            }
            model.getMatchInfo(requestUrl)
                    .subscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                    .subscribe(new RxHandlerSubscriber<String>() {

                        @Override
                        public void onNext(String o) {
                            parseHtmlMatchInfo(o);
                        }

                    });
        }
    }

    /**
     * 是否是团体赛
     *
     * @return
     */
    public boolean isGroup() {
        if ("Grade 1 - Team Tournaments".equals(matchClassify)) {
            return true;
        } else {
            return false;
        }
    }


    private void parseHtmlMatchInfo(String html) {
        Observable.just(html)

                .flatMap((Function<String, ObservableSource<MatchInfoBean>>) s -> Observable.just(getMatchInfo(html)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(new Observer<MatchInfoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MatchInfoBean bean) {
                        rootView.showMatchInfo(bean);
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

    private MatchInfoBean getMatchInfo(String html) {
        MatchInfoBean bean = new MatchInfoBean();
        if (html != null) {

            Document doc = Jsoup.parse(html);
            Pattern p = Pattern.compile("document.getElementById(.*).style.backgroundImage(.*).jpg");
            Matcher m = p.matcher(html);
            boolean isFind = m.find();
            if (isFind) {
                String group = m.group();
                m = Pattern.compile("https(.*).jpg").matcher(group);
                if (m.find()) {
                    String bgUrl = m.group();
                    LogUtils.d(TAG, bgUrl);
                    bean.setMatchBackground(bgUrl);
                }

            }
            Element head = doc.select("div.box-results-tournament").first();
            Element element = head.select("div.info h2").first();
            if (element != null) {
                bean.setMatchName(translateMatchName(element.text()));
            }
            element = head.select("div.info h4").first();
            if (element != null) {
                bean.setMatchDate(translateMonth(element.text()));
            }
            element = head.select("div.info h5").first();
            if (element != null) {
                bean.setMatchSite(element.text());
                String[] strings = element.text().split(",");
                if (strings.length > 0) {
                    bean.setCountry(strings[strings.length - 1].trim());
                }
            }
            element = head.select("div.info div.prize").first();
            if (element != null) {
                bean.setMatchBonus(element.text().replace("PRIZE MONEY USD", "奖金:$"));
            }
            element = head.select("div.logo-right img").first();
            if (element != null) {
                bean.setMatchIcon(element.attr("src"));
            }

            Element ul = doc.select("ul#ajaxTabsResults").first();
            if (ul != null) {
                Elements lis = ul.select("li");
                if (lis != null) {
                    List<MatchTabDateBean> headTabs = new ArrayList<>();
                    for (int i = 1; i < lis.size() - 1; i++) {
                        Element li = lis.get(i);
                        String link = li.select("a").first().attr("href");
                        String name = translateMonth(li.select("a").first().text());
                        headTabs.add(new MatchTabDateBean(link, name));
                    }
                    bean.setTabDateHeads(headTabs);

                }
            }

            Element li = doc.select("ul#ajaxTabsTmt li").first();
            if (li != null) {
                bean.setHistoryUrl(li.select("a").first().attr("href"));
            }

        }
        return bean;
    }

    private String translateMatchName(String key) {
        if (matchNameMap == null) {
            matchNameMap = FileHashUtils.getMatchName();
        }
        String matchNameNotYear = key.replaceAll("\\d+", "").trim();
        String value = matchNameMap.get(matchNameNotYear.toUpperCase());
        if (TextUtils.isEmpty(value)) {
            return key;
        } else {
            return key.replace(matchNameNotYear, value);
        }
    }

    private String translateMonth(String key) {
        if (key == null) return null;
        if (MonthMap == null) {
            MonthMap = FileHashUtils.getMonth();
        }
        String month = key.replaceAll("\\d+", "").replaceAll("-", "").trim();
        String value = MonthMap.get(month);
        if (TextUtils.isEmpty(value)) {
            return key;
        } else {
            return key.replace(month, value);
        }
    }
}
