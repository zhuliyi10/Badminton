package com.leory.badminton.mine.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import com.leory.badminton.mine.R;
import com.leory.badminton.mine.R2;
import com.leory.commonlib.base.BaseActivity;
import com.leory.commonlib.di.scope.ActivityScope;
import com.leory.commonlib.widget.XSDToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Describe : 登陆activity
 * Author : leory
 * Date : 2019-06-19
 */
@ActivityScope
public class LoginActivity extends BaseActivity {
    @BindView(R2.id.toolbar)
    XSDToolbar toolbar;
    @BindView(R2.id.et_phone)
    EditText etPhone;
    @BindView(R2.id.et_code)
    EditText etCode;
    @BindView(R2.id.txt_code)
    TextView txtCode;

    public static void launch(Activity preActivity){
        preActivity.startActivity(new Intent(preActivity,LoginActivity.class));
    }
    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login;
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
}
