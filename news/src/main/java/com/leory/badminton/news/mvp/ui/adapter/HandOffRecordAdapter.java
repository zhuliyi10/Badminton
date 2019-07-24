package com.leory.badminton.news.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.leory.badminton.news.R;
import com.leory.badminton.news.mvp.model.bean.HandOffBean;
import com.leory.commonlib.base.BaseAdapter;

import java.util.List;

/**
 * Describe : 交手记录adapter
 * Author : leory
 * Date : 2019-07-24
 */
public class HandOffRecordAdapter extends BaseAdapter<HandOffBean.Record> {
    public HandOffRecordAdapter(@Nullable List<HandOffBean.Record> data) {
        super(R.layout.item_hand_off_record, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HandOffBean.Record item) {
        helper.setText(R.id.txt_time, item.getDate());
        helper.setText(R.id.txt_match, item.getMatchName());
        helper.setText(R.id.txt_score, item.getScore());
        if (item.isLeftWin()) {
            helper.setText(R.id.win1, "胜");
            helper.setText(R.id.win2, "负");
        } else {
            helper.setText(R.id.win1, "负");
            helper.setText(R.id.win2, "胜");

        }
    }
}
