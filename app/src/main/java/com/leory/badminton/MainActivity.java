package com.leory.badminton;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.leory.commonlib.base.BaseActivity;
import com.leory.commonlib.di.component.AppComponent;
import com.leory.commonlib.utils.AppUtils;
import com.leory.interactions.RouterHub;

import butterknife.OnClick;

@Route(path = RouterHub.APP_MAINACTIVITY)
public class MainActivity extends BaseActivity {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
    }


    @OnClick({R.id.btn1,R.id.btn2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                AppUtils.navigation(this, RouterHub.VIDEO_VIDEOMAINACTIVITY);
                break;
            case R.id.btn2:
                AppUtils.navigation(this, RouterHub.NEWS_NEWSMAINACTIVITY);
                break;
        }
    }

}
