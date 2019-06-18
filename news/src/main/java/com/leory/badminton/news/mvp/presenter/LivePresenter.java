package com.leory.badminton.news.mvp.presenter;

import android.text.TextUtils;

import com.leory.badminton.news.app.utils.FileHashUtils;
import com.leory.badminton.news.mvp.contract.LiveContract;
import com.leory.badminton.news.mvp.model.bean.LiveBean;
import com.leory.badminton.news.mvp.model.bean.LiveDetailBean;
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
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Describe : 直播presenter
 * Author : leory
 * Date : 2019-06-03
 */
@FragmentScope
public class LivePresenter extends BasePresenter<LiveContract.Model, LiveContract.View> {
    private HashMap<String, String> matchNameMap;
    private HashMap<String, String> MonthMap;
    private HashMap<String, String> playerNameMap;
    @Inject
    public LivePresenter(LiveContract.Model model, LiveContract.View rootView) {
        super(model, rootView);
    }

    public void requestData() {
        model.getLiveMatch()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> rootView.showLoading()).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    rootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(new RxHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        parseHtmlData(s);
                    }
                });
    }


    private void parseHtmlData(String html) {
        Observable.just(html)

                .flatMap((Function<String, ObservableSource<LiveBean>>) s -> Observable.just(getLiveData(html)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(new Observer<LiveBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LiveBean bean) {
                        rootView.showLiveData(bean);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private LiveBean getLiveData(String html) {
        LiveBean bean = new LiveBean();
        if (html != null) {
            Document doc = Jsoup.parse(html);

            String detailUrl = doc.select("div.live-scores-box-single a").first().attr("href");
            if (detailUrl.endsWith("live/")) {
                requestLiveUrl(detailUrl);

                detailUrl = detailUrl.replace("live/", "");

            } else {
                detailUrl = detailUrl.replace("results/", "");
            }
            bean.setDetailUrl(detailUrl);
            Element data = doc.select("div.live-scores-box-wrap").first();
            String matchName = data.select("h2").first().text();
            bean.setMatchName(translateMatchName(matchName));
            String matchDate = data.select("h3").first().text();
            bean.setMatchDate(translateMonth(matchDate));
            String flag = data.select("div.flag img").first().attr("src");
            bean.setCountryFlag(flag);
            Elements spans = data.select("div.country span");
            if (spans.size() == 2) {
                bean.setCountry(spans.get(1).text());
                bean.setCity(spans.get(0).text());
            }
            bean.setMatchIcon(data.select("div.cat-logo img").first().attr("src"));
        }
        return bean;

    }


    private void requestLiveUrl(String detailUrl) {
        model.getLiveUrl(detailUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(new RxHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        String liveUrl = getLiveUrl(s);
                        if (liveUrl != null) {
                            requestLive(liveUrl);
                        }
                    }
                });
    }

    private String getLiveUrl(String html) {
        Matcher m = Pattern.compile("document.location='https://bwfworldtour.bwfbadminton.com/(.*)?match=(.*)&tab=live';").matcher(html);
        if (m.find()) {
            String group = m.group();
            m = Pattern.compile("https(.*)tab=live").matcher(group);
            if (m.find()) {
                String liveUrl = m.group();
                LogUtils.d(TAG, liveUrl);
                return liveUrl;
            }
        }
        return null;
    }

    /**
     * 请求直播中数据
     *
     * @param liveUrl
     */
    private void requestLive(String liveUrl) {

        Observable.interval(0, 5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<Long, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Long aLong) throws Exception {
                        return model.getLiveDetail(liveUrl);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(new RxHandlerSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        parseLiveDetail(s);
                    }
                });
    }

    private void parseLiveDetail(String html) {
        Observable.just(html)

                .flatMap((Function<String, ObservableSource<List<LiveDetailBean>>>) s -> Observable.just(getLiveDetails(html)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(new Observer<List<LiveDetailBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<LiveDetailBean> list) {
                        rootView.showLiveDetail(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private List<LiveDetailBean> getLiveDetails(String html) {
        List<LiveDetailBean> data = new ArrayList<>();
        if (html != null) {
            Document doc = Jsoup.parse(html);
            Elements lis = doc.select("li");
            if (lis != null) {
                for (Element li : lis) {
                    LiveDetailBean bean = new LiveDetailBean();
                    bean.setDetailUrl(li.select("a").first().attr("href"));
                    bean.setType(translateType(li.select("div.round strong").first().text()));
                    bean.setField(li.select("div.court").first().text().replace("Court", "场地"));
                    Element element = li.select("div.time").first();
                    if (element != null) {
                        bean.setTime(element.text());
                    }


                    //player1
                    Elements player1s = li.select("div.player1 span");
                    bean.setPlayer1(translatePlayerName(player1s.first().text()));
                    if (player1s.size() == 2) {
                        bean.setPlayer12(translatePlayerName(player1s.get(1).text()));
                    }

                    //flag1
                    Elements flags = li.select("div.flag1 img");

                    String flag1 = flags.first().attr("src");
                    if (flag1 != null && !flag1.startsWith("https:")) {
                        flag1 = "https:" + flag1;
                    }
                    bean.setFlag1(flag1);
                    if (flags.size() == 2) {
                        flag1 = flags.get(1).attr("src");
                        if (flag1 != null && !flag1.startsWith("https:")) {
                            flag1 = "https:" + flag1;
                        }
                        bean.setFlag12(flag1);
                    }

                    //player2
                    Elements player2s = li.select("div.player2 span");
                    bean.setPlayer2(translatePlayerName(player2s.first().text()));
                    if (player2s.size() == 2) {
                        bean.setPlayer22(translatePlayerName(player2s.get(1).text()));
                    }

                    //flag2
                    Elements flag2s = li.select("div.flag2 img");
                    String flag2 = flag2s.first().attr("src");
                    if (flag2 != null && !flag2.startsWith("https:")) {
                        flag2 = "https:" + flag2;
                    }
                    bean.setFlag2(flag2);
                    if (flag2s.size() == 2) {
                        flag2 = flag2s.get(1).attr("src");
                        if (flag2 != null && !flag2.startsWith("https:")) {
                            flag2 = "https:" + flag2;
                        }
                        bean.setFlag22(flag2);
                    }

                    bean.setVs(li.select("div.vs").first().text());
                    bean.setLeftDot(li.select("div.score-serve-1").first().text());
                    bean.setRightDot(li.select("div.score-serve-2").first().text());
                    bean.setScore(li.select("div.score").first().text());
                    data.add(bean);
                }
            }
        }
        return data;
    }


    private String translateType(String type) {
        if ("MS".equals(type)) {
            return "男单";
        } else if ("WS".equals(type)) {
            return "女单";
        } else if ("MD".equals(type)) {
            return "男双";
        } else if ("WD".equals(type)) {
            return "女双";
        } else if ("XD".equals(type)) {
            return "混双";
        }
        return type;
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
    private String translatePlayerName(String key) {
        if(playerNameMap==null){
            playerNameMap=FileHashUtils.getPlayerName();
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
