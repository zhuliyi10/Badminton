package com.zhuliyi.video.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zhuliyi.commonlib.base.BaseAdapter;
import com.zhuliyi.commonlib.image.ImageConfig;
import com.zhuliyi.commonlib.utils.AppUtils;
import com.zhuliyi.video.R;
import com.zhuliyi.video.mvp.model.bean.VideoBean;

import java.util.List;

/**
 * Describe : 视频adapter
 * Author : zhuly
 * Date : 2019-05-15
 */
public class VideoAdapter extends BaseAdapter<VideoBean> {
    public VideoAdapter(@Nullable List<VideoBean> data) {
        super(R.layout.item_video, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoBean item) {
        if (item.getImgurl() != null) {
            String imageUrl;
            if (item.getImgurl().startsWith("http")) {
                imageUrl = item.getImgurl();
            } else {
                imageUrl = "http://www.zhibo.tv" + item.getImgurl();
            }
            ImageConfig config = new ImageConfig.Builder()
                    .url(imageUrl)
                    .imageView(helper.getView(R.id.image))
                    .build();
            AppUtils.obtainImageLoader().loadImage(mContext, config);
        }


        helper.setText(R.id.txt_title, item.getTitle());
        helper.setText(R.id.txt_duration, item.getTotalTimes());
        if (item.getPlaycount() != null) {
            helper.setVisible(R.id.txt_play_count, true);
            helper.setText(R.id.txt_play_count, "播放数：" + item.getPlaycount());
        } else {
            helper.setVisible(R.id.txt_play_count, false);
        }
    }
}
