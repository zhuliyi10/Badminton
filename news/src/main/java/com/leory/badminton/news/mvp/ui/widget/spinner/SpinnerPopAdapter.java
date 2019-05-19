package com.leory.badminton.news.mvp.ui.widget.spinner;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.leory.badminton.news.R;
import com.leory.commonlib.base.BaseAdapter;

import java.util.List;

/**
 * Describe : 下拉菜单adapter
 * Author : zhuly
 * Date : 2018-10-23
 */

public class SpinnerPopAdapter extends BaseAdapter<String> {
    private int selectPos;
    private boolean change;

    public SpinnerPopAdapter(@Nullable List<String> data, boolean change) {
        super(R.layout.item_spinner_pop, data);
        this.change = change;
    }

    public void setSelectPos(int selectPos) {
        this.selectPos = selectPos;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        int pos = helper.getAdapterPosition();
        ImageView imageView = helper.getView(R.id.image_select);

        if (selectPos == pos && change) {
            helper.setTextColor(R.id.txt_name, Color.parseColor("#007AFF"));
        } else {
            helper.setTextColor(R.id.txt_name, Color.parseColor("#2D323A"));
        }
        helper.setText(R.id.txt_name, item);
        View line = helper.getView(R.id.line);
        if (pos == getData().size() - 1) {
            line.setVisibility(View.GONE);
        } else {
            line.setVisibility(View.VISIBLE);
        }

    }
}
