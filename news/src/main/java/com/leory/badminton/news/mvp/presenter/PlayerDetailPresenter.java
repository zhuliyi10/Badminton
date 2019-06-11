package com.leory.badminton.news.mvp.presenter;

import com.leory.badminton.news.mvp.contract.PlayerContract;
import com.leory.badminton.news.mvp.model.bean.PlayerDetailBean;
import com.leory.badminton.news.mvp.model.bean.PlayerInfoBean;
import com.leory.commonlib.di.scope.ActivityScope;
import com.leory.commonlib.http.RxHandlerSubscriber;
import com.leory.commonlib.mvp.BasePresenter;
import com.leory.commonlib.utils.RxLifecycleUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
 * Describe : 运动员详情presenter
 * Author : leory
 * Date : 2019-06-11
 */
@ActivityScope
public class PlayerDetailPresenter extends BasePresenter<PlayerContract.Model, PlayerContract.View> {
    private String playerUrl;

    @Inject
    public PlayerDetailPresenter(PlayerContract.Model model, PlayerContract.View rootView, @Named("player_url") String playerUrl) {
        super(model, rootView);
        this.playerUrl = playerUrl;
        requestData();
    }

    private void requestData() {
        if (playerUrl != null) {
            model.getPlayerDetail(playerUrl)
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

                .flatMap((Function<String, ObservableSource<PlayerDetailBean>>) s -> Observable.just(getPlayerDetailData(html)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(new Observer<PlayerDetailBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PlayerDetailBean data) {
                        rootView.showPlayerDetail(data);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private PlayerDetailBean getPlayerDetailData(String html) {
        PlayerDetailBean bean = new PlayerDetailBean();
        if (html != null) {
            Document doc = Jsoup.parse(html);
            Element info = doc.select("div.playertop-intro").first();
            bean.setHead(info.select("div.player-photo img").first().attr("src"));
            bean.setFlag(info.select("div.player-profile-country-wrap img").first().attr("src"));
            bean.setName(info.select("div.player-profile-country-wrap h2").first().text());
            Element ranking = info.select("div.player-world-rank div.ranking-number").first();
            if (ranking != null) {
                bean.setRankNum(ranking.text());
                bean.setType(info.select("div.player-world-rank div.ranking-title").first().text());
            } else {
                Elements trs = info.select("div.player-world-rank tr");
                if (trs.size() == 2) {
                    Elements ranks = trs.get(0).select("td");
                    Elements types = trs.get(1).select("td");
                    bean.setRankNum(ranks.get(0).text());
                    bean.setType(types.get(0).text());
                    bean.setRankNum2(ranks.get(1).text());
                    bean.setType2(types.get(1).text());
                }
            }
            bean.setWinNum(info.select("div.player-wins span.large").first().text());
            bean.setAge(info.select("div.player-age span.large").first().text());

            Element stats=doc.select("div.player-stats").first();
            if(stats!=null){
                PlayerInfoBean infoBean=new PlayerInfoBean();
                Elements pList=stats.select("p");
                StringBuffer buffer=new StringBuffer();
                for (Element p:pList){
                    buffer.append(p.text());
                    buffer.append("\n");
                }
                infoBean.setStats(buffer.toString());
                bean.setInfoBean(infoBean);
            }
        }
        return bean;
    }
}
