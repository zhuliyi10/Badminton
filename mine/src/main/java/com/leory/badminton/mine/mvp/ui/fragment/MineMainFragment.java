package com.leory.badminton.mine.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.leory.badminton.mine.R;
import com.leory.badminton.mine.R2;
import com.leory.badminton.mine.di.component.DaggerMineComponent;
import com.leory.badminton.mine.mvp.contract.MineContract;
import com.leory.badminton.mine.mvp.model.bean.UserInfoBean;
import com.leory.badminton.mine.mvp.presenter.MinePresenter;
import com.leory.badminton.mine.mvp.ui.activity.LoginActivity;
import com.leory.badminton.mine.mvp.ui.activity.SettingActivity;
import com.leory.commonlib.base.BaseFragment;
import com.leory.commonlib.base.delegate.IComponent;
import com.leory.commonlib.di.component.AppComponent;
import com.leory.commonlib.utils.ImageUtils;
import com.leory.commonlib.widget.CustomImageView;
import com.leory.interactions.RouterHub;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Describe : 圈子fragment
 * Author : zhuly
 * Date : 2019-05-22
 */

@Route(path = RouterHub.MINE_MINEMAINFRAGMENT)
public class MineMainFragment extends BaseFragment<MinePresenter> implements MineContract.View {

    @BindView(R2.id.info)
    ConstraintLayout info;
    @BindView(R2.id.login)
    ConstraintLayout login;
    @BindView(R2.id.img_head)
    CustomImageView imgHead;
    @BindView(R2.id.txt_name)
    TextView txtName;
    @BindView(R2.id.txt_desc)
    TextView txtDesc;
    @BindView(R2.id.txt_account)
    TextView txtAccount;

    @Override
    public IComponent setupActivityComponent(IComponent component) {
        DaggerMineComponent.builder()
                .appComponent((AppComponent) component)
                .view(this)
                .build()
                .inject(this);
        return super.setupActivityComponent(component);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.updateLoginState();
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine_main, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void showLoginState(boolean isLogin, UserInfoBean bean) {
        info.setVisibility(isLogin ? View.VISIBLE : View.GONE);
        login.setVisibility(isLogin ? View.GONE : View.VISIBLE);
        if (isLogin && bean != null) {
            ImageUtils.loadImage(getContext(), imgHead, bean.getIconUrl());
            txtName.setText(bean.getName());
            txtDesc.setText(TextUtils.isEmpty(bean.getDesc()) ? "这个羽毛球爱好者很懒，什么都没留下..." : bean.getDesc());
            if (TextUtils.isEmpty(bean.getPhone())) {
                txtAccount.setText("绑定手机号");
            } else {
                txtAccount.setText("账号：" + bean.getPhone());
            }
        }
    }

    @OnClick({R2.id.btn_login, R2.id.setting})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.btn_login) {
            LoginActivity.launch(getActivity());
        } else if (view.getId() == R.id.setting) {
            SettingActivity.launch(getActivity());
        }
    }

}
