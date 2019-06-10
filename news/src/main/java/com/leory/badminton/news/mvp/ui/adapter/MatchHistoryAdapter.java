package com.leory.badminton.news.mvp.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.leory.badminton.news.R;
import com.leory.badminton.news.mvp.model.bean.MatchHistoryBean;
import com.leory.badminton.news.mvp.model.bean.MatchHistoryHeadBean;
import com.leory.badminton.news.mvp.model.bean.MultiMatchHistoryBean;
import com.leory.commonlib.image.ImageConfig;
import com.leory.commonlib.utils.AppUtils;

import java.util.List;

/**
 * Describe : 历史赛事adapter
 * Author : leory
 * Date : 2019-06-07
 */
public class MatchHistoryAdapter extends BaseMultiItemQuickAdapter<MultiMatchHistoryBean, BaseViewHolder> {

    public MatchHistoryAdapter(List<MultiMatchHistoryBean> data) {
        super(data);
        addItemType(MultiMatchHistoryBean.TYPE_HEAD, R.layout.head_match_history);
        addItemType(MultiMatchHistoryBean.TYPE_CONTENT, R.layout.item_match_history);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiMatchHistoryBean item) {
        if (item.getItemType() == MultiMatchHistoryBean.TYPE_HEAD) {
            MatchHistoryHeadBean headBean = (MatchHistoryHeadBean) item.getT();
            helper.setText(R.id.year, headBean.getYear());
            helper.setText(R.id.match_name, headBean.getMatchName());
        } else {
            MatchHistoryBean bean = (MatchHistoryBean) item.getT();
            loadImg(helper.getView(R.id.img_ms), bean.getMsHead());
            loadImg(helper.getView(R.id.ms_flag), bean.getMsFlag());
            helper.setText(R.id.ms_name, bean.getMsName());

            loadImg(helper.getView(R.id.img_ws), bean.getWsHead());
            loadImg(helper.getView(R.id.ws_flag), bean.getWsFlag());
            helper.setText(R.id.ws_name, bean.getWsName());

            loadImg(helper.getView(R.id.img_md1), bean.getMd1Head());
            loadImg(helper.getView(R.id.md1_flag), bean.getMd1Flag());
            helper.setText(R.id.md1_name, bean.getMd1Name());
            loadImg(helper.getView(R.id.img_md2), bean.getMd2Head());
            loadImg(helper.getView(R.id.md2_flag), bean.getMd2Flag());
            helper.setText(R.id.md2_name, bean.getMd2Name());

            loadImg(helper.getView(R.id.img_wd1), bean.getWd1Head());
            loadImg(helper.getView(R.id.wd1_flag), bean.getWd1Flag());
            helper.setText(R.id.wd1_name, bean.getWd1Name());
            loadImg(helper.getView(R.id.img_wd2), bean.getWd2Head());
            loadImg(helper.getView(R.id.wd2_flag), bean.getWd2Flag());
            helper.setText(R.id.wd2_name, bean.getWd2Name());

            loadImg(helper.getView(R.id.img_xd1), bean.getXd1Head());
            loadImg(helper.getView(R.id.xd1_flag), bean.getXd1Flag());
            helper.setText(R.id.xd1_name, bean.getXd1Name());
            loadImg(helper.getView(R.id.img_xd2), bean.getXd2Head());
            loadImg(helper.getView(R.id.xd2_flag), bean.getXd2Flag());
            helper.setText(R.id.xd2_name, bean.getXd2Name());
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
