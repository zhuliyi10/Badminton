package com.leory.badminton.mine.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.leory.badminton.mine.R;
import com.leory.badminton.mine.R2;
import com.leory.badminton.mine.mvp.model.sp.AccountSp;
import com.leory.commonlib.base.BaseActivity;
import com.leory.commonlib.widget.XSDToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Describe : 设置页面
 * Author : leory
 * Date : 2019-06-20
 */
public class SettingActivity extends BaseActivity {
    @BindView(R2.id.toolbar)
    XSDToolbar toolbar;

    public static void launch(Activity preActivity) {
        preActivity.startActivity(new Intent(preActivity, SettingActivity.class));
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_setting;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbar.setOnBackListener(new XSDToolbar.OnBackListener() {
            @Override
            public void onBackClick() {
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R2.id.btn_logout})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.btn_logout) {
            AccountSp.putLoginState(false);
            finish();
        }
    }
}
