package com.leory.badminton.news.mvp.presenter;

import android.text.TextUtils;

import com.leory.badminton.news.app.utils.FileHashUtils;
import com.leory.badminton.news.mvp.contract.PlayerContract;
import com.leory.badminton.news.mvp.model.bean.PlayerMatchBean;
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
 * Describe : 运动员赛果
 * Author : leory
 * Date : 2019-06-11
 */
@FragmentScope
public class PlayerMatchPresenter extends BasePresenter<PlayerContract.Model, PlayerContract.MatchView> {
    private String playerUrl;
    private HashMap<String, String> matchNameMap;
    private HashMap<String, String> playerNameMap;
    @Inject
    public PlayerMatchPresenter(PlayerContract.Model model, PlayerContract.MatchView rootView, @Named("player_url") String playerUrl) {
        super(model, rootView);
        this.playerUrl = playerUrl;
    }

    public void requestData(String year) {
        if (playerUrl != null) {
            String requestUrl = playerUrl + "/tournament-results/";
            model.getPlayerMatches(requestUrl, year)
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
                .flatMap((Function<String, ObservableSource<List<PlayerMatchBean>>>) s -> Observable.just(getPlayerMatchData(html)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(new Observer<List<PlayerMatchBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<PlayerMatchBean> data) {
                        rootView.showMatchData(data);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private List<PlayerMatchBean> getPlayerMatchData(String html) {
        List<PlayerMatchBean> data = new ArrayList<>();
        if (html != null) {
            Document doc = Jsoup.parse(html);
            Elements matches = doc.select("div.box-profile-tournament");
            Elements roundHeads = doc.select("div.title-tournament-matches");
            Elements rounds = doc.select("div.tournament-matches");
            if (matches.size() != rounds.size() && roundHeads.size() == rounds.size()) {
                for (int i = 0; i < roundHeads.size(); ) {
                    String title = roundHeads.get(i).select("a").first().text();
                    if (title.contains("循环配置")) {
                        roundHeads.remove(i);
                        rounds.remove(i);
                        continue;
                    }
                    i++;
                }
            }
            if (matches.size() == rounds.size()) {
                for (int i = 0; i < matches.size(); i++) {
                    PlayerMatchBean matchBean = new PlayerMatchBean();
                    Element match = matches.get(i);
                    PlayerMatchBean.MatchInfo matchInfo = new PlayerMatchBean.MatchInfo();
                    matchInfo.setMatchUrl(match.select("h2 a").first().attr("href"));
                    matchInfo.setName(translateMatchName(match.select("h2 a").first().text()));
                    matchInfo.setCategory(match.select("h3").first().text());
                    matchInfo.setDate(match.select("h4").first().text());
                    Element bonus=match.select("div.prize").first();
                    if(bonus!=null) {
                        matchInfo.setBonus(bonus.text());
                    }
                    matchBean.setMatchInfo(matchInfo);
                    Element round = rounds.get(i);
                    List<PlayerMatchBean.ResultRound> roundList = new ArrayList<>();
                    Elements rows = round.select("div.col-1-2 div.tournament-matches-row");
                    for (Element row : rows) {
                        PlayerMatchBean.ResultRound resultRound = new PlayerMatchBean.ResultRound();
                        resultRound.setRound(row.select("div.player-result-round").first().text());
                        //player1
                        Elements name1 = row.select("div.player-result-name-1 div.name");
                        if (name1.size() > 0) {
                            resultRound.setPlayer1(translatePlayerName(name1.get(0).select("a").first().text()));
                        }
                        if (name1.size() > 1) {
                            resultRound.setPlayer12(translatePlayerName(name1.get(1).select("a").first().text()));
                        }
                        Elements flag1 = row.select("div.player-result-flag-1 div.flag");
                        if (flag1.size() > 0) {
                            resultRound.setFlag1(flag1.get(0).select("img").first().attr("src"));
                        }
                        if (flag1.size() > 1) {
                            resultRound.setFlag12(flag1.get(1).select("img").first().attr("src"));
                        }
                        //player1
                        Elements name2 = row.select("div.player-result-name-2 div.name");
                        if (name2.size() > 0) {
                            resultRound.setPlayer2(translatePlayerName(name2.get(0).select("a").first().text()));
                        }
                        if (name2.size() > 1) {
                            resultRound.setPlayer22(translatePlayerName(name2.get(1).select("a").first().text()));
                        }
                        Elements flag2 = row.select("div.player-result-flag-2 div.flag");
                        if (flag2.size() > 0) {
                            resultRound.setFlag2(flag2.get(0).select("img").first().attr("src"));
                        }
                        if (flag2.size() > 1) {
                            resultRound.setFlag22(flag2.get(1).select("img").first().attr("src"));
                        }

                        resultRound.setVs(row.select("div.player-result-vs").first().text());
                        resultRound.setScore(row.select("div.player-result-win span").first().text());
                        resultRound.setDuration("时长：" + round.select("div.player-result-duration div.timer").first().text());
                        roundList.add(resultRound);
                    }
                    matchBean.setRounds(roundList);
                    data.add(matchBean);
                }
            }
        }
        return data;
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
