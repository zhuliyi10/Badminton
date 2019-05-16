package com.zhuliyi.commonlib.widget.morePop;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zhulilyi.commonlib.R;
import com.zhuliyi.commonlib.base.BaseAdapter;

import java.util.List;

/**
 * Describe : 选择更多adapter
 * Author : zhuly
 * Date : 2018-10-23
 */

public class MorePopAdapter extends BaseAdapter<MorePopBean> {
    private  int selectPos;
    private boolean change;
    public MorePopAdapter( @Nullable List<MorePopBean> data,boolean change) {
        super(R.layout.item_more_pop, data);
        this.change=change;
    }

    public void setSelectPos(int selectPos){
        this.selectPos=selectPos;
    }
    @Override
    protected void convert(BaseViewHolder helper, MorePopBean item) {
        int pos=helper.getAdapterPosition();
        ImageView imageView=helper.getView(R.id.image_select);
        if(item.getDrawRes()==0){
            imageView.setVisibility(View.GONE);
        }else {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(item.getDrawRes());
        }

        if(selectPos==pos&&change){
            helper.setTextColor(R.id.txt_name, Color.parseColor("#007AFF"));
        }else {
            helper.setTextColor(R.id.txt_name, Color.parseColor("#2D323A"));
        }

        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) helper.itemView.getLayoutParams();
        if(!item.isCanClick()){
            params.height = 0;
            params.width = 0;
            helper.itemView.setVisibility(View.VISIBLE);
        }else {
            params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            params.width = LinearLayout.LayoutParams.MATCH_PARENT;
            helper.itemView.setVisibility(View.VISIBLE);
        }
        helper.setText(R.id.txt_name,item.getName());
        View line=helper.getView(R.id.line);
        if(pos==getData().size()-1){
            line.setVisibility(View.GONE);
        }else {
            line.setVisibility(View.VISIBLE);
        }

    }
}
