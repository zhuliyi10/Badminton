package com.leory.badminton.news.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.leory.badminton.news.R;
import com.leory.badminton.news.mvp.model.bean.PlayerMatchBean;
import com.leory.commonlib.base.BaseAdapter;
import com.leory.commonlib.utils.ImageUtils;

import java.util.List;

/**
 * Describe : 运动员赛果比赛adapter
 * Author : leory
 * Date : 2019-06-11
 */
public class PlayerMatchRoundAdapter extends BaseAdapter<PlayerMatchBean.ResultRound> {
    public PlayerMatchRoundAdapter(@Nullable List<PlayerMatchBean.ResultRound> data) {
        super(R.layout.item_player_match_round, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PlayerMatchBean.ResultRound item) {
        helper.setText(R.id.txt_round, item.getRound());
        helper.setText(R.id.txt_duration, item.getDuration());
        helper.setText(R.id.txt_player1, item.getPlayer1());
        helper.setText(R.id.txt_player2, item.getPlayer2());
        ImageUtils.loadImage(mContext, helper.getView(R.id.img_flag1), item.getFlag1());
        ImageUtils.loadImage(mContext, helper.getView(R.id.img_flag2), item.getFlag2());
        helper.setText(R.id.vs, item.getVs());
        helper.setText(R.id.txt_score, item.getScore());

        if (item.getPlayer2() == null) {
            helper.getView(R.id.player12).setVisibility(View.GONE);
            helper.getView(R.id.player22).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.player12).setVisibility(View.VISIBLE);
            helper.getView(R.id.player22).setVisibility(View.VISIBLE);
            helper.setText(R.id.txt_player12, item.getPlayer12());
            helper.setText(R.id.txt_player22, item.getPlayer22());
            ImageUtils.loadImage(mContext, helper.getView(R.id.img_flag12), item.getFlag12());
            ImageUtils.loadImage(mContext, helper.getView(R.id.img_flag22), item.getFlag22());
        }
    }
}
