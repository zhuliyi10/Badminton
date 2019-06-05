package com.leory.badminton.news.mvp.presenter;

import com.leory.badminton.news.mvp.contract.MatchDetailContract;
import com.leory.badminton.news.mvp.model.bean.MatchInfoBean;
import com.leory.commonlib.di.scope.ActivityScope;
import com.leory.commonlib.http.RxHandlerSubscriber;
import com.leory.commonlib.mvp.BasePresenter;
import com.leory.commonlib.utils.LogUtils;
import com.leory.commonlib.utils.RxLifecycleUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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

    private String currentMatchSchedule;


    String detailUrl;

    String matchClassify;

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
                bean.setMatchName(element.text());
            }
            element = head.select("div.info h4").first();
            if (element != null) {
                bean.setMatchDate(element.text());
            }
            element = head.select("div.info h5").first();
            if (element != null) {
                bean.setMatchSite(element.text());
            }
            element = head.select("div.info div.prize").first();
            if (element != null) {
                bean.setMatchBonus(element.text());
            }
            element = head.select("div.logo-right img").first();
            if (element != null) {
                bean.setMatchIcon(element.attr("src"));
            }
            Element bgElement = doc.select("div#wrapper-background").first();
            if (bgElement != null) {
                String text = bgElement.attr("style");
                if (text != null) {
                    String[] urlAttr = text.split("\"");
                    if (urlAttr.length == 3) {
                        bean.setMatchIcon(urlAttr[1]);
                    }
                }
            }

        }
        return bean;
    }


}
