package com.leory.badminton.news.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leory.badminton.news.R;
import com.leory.badminton.news.R2;
import com.leory.badminton.news.mvp.model.bean.PlayerInfoBean;
import com.leory.commonlib.base.BaseLazyLoadFragment;
import com.leory.commonlib.di.scope.FragmentScope;

import butterknife.BindView;

/**
 * Describe : 运动员个人资料
 * Author : leory
 * Date : 2019-06-11
 */
@FragmentScope
public class PlayerInfoFragment extends BaseLazyLoadFragment {
    private static final String KEY_PLAYER_INFO = "key_player_info";
    @BindView(R2.id.txt_stats)
    TextView txtStats;

    public static PlayerInfoFragment newInstance(PlayerInfoBean bean) {
        PlayerInfoFragment fragment = new PlayerInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_PLAYER_INFO, bean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void lazyLoadData() {
        PlayerInfoBean infoBean = (PlayerInfoBean) getArguments().getSerializable(KEY_PLAYER_INFO);
        if (infoBean != null) {
            txtStats.setText(infoBean.getStats());
        }
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_player_info, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}
