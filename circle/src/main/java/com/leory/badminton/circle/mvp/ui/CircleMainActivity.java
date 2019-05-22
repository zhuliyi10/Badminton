package com.leory.badminton.circle.mvp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.leory.badminton.circle.R;
import com.leory.commonlib.base.BaseActivity;
import com.leory.commonlib.di.component.AppComponent;

/**
 * Describe : 圈子activity
 * Author : zhuly
 * Date : 2019-05-22
 */
public class CircleMainActivity extends BaseActivity {
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_circle_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}
