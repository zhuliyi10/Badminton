package com.leory.badminton.news.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leory.badminton.news.R;
import com.leory.badminton.news.R2;
import com.leory.badminton.news.di.component.DaggerMatchComponent;
import com.leory.badminton.news.di.component.MatchComponent;
import com.leory.badminton.news.mvp.contract.MatchContract;
import com.leory.badminton.news.mvp.presenter.MatchPresenter;
import com.leory.badminton.news.mvp.ui.widget.spinner.SpinnerPopView;
import com.leory.commonlib.base.BaseFragment;
import com.leory.commonlib.di.component.AppComponent;
import com.leory.commonlib.utils.ToastUtils;

import java.util.Arrays;

import butterknife.BindView;

/**
 * Describe :赛事fragment
 * Author : leory
 * Date : 2019-05-19
 */
public class MatchFragment extends BaseFragment<MatchPresenter> implements MatchContract.View {


    @BindView(R2.id.spinner_year)
    SpinnerPopView spinnerYear;
    @BindView(R2.id.spinner_finish)
    SpinnerPopView spinnerFinish;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMatchComponent.builder()
                .view(this)
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_match, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String[] years = new String[]{"2020", "2019", "2018", "2017", "2016", "2015", "2014"};
        spinnerYear.initData(Arrays.asList(years), 1);
        spinnerYear.setOnSelectListener(new SpinnerPopView.OnSelectListener() {
            @Override
            public void onItemClick(int pos, String name) {
                ToastUtils.showShort(name);
            }
        });
        String[] finishes = new String[]{"全部", "已完成", "剩余"};
        spinnerFinish.initData(Arrays.asList(finishes), 2);
        spinnerFinish.setOnSelectListener(new SpinnerPopView.OnSelectListener() {
            @Override
            public void onItemClick(int pos, String name) {
                ToastUtils.showShort(name);
            }
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtils.showShort(message);
    }
}
