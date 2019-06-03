package com.leory.badminton.news.mvp.presenter;

import com.leory.badminton.news.mvp.contract.LiveContract;
import com.leory.badminton.news.mvp.model.bean.LiveBean;
import com.leory.commonlib.di.scope.FragmentScope;
import com.leory.commonlib.http.RxHandlerSubscriber;
import com.leory.commonlib.mvp.BasePresenter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

            String detailUrl=doc.select("div.live-scores-box-single a").first().attr("href");
            bean.setDetailUrl(detailUrl.replace("results/",""));
            Element data = doc.select("div.live-scores-box-wrap").first();
            String matchName = data.select("h2").first().text();
            bean.setMatchName(matchName);
            String matchDate = data.select("h3").first().text();
            bean.setMatchDate(matchDate);
            String flag = data.select("div.flag img").first().attr("src");
            bean.setCountryFlag(flag);
            Elements spans = data.select("div.country span");
            if (spans.size() == 2) {
                bean.setCountry(spans.get(1).text());
                bean.setCity(spans.get(0).text());
            }
        }
        return bean;

    }
}
