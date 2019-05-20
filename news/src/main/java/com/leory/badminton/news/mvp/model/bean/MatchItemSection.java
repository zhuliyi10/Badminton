package com.leory.badminton.news.mvp.model.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Describe : 带有头部的bean
 * Author : leory
 * Date : 2019-05-20
 */
public class MatchItemSection extends SectionEntity<MatchItemBean> {
    public MatchItemSection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public MatchItemSection(MatchItemBean matchItemBean) {
        super(matchItemBean);
    }
}
