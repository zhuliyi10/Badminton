package com.leory.badminton.news.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.leory.badminton.news.R;
import com.leory.commonlib.base.BaseActivity;

/**
 * Describe : 运动员详情
 * Author : leory
 * Date : 2019-06-10
 */
public class PlayerDetailActivity extends BaseActivity {
    public static void launch(Activity preActivity) {
        preActivity.startActivity(new Intent(preActivity, PlayerDetailActivity.class));
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_player_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}
