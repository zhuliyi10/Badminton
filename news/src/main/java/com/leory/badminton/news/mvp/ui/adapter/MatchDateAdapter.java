package com.leory.badminton.news.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.leory.badminton.news.R;
import com.leory.badminton.news.mvp.model.bean.MatchDateBean;
import com.leory.commonlib.base.BaseAdapter;
import com.leory.commonlib.image.ImageConfig;
import com.leory.commonlib.utils.AppUtils;

import java.util.List;

/**
 * Describe : 赛事赛程adapter
 * Author : leory
 * Date : 2019-06-06
 */
public class MatchDateAdapter extends BaseAdapter<MatchDateBean> {
    public MatchDateAdapter(@Nullable List<MatchDateBean> data) {
        super(R.layout.item_match_date, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MatchDateBean item) {
        int position = helper.getAdapterPosition();
        if (position % 2 == 0) {
            helper.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        } else {
            helper.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.bg_gray));
        }
        helper.setText(R.id.txt_type, item.getType());
        helper.setText(R.id.txt_time, item.getTime());
        helper.setText(R.id.txt_field, item.getField());
        helper.setText(R.id.txt_duration, item.getDuration());
        helper.setText(R.id.txt_player1, item.getPlayer1());
        helper.setText(R.id.txt_player2, item.getPlayer2());
        ImageConfig config = new ImageConfig.Builder()
                .imageView(helper.getView(R.id.img_flag1))
                .url(item.getFlag1())
                .build();
        AppUtils.obtainImageLoader().loadImage(mContext, config);
        config = new ImageConfig.Builder()
                .imageView(helper.getView(R.id.img_flag2))
                .url(item.getFlag2())
                .build();
        AppUtils.obtainImageLoader().loadImage(mContext, config);
        helper.setText(R.id.vs, item.getVs());
        helper.setText(R.id.txt_score, item.getScore());

        if ("男单".equals(item.getType()) || "女单".equals(item.getType())) {
            helper.getView(R.id.player12).setVisibility(View.GONE);
            helper.getView(R.id.player22).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.player12).setVisibility(View.VISIBLE);
            helper.getView(R.id.player22).setVisibility(View.VISIBLE);
            helper.setText(R.id.txt_player12, item.getPlayer12());
            helper.setText(R.id.txt_player22, item.getPlayer22());
            config = new ImageConfig.Builder()
                    .imageView(helper.getView(R.id.img_flag12))
                    .url(item.getFlag12())
                    .build();
            AppUtils.obtainImageLoader().loadImage(mContext, config);
            config = new ImageConfig.Builder()
                    .imageView(helper.getView(R.id.img_flag22))
                    .url(item.getFlag22())
                    .build();
            AppUtils.obtainImageLoader().loadImage(mContext, config);
        }
    }
}
