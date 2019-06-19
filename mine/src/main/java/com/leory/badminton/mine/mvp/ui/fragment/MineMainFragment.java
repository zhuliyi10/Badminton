package com.leory.badminton.mine.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.leory.badminton.mine.R;
import com.leory.badminton.mine.R2;
import com.leory.badminton.mine.di.component.DaggerMineComponent;
import com.leory.badminton.mine.mvp.contract.MineContract;
import com.leory.badminton.mine.mvp.presenter.MinePresenter;
import com.leory.badminton.mine.mvp.ui.activity.LoginActivity;
import com.leory.commonlib.base.BaseFragment;
import com.leory.commonlib.base.delegate.IComponent;
import com.leory.commonlib.di.component.AppComponent;
import com.leory.interactions.RouterHub;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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
    Unbinder unbinder;

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
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine_main, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        presenter.updateLoginState();
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
    public void showLoginState(boolean isLogin) {
        info.setVisibility(isLogin ? View.VISIBLE : View.GONE);
        login.setVisibility(isLogin ? View.GONE : View.VISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R2.id.btn_login})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.btn_login) {
            LoginActivity.launch(getActivity());
        }
    }
}
