package com.leory.badminton.news.mvp.presenter;

import android.graphics.Color;

import com.leory.badminton.news.mvp.contract.MatchContract;
import com.leory.badminton.news.mvp.model.bean.MatchItemBean;
import com.leory.badminton.news.mvp.model.bean.MatchItemSection;
import com.leory.commonlib.di.scope.FragmentScope;
import com.leory.commonlib.http.RxHandlerSubscriber;
import com.leory.commonlib.mvp.BasePresenter;
import com.leory.commonlib.utils.LogUtils;
import com.leory.commonlib.utils.RxLifecycleUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Describe :赛事presenter
 * Author : leory
 * Date : 2019-05-19
 */
@FragmentScope
public class MatchPresenter extends BasePresenter<MatchContract.Model, MatchContract.View> {

    @Inject
    public MatchPresenter(MatchContract.Model model, MatchContract.View rootView) {
        super(model, rootView);
    }

    public void requestData(String year, String finish) {
        String state;
        if ("全部".equals(finish)) {
            state = "all";
        } else if ("已完成".equals(finish)) {
            state = "completed";
        } else {
            state = "remaining";
        }
        model.getMatchList(year, state)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> rootView.showLoading()).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(new RxHandlerSubscriber<String>() {

                    @Override
                    public void onNext(String o) {
                        parseHtmlResult(o);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        rootView.hideLoading();
                    }
                });
    }

    /**
     * 解析html
     *
     * @param html
     */
    private void parseHtmlResult(String html) {
        Observable.just(html)

                .flatMap((Function<String, ObservableSource<List<MatchItemSection>>>) s -> Observable.just(getMatchData(html)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> rootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(new Observer<List<MatchItemSection>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<MatchItemSection> matchItemSections) {
                        rootView.showMatchData(matchItemSections);
                    }

                    @Override
                    public void onError(Throwable e) {
                        rootView.showMatchData(new ArrayList<>());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private List<MatchItemSection> getMatchData(String html) {
        List<MatchItemSection> matchList = new ArrayList<>();
        if (html != null) {
            Document doc = Jsoup.parse(html);
            Elements months = doc.select("div.item-results");

            for (Element month : months) {
                String monthName = month.select("h2").first().text();
                LogUtils.d(TAG, monthName);
                matchList.add(new MatchItemSection(true, monthName));
                Elements list = month.select("tr[class=black],tr[class=gray1],tr[class=gray2],tr[class=gray3]");
                int i = 0;
                for (Element item : list) {

                    Elements tr = item.select("td");
                    if (tr.size() == 7) {

                        MatchItemBean bean = new MatchItemBean();
                        String matchClassify = tr.get(5).select("div[class=name]").first().text();//赛事类别
                        if ("HSBC BWF World Tour Super 300".equals(matchClassify) ||
                                "HSBC BWF World Tour Super 500".equals(matchClassify) ||
                                "HSBC BWF World Tour Super 750".equals(matchClassify) ||
                                "HSBC BWF World Tour Super 1000".equals(matchClassify)||
                                "HSBC BWF World Tour Finals".equals(matchClassify)||
                                "Grade 1 - Individual Tournaments".equals(matchClassify)||
                                "Grade 1 - Team Tournaments".equals(matchClassify)
                        ) {
                            bean.setMatchClassify(matchClassify);
                        } else {
                            continue;
                        }
                        String countryName = tr.get(1).select("div[class=country_code]").first().text();//国家简名
                        bean.setCountryName(countryName);
                        String countryFlagUrl = tr.get(1).select("img").first().attr("src");//国旗url
                        bean.setCountryFlagUrl(countryFlagUrl);
                        String cityName = tr.get(6).select("div").first().text();//城市名字
                        bean.setCityName(cityName);
                        String matchDay = tr.get(2).text();//比赛日期
                        bean.setMatchDay(monthName + "\n" + matchDay);
                        String matchName = tr.get(3).select("a").first().text();//赛事名称
                        bean.setMatchName(matchName);
                        bean.setMatchUrl(tr.get(3).select("a").first().attr("href"));

                        String matchBonus = tr.get(4).select("div").first().text();//赛事奖金
                        bean.setMatchBonus(matchBonus);
                        if (i % 2 == 0) {
                            bean.setBgColor(Color.WHITE);
                        } else {
                            bean.setBgColor(Color.parseColor("#f5f5f5"));
                        }
                        matchList.add(new MatchItemSection(bean));

                        i++;
                    }
                }
            }

        }
        return matchList;
    }
}
