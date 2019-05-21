package com.leory.badminton.news.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.leory.badminton.news.R;
import com.leory.badminton.news.mvp.model.bean.RankingBean;
import com.leory.commonlib.base.BaseAdapter;
import com.leory.commonlib.image.ImageConfig;
import com.leory.commonlib.utils.AppUtils;

import java.util.List;

/**
 * Describe : 排名adapter
 * Author : zhuly
 * Date : 2019-05-21
 */
public class RankingAdapter extends BaseAdapter<RankingBean> {

    public RankingAdapter( @Nullable List<RankingBean> data) {
        super(R.layout.item_ranking, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RankingBean item) {
        helper.setText(R.id.randing_num,item.getRankingNum());
        helper.setText(R.id.country_name,item.getCountryName());
        ImageConfig config=new ImageConfig.Builder()
                .url(item.getCountryFlagUrl())
                .imageView(helper.getView(R.id.img_country_flag))
                .build();
        AppUtils.obtainImageLoader().loadImage(mContext,config);
        helper.setText(R.id.player_name,item.getPlayerName());
        helper.setText(R.id.points,item.getPoints());
        if(helper.getAdapterPosition()%2==0){
            helper.itemView.setBackgroundResource(R.color.white);
        }else {
            helper.itemView.setBackgroundResource(R.color.bg_gray);
        }
    }
}
