package com.leory.badminton.news.mvp.presenter;

import android.util.Log;

import com.leory.badminton.news.mvp.contract.MatchDetailContract;
import com.leory.badminton.news.mvp.model.bean.MatchInfoBean;
import com.leory.badminton.news.mvp.ui.widget.againstFlow.AgainstFlowBean;
import com.leory.commonlib.di.scope.ActivityScope;
import com.leory.commonlib.http.RxHandlerSubscriber;
import com.leory.commonlib.mvp.BasePresenter;
import com.leory.commonlib.utils.LogUtils;

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
 * Describe : 赛事详情presenter
 * Author : zhuly
 * Date : 2019-05-27
 */
@ActivityScope
public class MatchDetailPresenter extends BasePresenter<MatchDetailContract.Model, MatchDetailContract.View> {

    private List<List<AgainstFlowBean>> againstData;
    private String currentMatchSchedule;
    String detailUrl;


    @Inject
    public MatchDetailPresenter(MatchDetailContract.Model model, MatchDetailContract.View rootView, String detailUrl) {
        super(model, rootView);
        this.detailUrl = detailUrl;
        requestData(null);
        requestMatchInfo();
    }

    private void requestMatchInfo() {
        if (detailUrl != null) {
            String requestUrl = detailUrl + "results/podium/";
            model.getMatchInfo(requestUrl)
                    .subscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new RxHandlerSubscriber<String>() {

                        @Override
                        public void onNext(String o) {
                            parseHtmlMatchInfo(o);
                        }

                    });
        }
    }

    public void requestData(String type) {
        String enType = "ms";
        if ("男单".equals(type)) {
            enType = "ms";
        } else if ("女单".equals(type)) {
            enType = "ws";
        } else if ("男双".equals(type)) {
            enType = "md";
        } else if ("女双".equals(type)) {
            enType = "wd";
        } else if ("混双".equals(type)) {
            enType = "xd";
        }

        if (detailUrl != null) {
            String requestUrl = detailUrl + "result/draw/" + enType;
            model.getMatchDetail(requestUrl)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(disposable -> rootView.showLoading()).subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> rootView.hideLoading())
                    .subscribe(new RxHandlerSubscriber<String>() {

                        @Override
                        public void onNext(String o) {
                            parseHtmlResult(o, type);
                        }

                    });
        }
    }

    /**
     * 选择比赛进度
     *
     * @param scheduleText
     */
    public void selectSchedule(String scheduleText) {
        this.currentMatchSchedule = scheduleText;

        if (scheduleText.equals("1/16决赛")) {
            showAgainView(0);
        } else if (scheduleText.equals("1/8决赛")) {
            showAgainView(1);
        } else if (scheduleText.equals("1/4决赛")) {
            showAgainView(2);
        } else if (scheduleText.equals("半决赛")) {
            showAgainView(3);
        } else if (scheduleText.equals("决赛")) {
            showAgainView(4);
        }

    }

    private void showAgainView(int level) {
        if (againstData != null) {
            List<List<AgainstFlowBean>> selectData = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                selectData.add(againstData.get(i + level));
            }
            if (level + 2 < againstData.size())
                for (int i = 0; i < againstData.get(level + 2).size(); i++) {
                    selectData.add(new ArrayList<>());
                }
            rootView.showAgainstView(selectData);
        }

    }

    private void parseHtmlMatchInfo(String html) {
        Observable.just(html)

                .flatMap((Function<String, ObservableSource<MatchInfoBean>>) s -> Observable.just(getMatchInfo(html)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
                        LogUtils.d(TAG,e.getMessage());
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
            Element head = doc.select("div.box-results-tournament").first();
            Element element = head.select("div.info h2").first();
            bean.setMatchName(element.text());
            bean.setMatchDate(head.select("div.info h4").first().text());
            bean.setMatchSite(head.select("div.info h5").first().text());
            bean.setMatchBonus(head.select("div.info div.prize").first().text());
            bean.setMatchIcon(head.select("div.logo-right img").first().attr("src"));
            Element bgElement = doc.select("div#wrapper-background").first();
            String text = bgElement.attr("style");
            if (text != null) {
                String[] urlAttr = text.split("\"");
                if (urlAttr.length == 3) {
                    bean.setMatchIcon(urlAttr[1]);
                }
            }

        }
        return bean;
    }

    /**
     * 解析html
     *
     * @param html
     */
    private void parseHtmlResult(String html, String type) {
        Observable.just(html)

                .flatMap((Function<String, ObservableSource<List<List<AgainstFlowBean>>>>) s -> Observable.just(getAgainstData(html, type)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<List<AgainstFlowBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<List<AgainstFlowBean>> data) {
                        againstData = data;
                        selectSchedule(currentMatchSchedule);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private List<List<AgainstFlowBean>> getAgainstData(String html, String type) {
        boolean isDouble = isDouble(type);
        List<List<AgainstFlowBean>> data = new ArrayList<>();
        if (html != null) {
            Document doc = Jsoup.parse(html);
            List<AgainstFlowBean> enter16 = new ArrayList<>();
            List<AgainstFlowBean> enter8 = new ArrayList<>();
            List<AgainstFlowBean> enter4 = new ArrayList<>();
            List<AgainstFlowBean> enter2 = new ArrayList<>();
            List<AgainstFlowBean> enter1 = new ArrayList<>();
            List<AgainstFlowBean> champion = new ArrayList<>();
            Elements trs = doc.select("tr");
            for (int i = 2; i < trs.size(); i++) {
                //1/16决赛
                if (i % 2 == 0) {
                    AgainstFlowBean bean = new AgainstFlowBean();
                    Element td = trs.get(i).select("td").get(1);
                    setItemData(bean, isDouble, td);
                    enter16.add(bean);

                }
                //1/8决赛
                if (i % 4 == 3) {
                    AgainstFlowBean bean = new AgainstFlowBean();
                    Element td = trs.get(i).select("td#col-2").first();
                    setItemData(bean, isDouble, td);
                    enter8.add(bean);
                }
                if (i % 4 == 0) {//比分
                    Element element = trs.get(i).select("td div.draw-score").first();
                    if (element != null) {
                        enter8.get(enter8.size() - 1).setScore(element.text());
                    }
                }

                //1/4决赛
                if (i % 8 == 5) {
                    AgainstFlowBean bean = new AgainstFlowBean();
                    Element td = trs.get(i).select("td#col-3").first();
                    setItemData(bean, isDouble, td);
                    enter4.add(bean);
                }
                if (i % 8 == 6) {
                    Element element = trs.get(i).select("td div.draw-score").first();
                    if (element != null) {
                        enter4.get(enter4.size() - 1).setScore(element.text());
                    }
                }

                //半决赛
                if (i % 16 == 9) {
                    AgainstFlowBean bean = new AgainstFlowBean();
                    Element td = trs.get(i).select("td#col-4").first();
                    setItemData(bean, isDouble, td);
                    enter2.add(bean);
                }
                if (i % 16 == 10) {
                    Element element = trs.get(i).select("td div.draw-score").first();
                    if (element != null) {
                        enter2.get(enter2.size() - 1).setScore(element.text());
                    }
                }

                //决赛
                if (i % 32 == 17) {
                    AgainstFlowBean bean = new AgainstFlowBean();
                    Element td = trs.get(i).select("td#col-5").first();
                    setItemData(bean, isDouble, td);
                    enter1.add(bean);
                }
                if (i % 32 == 18) {
                    Element element = trs.get(i).select("td div.draw-score").first();
                    if (element != null) {
                        enter1.get(enter1.size() - 1).setScore(element.text());
                    }
                }
                //冠军
                if (i % 64 == 33) {
                    AgainstFlowBean bean = new AgainstFlowBean();
                    Element td = trs.get(i).select("td#col-6").first();
                    setItemData(bean, isDouble, td);
                    champion.add(bean);
                }
                if (i % 64 == 34) {
                    Element element = trs.get(i).select("td div.draw-score").first();
                    if (element != null) {
                        champion.get(champion.size() - 1).setScore(element.text());
                    }
                }
            }

            data.add(enter16);
            data.add(enter8);
            data.add(enter4);
            data.add(enter2);
            data.add(enter1);
            data.add(champion);
        }


        return data;
    }

    private void setItemData(AgainstFlowBean bean, boolean isDouble, Element td) {
        bean.setDouble(isDouble);
        Element element;
        if (isDouble) {
            element = td.select("div.draw-player1-wrap img").first();
            if (element != null) {
                bean.setIcon1(element.attr("src"));
            }
            element = td.select("div.draw-player1-wrap a").first();
            if (element != null) {
                bean.setName1(element.text());
            }
            element = td.select("div.draw-player2-wrap img").first();
            if (element != null) {
                bean.setIcon2(element.attr("src"));
            }
            element = td.select("div.draw-player2-wrap a").first();
            if (element != null) {
                bean.setName2(element.text());
            }
        } else {
            element = td.select("div.draw-player1-wrap img").first();
            if (element != null) {
                bean.setIcon1(element.attr("src"));
            }
            element = td.select("div.draw-player1-wrap a").first();
            if (element != null) {
                bean.setName1(element.text());
            }
        }
    }

    /**
     * 是否是双打
     *
     * @param type
     * @return
     */
    private boolean isDouble(String type) {
        if ("男双".equals(type) || "女双".equals(type) || "混双".equals(type)) {
            return true;
        }
        return false;
    }
}
