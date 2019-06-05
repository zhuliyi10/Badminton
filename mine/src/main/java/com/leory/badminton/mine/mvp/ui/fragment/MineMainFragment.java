package com.leory.badminton.mine.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.leory.badminton.mine.R;
import com.leory.commonlib.base.BaseFragment;
import com.leory.interactions.RouterHub;

/**
 * Describe : 圈子fragment
 * Author : zhuly
 * Date : 2019-05-22
 */

@Route(path = RouterHub.MINE_MINEMAINFRAGMENT)
public class MineMainFragment extends BaseFragment {


    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine_main, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}
