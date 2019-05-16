package com.zhuliyi.video.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.zhuliyi.commonlib.base.BaseAdapter;
import com.zhuliyi.commonlib.image.ImageConfig;
import com.zhuliyi.commonlib.utils.AppUtils;
import com.zhuliyi.video.R;
import com.zhuliyi.video.mvp.model.bean.VideoBean;
import com.zhuliyi.video.mvp.ui.widget.SampleCoverVideo;

import java.util.List;

/**
 * Describe : 视频adapter
 * Author : zhuly
 * Date : 2019-05-15
 */
public class VideoAdapter extends BaseAdapter<VideoBean> {
    public static final String TAG = VideoAdapter.class.getSimpleName();
    GSYVideoOptionBuilder gsyVideoOptionBuilder;

    public VideoAdapter(@Nullable List<VideoBean> data) {
        super(R.layout.item_video, data);
        gsyVideoOptionBuilder = new GSYVideoOptionBuilder();
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoBean item) {
        int position = helper.getAdapterPosition();
        SampleCoverVideo videoPlayer = helper.getView(R.id.detail_player);
        videoPlayer.setUpLazy(item.getVideourl(), true, null, null, item.getTitle());
        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.GONE);
        //设置返回键
        videoPlayer.getBackButton().setVisibility(View.GONE);
        //设置全屏按键功能
        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoPlayer.startWindowFullscreen(mContext, false, true);
            }
        });
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        gsyVideoOptionBuilder.setPlayTag(TAG)//防止错位设置
                .setThumbImageView(imageView)
                .setUrl(item.getVideourl())
                .setCacheWithPlay(false)
                .setRotateViewAuto(true)
                .setPlayPosition(position)
                .setAutoFullWithSize(true)//是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏
                .setShowFullAnimation(true)      //全屏动画
                .setIsTouchWiget(false)      //小屏时不触摸滑动
                .build(videoPlayer);

        if (item.getImgurl() != null) {
            String imageUrl;
            if (item.getImgurl().startsWith("http")) {
                imageUrl = item.getImgurl();
            } else {
                imageUrl = "http://www.zhibo.tv" + item.getImgurl();
            }
            ImageConfig config = new ImageConfig.Builder()
                    .url(imageUrl)
                    .imageView(imageView)
                    .build();
            AppUtils.obtainImageLoader().loadImage(mContext, config);
        }


        helper.setText(R.id.txt_title, item.getTitle());
        if (item.getTotalTimes() != null) {
            helper.setVisible(R.id.txt_duration, true);
            helper.setText(R.id.txt_duration, "时长：" + getFormatDuration(item.getTotalTimes()));
        } else {
            helper.setVisible(R.id.txt_duration, false);
        }

        if (item.getPlaycount() != null) {
            helper.setVisible(R.id.txt_play_count, true);
            helper.setText(R.id.txt_play_count, "播放数：" + item.getPlaycount());
        } else {
            helper.setVisible(R.id.txt_play_count, false);
        }
    }


    private String getFormatDuration(String time){
        if(TextUtils.isDigitsOnly(time)){
            int totalSecond=Integer.parseInt(time);
            int min=totalSecond/60;
            int second=totalSecond%60;
            StringBuffer buffer=new StringBuffer().append(min).append(":").append(String.format("%02d",second));
            return buffer.toString();
        }
        return time;
    }
}