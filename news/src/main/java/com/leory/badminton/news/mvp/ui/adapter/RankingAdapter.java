package com.leory.badminton.news.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.leory.badminton.news.R;
import com.leory.commonlib.utils.ImageUtils;
import com.leory.badminton.news.mvp.model.bean.RankingBean;
import com.leory.commonlib.base.BaseAdapter;

import java.util.List;

/**
 * Describe : 排名adapter
 * Author : zhuly
 * Date : 2019-05-21
 */
public class RankingAdapter extends BaseAdapter<RankingBean> {

    public RankingAdapter(@Nullable List<RankingBean> data) {
        super(R.layout.item_ranking, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RankingBean item) {
        helper.addOnClickListener(R.id.player_name);
        helper.addOnClickListener(R.id.player2_name);
        helper.setText(R.id.randing_num, item.getRankingNum());
        helper.setText(R.id.country_name, item.getCountryName());
        ImageUtils.loadImage(mContext, helper.getView(R.id.img_country_flag), item.getCountryFlagUrl());
        helper.setText(R.id.player_name, item.getPlayerName());
        helper.setText(R.id.points, item.getPoints());
        if (helper.getAdapterPosition() % 2 == 0) {
            helper.itemView.setBackgroundResource(R.color.white);
        } else {
            helper.itemView.setBackgroundResource(R.color.bg_gray);
        }

        if (TextUtils.isEmpty(item.getPlayer2Name())) {
            helper.getView(R.id.player2_name).setVisibility(View.GONE);
            helper.getView(R.id.country2).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.player2_name).setVisibility(View.VISIBLE);
            helper.getView(R.id.country2).setVisibility(View.VISIBLE);
            helper.setText(R.id.country_name2, item.getCountry2Name());
            ImageUtils.loadImage(mContext, helper.getView(R.id.img_country_flag2), item.getCountryFlag2Url());
            helper.setText(R.id.player2_name, item.getPlayer2Name());
        }
    }
}
