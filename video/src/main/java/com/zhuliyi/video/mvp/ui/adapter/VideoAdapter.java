package com.zhuliyi.video.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
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
    public static final String TAG = VideoAdapter.class.getSimpleName();
    public VideoAdapter(@Nullable List<VideoBean> data) {
        super(R.layout.item_video, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoBean item) {
        int position=helper.getAdapterPosition();
        StandardGSYVideoPlayer videoPlayer = helper.getView(R.id.detail_player);
        videoPlayer.setUpLazy(item.getVideourl(), true, null, null, "这是title");
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
        //防止错位设置
        videoPlayer.setPlayTag(TAG);
        videoPlayer.setPlayPosition(position);
        //是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏
        videoPlayer.setAutoFullWithSize(true);
        //音频焦点冲突时是否释放
        videoPlayer.setReleaseWhenLossAudio(false);
        //全屏动画
        videoPlayer.setShowFullAnimation(true);
        //小屏时不触摸滑动
        videoPlayer.setIsTouchWiget(false);
//        if (item.getImgurl() != null) {
//            String imageUrl;
//            if (item.getImgurl().startsWith("http")) {
//                imageUrl = item.getImgurl();
//            } else {
//                imageUrl = "http://www.zhibo.tv" + item.getImgurl();
//            }
//            ImageConfig config = new ImageConfig.Builder()
//                    .url(imageUrl)
//                    .imageView(helper.getView(R.id.image))
//                    .build();
//            AppUtils.obtainImageLoader().loadImage(mContext, config);
//        }


        helper.setText(R.id.txt_title, item.getTitle());
        if(item.getTotalTimes()!=null){
            helper.setVisible(R.id.txt_duration,true);
            helper.setText(R.id.txt_duration, "时长："+item.getTotalTimes());
        }else {
            helper.setVisible(R.id.txt_duration,false);
        }

        if (item.getPlaycount() != null) {
            helper.setVisible(R.id.txt_play_count, true);
            helper.setText(R.id.txt_play_count, "播放数：" + item.getPlaycount());
        } else {
            helper.setVisible(R.id.txt_play_count, false);
        }
    }
}
