package com.leory.badminton.news.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.leory.badminton.news.R;
import com.leory.commonlib.base.BaseActivity;
import com.leory.commonlib.di.component.AppComponent;

/**
 * Describe : 赛事详情activity
 * Author : leory
 * Date : 2019-05-23
 */
public class MatchDetailActivity extends BaseActivity {


    public static void launch(Activity preActivity) {
        preActivity.startActivity(new Intent(preActivity, MatchDetailActivity.class));
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_match_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }


}
