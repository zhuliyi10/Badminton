package com.leory.badminton.news.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.leory.badminton.news.R;
import com.leory.badminton.news.mvp.model.bean.PlayerMatchBean;
import com.leory.commonlib.base.BaseAdapter;

import java.util.List;

/**
 * Describe : 运动员赛果adapter
 * Author : leory
 * Date : 2019-06-11
 */
public class PlayerMatchAdapter extends BaseAdapter<PlayerMatchBean> {

    public PlayerMatchAdapter(@Nullable List<PlayerMatchBean> data) {
        super(R.layout.item_player_match, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PlayerMatchBean item) {
        PlayerMatchBean.MatchInfo info = item.getMatchInfo();
        helper.setText(R.id.txt_name, info.getName());
        helper.setText(R.id.txt_category, info.getCategory());
        helper.setText(R.id.txt_time, info.getDate());
        helper.setText(R.id.txt_bonus, info.getBonus());
        RecyclerView rcv = helper.getView(R.id.rcv);
        rcv.setLayoutManager(new LinearLayoutManager(mContext));
        rcv.setAdapter(new PlayerMatchRoundAdapter(item.getRounds()));
    }
}
