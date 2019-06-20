package com.leory.badminton.mine.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.leory.badminton.mine.R;
import com.leory.badminton.mine.R2;
import com.leory.badminton.mine.mvp.model.bean.UserInfoBean;
import com.leory.badminton.mine.mvp.model.sp.AccountSp;
import com.leory.commonlib.base.BaseActivity;
import com.leory.commonlib.di.scope.ActivityScope;
import com.leory.commonlib.utils.LogUtils;
import com.leory.commonlib.widget.XSDToolbar;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    public static void launch(Activity preActivity) {
        preActivity.startActivity(new Intent(preActivity, LoginActivity.class));
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

    @OnClick({R2.id.wechat, R2.id.qq})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.wechat) {
            getThirdPartiesInfo(SHARE_MEDIA.WEIXIN);
        } else if (view.getId() == R.id.qq) {
            getThirdPartiesInfo(SHARE_MEDIA.QQ);
        }
    }

    private void getThirdPartiesInfo(SHARE_MEDIA share_media) {
        UMShareAPI.get(this).getPlatformInfo(this, share_media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                LogUtils.d(TAG, share_media.toString());
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                LogUtils.d(TAG, map.toString());
                UserInfoBean bean = AccountSp.getUserInfoBean();
                if (bean == null) {
                    bean = new UserInfoBean();
                    bean.setWechatUid(map.get("uid"));
                    bean.setName(map.get("name"));
                    bean.setGender(map.get("gender"));
                    bean.setIconUrl(map.get("iconurl"));
                }
                if (share_media == SHARE_MEDIA.QQ) {
                    bean.setQqUid(map.get("uid"));
                } else if (share_media == SHARE_MEDIA.WEIXIN) {
                    bean.setWechatUid(map.get("uid"));
                }
                bean.setShareMedia(share_media);
                AccountSp.putUserInfoBean(bean);
                AccountSp.putLoginState(true);
                finish();
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                LogUtils.d(TAG, throwable.toString());
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                LogUtils.d(TAG, "取消了");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }
}
