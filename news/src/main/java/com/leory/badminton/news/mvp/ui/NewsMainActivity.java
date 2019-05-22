package com.leory.badminton.news.mvp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.leory.badminton.news.R;
import com.leory.commonlib.base.BaseActivity;
import com.leory.commonlib.di.component.AppComponent;


public class NewsMainActivity extends BaseActivity {


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_news_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }


}
