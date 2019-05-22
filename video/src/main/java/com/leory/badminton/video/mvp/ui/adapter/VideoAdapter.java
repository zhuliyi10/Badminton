package com.leory.badminton.video.mvp.ui.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.leory.badminton.video.R;
import com.leory.commonlib.utils.ToastUtils;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.leory.commonlib.base.BaseAdapter;
import com.leory.commonlib.image.ImageConfig;
import com.leory.commonlib.utils.AppUtils;
import com.leory.badminton.video.mvp.model.bean.VideoBean;
import com.leory.badminton.video.mvp.ui.widget.SampleCoverVideo;

import java.util.List;

/**
 * Describe : 视频adapter
 * Author : leory
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

        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        gsyVideoOptionBuilder.setPlayTag(TAG)//防止错位设置
                .setIsTouchWiget(false)
                .setThumbImageView(imageView)
                .setUrl(item.getVideourl())
                .setVideoTitle(item.getTitle())
                .setCacheWithPlay(false)
                .setRotateViewAuto(true)
                .setLockLand(true)
                .setPlayTag(TAG)
                .setShowFullAnimation(true)
                .setNeedLockFull(true)
                .setPlayPosition(position)
                .build(videoPlayer);

        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.GONE);

        //设置返回键
        videoPlayer.getBackButton().setVisibility(View.GONE);

        //设置全屏按键功能
        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoPlayer.startWindowFullscreen(mContext, true, true);
            }
        });
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
        helper.getView(R.id.txt_title).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ToastUtils.showShort("播放地址已复制到剪贴板");
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", item.getVideourl());
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                return true;
            }
        });
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
