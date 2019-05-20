package com.leory.badminton.news.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.leory.badminton.news.R;
import com.leory.badminton.news.mvp.model.bean.MatchItemBean;
import com.leory.badminton.news.mvp.model.bean.MatchItemSection;

import java.util.List;

/**
 * Describe : 赛事列表adapter
 * Author : leory
 * Date : 2019-05-20
 */
public class MatchSectionAdapter extends BaseSectionQuickAdapter<MatchItemSection, BaseViewHolder> {

    public MatchSectionAdapter(List<MatchItemSection> data) {
        super(R.layout.item_match, R.layout.head_match_month, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, MatchItemSection item) {
        helper.setText(R.id.txt_head, item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, MatchItemSection item) {
        MatchItemBean bean = item.t;
        helper.setText(R.id.match_name, bean.getMatchName());
        helper.setText(R.id.match_day, bean.getMatchDay());
        helper.setText(R.id.match_classify, bean.getMatchClassify());
        helper.itemView.setBackgroundColor(bean.getBgColor());
    }
}
