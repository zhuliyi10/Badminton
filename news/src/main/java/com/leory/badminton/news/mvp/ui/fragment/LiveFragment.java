package com.leory.badminton.news.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leory.badminton.news.R;
import com.leory.commonlib.base.BaseLazyLoadFragment;
import com.leory.commonlib.di.component.AppComponent;

/**
 * Describe :直播fragment
 * Author : leory
 * Date : 2019-05-19
 */
public class LiveFragment extends BaseLazyLoadFragment {
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_live,container,false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void lazyLoadData() {

    }
}
