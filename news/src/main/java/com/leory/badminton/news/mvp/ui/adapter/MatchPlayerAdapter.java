package com.leory.badminton.news.mvp.ui.adapter;


import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.leory.badminton.news.R;
import com.leory.badminton.news.mvp.model.bean.MatchPlayerBean;
import com.leory.commonlib.base.BaseAdapter;
import com.leory.commonlib.image.ImageConfig;
import com.leory.commonlib.utils.AppUtils;

import java.util.List;

/**
 * Describe : 参赛运动员adapter
 * Author : leory
 * Date : 2019-06-10
 */
public class MatchPlayerAdapter extends BaseAdapter<MatchPlayerBean> {


    public MatchPlayerAdapter(@Nullable List<MatchPlayerBean> data) {
        super(R.layout.item_match_player, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MatchPlayerBean item) {
        helper.setText(R.id.name1, item.getName1());
        helper.setText(R.id.country1, item.getCountry1());
        loadImg(helper.getView(R.id.head1), item.getHead1());
        loadImg(helper.getView(R.id.flag1), item.getFlag1());
        if (item.getName2() != null) {
            helper.getView(R.id.player2).setVisibility(View.VISIBLE);
            helper.setText(R.id.name2, item.getName2());
            helper.setText(R.id.country2, item.getCountry2());
            loadImg(helper.getView(R.id.head2), item.getHead2());
            loadImg(helper.getView(R.id.flag2), item.getFlag2());
        } else {
            helper.getView(R.id.player2).setVisibility(View.GONE);
        }

    }

    private void loadImg(ImageView imageView, String url) {
        ImageConfig config = new ImageConfig.Builder()
                .imageView(imageView)
                .url(url)
                .build();
        AppUtils.obtainImageLoader().loadImage(mContext, config);
    }
}
