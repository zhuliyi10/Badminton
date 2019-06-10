package com.leory.badminton.news.mvp.ui.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.leory.badminton.news.R;
import com.leory.badminton.news.mvp.model.bean.MatchHistoryBean;
import com.leory.badminton.news.mvp.model.bean.MatchHistoryHeadBean;
import com.leory.badminton.news.mvp.model.bean.MatchPlayerHeadBean;
import com.leory.badminton.news.mvp.model.bean.MatchPlayerListBean;
import com.leory.badminton.news.mvp.model.bean.MultiMatchHistoryBean;
import com.leory.badminton.news.mvp.model.bean.MultiMatchPlayersBean;
import com.leory.commonlib.image.ImageConfig;
import com.leory.commonlib.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe : 参赛运动员adapter
 * Author : leory
 * Date : 2019-06-10
 */
public class MatchPlayersAdapter extends BaseMultiItemQuickAdapter<MultiMatchPlayersBean, BaseViewHolder> {

    public MatchPlayersAdapter(List<MultiMatchPlayersBean> data) {
        super(data);
        addItemType(MultiMatchHistoryBean.TYPE_HEAD, R.layout.head_match_players_tips);
        addItemType(MultiMatchHistoryBean.TYPE_CONTENT, R.layout.item_match_players_country);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiMatchPlayersBean item) {
        if (item.getItemType() == MultiMatchHistoryBean.TYPE_HEAD) {
            MatchPlayerHeadBean headBean= (MatchPlayerHeadBean) item.getT();
            helper.setText(R.id.txt_name,headBean.getName());
            TextView second= helper.getView(R.id.txt_second);
            if(TextUtils.isEmpty(headBean.getSecond())){
                second.setVisibility(View.GONE);
            }else {
                second.setVisibility(View.VISIBLE);
                second.setText(headBean.getSecond());
            }

        } else {
            MatchPlayerListBean listBean= (MatchPlayerListBean) item.getT();
            RecyclerView rcv=helper.getView(R.id.rcv_country);
            rcv.setLayoutManager(new GridLayoutManager(mContext,2));
            rcv.setAdapter(new MatchPlayerAdapter(listBean.getData()));
        }
    }


}
