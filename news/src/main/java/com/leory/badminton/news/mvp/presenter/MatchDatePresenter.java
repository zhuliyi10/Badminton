package com.leory.badminton.news.mvp.presenter;

import android.text.TextUtils;

import com.leory.badminton.news.app.utils.FileHashUtils;
import com.leory.badminton.news.mvp.contract.MatchDetailContract;
import com.leory.badminton.news.mvp.model.MatchDetailModel;
import com.leory.badminton.news.mvp.model.bean.MatchDateBean;
import com.leory.badminton.news.mvp.model.bean.MatchTabDateBean;
import com.leory.commonlib.di.scope.FragmentScope;
import com.leory.commonlib.http.RxHandlerSubscriber;
import com.leory.commonlib.mvp.BasePresenter;
import com.leory.commonlib.utils.RxLifecycleUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
 * Describe : 比赛赛程presenter
 * Author : leory
 * Date : 2019-06-06
 */
@FragmentScope
public class MatchDatePresenter extends BasePresenter<MatchDetailModel, MatchDetailContract.MatchDateView> {
    @Inject
    @Named("player_name")
    HashMap<String, String> playerNameMap;
    @Inject
    List<MatchTabDateBean> dateBeans;
    @Inject
    @Named("country")
    String country;

    private HashMap<String, String> timeDifferMap;

    @Inject
    public MatchDatePresenter(MatchDetailModel model, MatchDetailContract.MatchDateView rootView) {
        super(model, rootView);
    }

    public void requestPosition(int pos) {
        if (dateBeans != null && dateBeans.size() > pos) {
            model.getMatchDate(dateBeans.get(pos).getLink())
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

                .flatMap((Function<String, ObservableSource<List<MatchDateBean>>>) s -> Observable.just(getMatchDateData(html)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(new Observer<List<MatchDateBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<MatchDateBean> data) {
                        rootView.showDateData(data);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private List<MatchDateBean> getMatchDateData(String html) {
        List<MatchDateBean> data = new ArrayList<>();
        if (html != null) {
            Document doc = Jsoup.parse(html);
            Elements lis = doc.select("li.draw-MS,li.draw-WS,li.draw-MD,li.draw-WD,li.draw-XD");
            if (lis != null) {
                for (int i = 0; i < lis.size(); i = i + 1) {
                    Element li = lis.get(i);
                    MatchDateBean bean = new MatchDateBean();
                    bean.setType(translateType(li.select("div.round").first().text().replace(" - Qualification", "")));
                    String court = li.select("span.round-court").first().text();
                    if (TextUtils.isEmpty(court)) {
                        bean.setField("流水场");
                    } else if (TextUtils.isDigitsOnly(court)) {
                        bean.setField("场地 " + court);
                    } else {

                        bean.setField(court.replace("Quaycentre", "场地").replace("Court", "场地"));
                    }

                    bean.setTime(getChineseTime(translateTime(li.select("div.time").first().text())));

                    //player1
                    Element player11 = li.select("div.player1-wrap").first();
                    if (player11 != null) {
                        bean.setPlayer1(translatePlayerName(player11.select("div.player1").first().text()));
                        bean.setFlag1(player11.select("div.flag img").attr("src"));
                    }
                    //player12
                    Element player12 = li.select("div.player2-wrap").first();
                    if (player12 != null) {
                        bean.setPlayer12(translatePlayerName(player12.select("div.player2").first().text()));
                        bean.setFlag12(player12.select("div.flag img").attr("src"));
                    }
                    //player2
                    Element player21 = li.select("div.player3-wrap").first();
                    if (player21 != null) {
                        bean.setPlayer2(translatePlayerName(player21.select("div.player3").first().text()));
                        bean.setFlag2(player21.select("div.flag img").attr("src"));
                    }
                    //player22
                    Element player22 = li.select("div.player4-wrap").first();
                    if (player22 != null) {
                        bean.setPlayer22(translatePlayerName(player22.select("div.player4").first().text()));
                        bean.setFlag22(player22.select("div.flag img").attr("src"));
                    }


                    bean.setVs(li.select("div.vs").first().text());
                    bean.setScore(li.select("div.score").first().text());
                    Element durationElement = li.select("div.timer1 span").first();
                    if (durationElement != null) {
                        if ("0:00".equals(durationElement.text())) {
                            bean.setDuration("未开始");
                        } else {
                            bean.setDuration("时长：" + durationElement.text());
                        }

                    } else {
                        bean.setDuration("比赛中");
                    }
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

    private String translatePlayerName(String key) {
        String playerName = key.replaceAll("\\[\\d+\\]", "").trim();
        String value = playerNameMap.get(playerName);
        if (TextUtils.isEmpty(value)) {
            return key;
        } else {
            return key.replace(playerName, value);
        }
    }

    private String translateTime(String time) {
        String temp = time.replace("Starting at", "开始于");
        return temp.replace("Followed by", "紧跟着");
    }

    private int getTimeDiffer() {
        if (timeDifferMap == null) {
            timeDifferMap = FileHashUtils.getTimeDiffer();
        }
        String value = timeDifferMap.get(country);
        if (!TextUtils.isEmpty(value)) {
            return Integer.parseInt(value);
        }
        return 0;
    }

    private String getChineseTime(String time) {
        if (time == null) return time;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            Date date = sdf.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.HOUR_OF_DAY, -getTimeDiffer());
            return sdf.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
}
