package com.zhuliyi.badminton;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zhulilyi.badminton.R;
import com.zhuliyi.commonlib.base.BaseActivity;
import com.zhuliyi.commonlib.utils.AppUtils;
import com.zhuliyi.interactions.RouterHub;

import butterknife.OnClick;

@Route(path = RouterHub.APP_MAINACTIVITY)
public class MainActivity extends BaseActivity {

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
    }


    @OnClick({R.id.btn1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                AppUtils.navigation(this, RouterHub.VIDEO_VIDEOMAINACTIVITY);
                break;
        }
    }

}
