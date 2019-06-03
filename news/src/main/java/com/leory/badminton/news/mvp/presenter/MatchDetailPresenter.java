package com.leory.badminton.news.mvp.presenter;

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

    private List<List<AgainstFlowBean>> againstData;
    private String currentMatchSchedule;
    String detailUrl;
    String matchClassify;

    @Inject
    public MatchDetailPresenter(MatchDetailContract.Model model, MatchDetailContract.View rootView, @Named("detail_url") String detailUrl, @Named("match_classify") String matchClassify) {
        super(model, rootView);
        this.detailUrl = detailUrl;
        this.matchClassify = matchClassify;
        requestData(null);
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

    public void requestData(String type) {


        if (detailUrl != null) {
            String requestUrl;

            if (isGroup()) {
                requestUrl = detailUrl + "draw/group-1";
            } else {
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

                if ("Grade 1 - Individual Tournaments".equals(matchClassify)) {
                    requestUrl = detailUrl + "draw/" + enType;
                } else {
                    requestUrl = detailUrl + "result/draw/" + enType;
                }
            }

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
    public void selectSchedule(String scheduleText, int pos) {
        this.currentMatchSchedule = scheduleText;
        showAgainView(pos);
    }

    private void showAgainView(int level) {
        if (againstData != null) {
            List<List<AgainstFlowBean>> selectData = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                if (i + level < againstData.size()) {
                    selectData.add(againstData.get(i + level));
                }
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
                        rootView.showMatchSchedule(data.size() - 1);

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
            Elements trs = doc.select("tr");
            if (trs.size() > 2) {
                int colNum = (int) (Math.log(trs.size() - 2) / Math.log(2));
                for (int i = 0; i < colNum; i++) {
                    data.add(new ArrayList<>());
                }
                for (int i = 2; i < trs.size(); i++) {
                    if (colNum > 0) {
                        if (i % 2 == 0) {
                            AgainstFlowBean bean = new AgainstFlowBean();
                            Element td = trs.get(i).select("td").get(1);
                            setItemData(bean, isDouble, td);
                            data.get(0).add(bean);

                        }
                    }
                    if (colNum > 1) {
                        if (i % 4 == 3) {
                            AgainstFlowBean bean = new AgainstFlowBean();
                            Element td = trs.get(i).select("td#col-2").first();
                            setItemData(bean, isDouble, td);
                            data.get(1).add(bean);
                        }
                        if (i % 4 == 0) {//比分
                            Element element = trs.get(i).select("td div.draw-score").first();
                            if (element != null) {
                                data.get(1).get(data.get(1).size() - 1).setScore(element.text());
                            }
                        }
                    }
                    if (colNum > 2) {
                        if (i % 8 == 5) {
                            AgainstFlowBean bean = new AgainstFlowBean();
                            Element td = trs.get(i).select("td#col-3").first();
                            setItemData(bean, isDouble, td);
                            data.get(2).add(bean);
                        }
                        if (i % 8 == 6) {
                            Element element = trs.get(i).select("td div.draw-score").first();
                            if (element != null) {
                                data.get(2).get(data.get(2).size() - 1).setScore(element.text());
                            }
                        }
                    }
                    if (colNum > 3) {
                        if (i % 16 == 9) {
                            AgainstFlowBean bean = new AgainstFlowBean();
                            Element td = trs.get(i).select("td#col-4").first();
                            setItemData(bean, isDouble, td);
                            data.get(3).add(bean);
                        }
                        if (i % 16 == 10) {
                            Element element = trs.get(i).select("td div.draw-score").first();
                            if (element != null) {
                                data.get(3).get(data.get(3).size() - 1).setScore(element.text());
                            }
                        }
                    }
                    if (colNum > 4) {
                        if (i % 32 == 17) {
                            AgainstFlowBean bean = new AgainstFlowBean();
                            Element td = trs.get(i).select("td#col-5").first();
                            setItemData(bean, isDouble, td);
                            data.get(4).add(bean);
                        }
                        if (i % 32 == 18) {
                            Element element = trs.get(i).select("td div.draw-score").first();
                            if (element != null) {
                                data.get(4).get(data.get(4).size() - 1).setScore(element.text());
                            }
                        }
                    }
                    if (colNum > 5) {
                        if (i % 64 == 33) {
                            AgainstFlowBean bean = new AgainstFlowBean();
                            Element td = trs.get(i).select("td#col-6").first();
                            setItemData(bean, isDouble, td);
                            data.get(5).add(bean);
                        }
                        if (i % 64 == 34) {
                            Element element = trs.get(i).select("td div.draw-score").first();
                            if (element != null) {
                                data.get(5).get(data.get(5).size() - 1).setScore(element.text());
                            }
                        }
                    }
                    if (colNum > 6) {
                        if (i % 128 == 65) {
                            AgainstFlowBean bean = new AgainstFlowBean();
                            Element td = trs.get(i).select("td#col-6").first();
                            setItemData(bean, isDouble, td);
                            data.get(6).add(bean);
                        }
                        if (i % 128 == 66) {
                            Element element = trs.get(i).select("td div.draw-score").first();
                            if (element != null) {
                                data.get(6).get(data.get(6).size() - 1).setScore(element.text());
                            }
                        }
                    }
                }

            }
        }


        return data;
    }


    private void setItemData(AgainstFlowBean bean, boolean isDouble, Element td) {
        bean.setDouble(isDouble);
        Element element;
        if (isDouble) {
            element = td.select("div.draw-player1-wrap img").first();
            if (element != null) {
                bean.setIcon1(addHttps(element.attr("src")));
            }
            element = td.select("div.draw-player1-wrap a").first();
            if (element != null) {
                bean.setName1(element.text());
            }
            element = td.select("div.draw-player2-wrap img").first();
            if (element != null) {
                bean.setIcon2(addHttps(element.attr("src")));
            }
            element = td.select("div.draw-player2-wrap a").first();
            if (element != null) {
                bean.setName2(element.text());
            }
        } else {
            element = td.select("div.draw-player1-wrap img").first();
            if (element != null) {
                bean.setIcon1(addHttps(element.attr("src")));
            }
            element = td.select("div.draw-player1-wrap a").first();
            if (element == null) {
                element = td.select("div.draw-player1-wrap div.draw-name").first();
            }
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

    private String addHttps(String img) {
        if (img != null) {
            if (!img.startsWith("https:")) {
                return "https:" + img;
            } else {
                return img;
            }
        }
        return null;
    }
}
