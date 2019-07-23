package com.leory.badminton.news.mvp.presenter;

import com.leory.badminton.news.mvp.contract.HandOffRecordContract;
import com.leory.commonlib.di.scope.ActivityScope;
import com.leory.commonlib.http.RxHandlerSubscriber;
import com.leory.commonlib.mvp.BasePresenter;
import com.leory.commonlib.utils.RxLifecycleUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Describe : 交手记录presenter
 * Author : leory
 * Date : 2019-07-23
 */
@ActivityScope
public class HandOffRecordPresenter extends BasePresenter<HandOffRecordContract.Model,HandOffRecordContract.View> {
    @Inject
    String handOffUrl;
    @Inject
    public HandOffRecordPresenter(HandOffRecordContract.Model model, HandOffRecordContract.View rootView) {
        super(model, rootView);
        requestData();
    }
    private void requestData(){
        model.getHandOffRecords(handOffUrl)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> rootView.showLoading()).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> rootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(rootView))
                .subscribe(new RxHandlerSubscriber<String>() {

                    @Override
                    public void onNext(String o) {

                    }

                });
    }
}
