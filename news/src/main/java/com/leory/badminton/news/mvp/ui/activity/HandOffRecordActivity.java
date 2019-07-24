package com.leory.badminton.news.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.leory.badminton.news.R;
import com.leory.badminton.news.R2;
import com.leory.badminton.news.di.component.DaggerHandOffRecordComponent;
import com.leory.badminton.news.mvp.contract.HandOffRecordContract;
import com.leory.badminton.news.mvp.model.bean.HandOffBean;
import com.leory.badminton.news.mvp.presenter.HandOffRecordPresenter;
import com.leory.commonlib.base.BaseActivity;
import com.leory.commonlib.base.delegate.IComponent;
import com.leory.commonlib.di.component.AppComponent;
import com.leory.commonlib.di.scope.ActivityScope;
import com.leory.commonlib.utils.ToastUtils;
import com.leory.commonlib.widget.XSDToolbar;

import butterknife.BindView;

/**
 * Describe : 交手记录
 * Author : leory
 * Date : 2019-07-23
 */
@ActivityScope
public class HandOffRecordActivity extends BaseActivity<HandOffRecordPresenter> implements HandOffRecordContract.View {

    private static final String KEY_HAND_OFF_URL = "key_hand_off_url";
    @BindView(R2.id.toolbar)
    XSDToolbar toolbar;
    @BindView(R2.id.rcv)
    RecyclerView rcv;

    public static void launch(Activity preActivity, String handOffUrl) {
        preActivity.startActivity(new Intent(preActivity, HandOffRecordActivity.class).putExtra(KEY_HAND_OFF_URL, handOffUrl));
    }

    @Override
    public IComponent setupActivityComponent(IComponent component) {
        DaggerHandOffRecordComponent.builder()
                .view(this)
                .recordUrl(getIntent().getStringExtra(KEY_HAND_OFF_URL))
                .appComponent((AppComponent) component)
                .build()
                .inject(this);
        return null;
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_hand_off;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbar.setOnBackListener(() -> finish());

    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtils.showShort(message);
    }

    @Override
    public void showHandOffView(HandOffBean bean) {

    }
}
