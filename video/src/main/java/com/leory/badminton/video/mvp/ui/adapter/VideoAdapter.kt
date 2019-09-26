package com.leory.badminton.video.mvp.ui.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.ImageView

import com.chad.library.adapter.base.BaseViewHolder
import com.leory.badminton.video.R
import com.leory.badminton.video.mvp.model.bean.VideoBean
import com.leory.badminton.video.mvp.ui.widget.SampleCoverVideo
import com.leory.commonlib.base.BaseAdapter
import com.leory.commonlib.image.ImageConfig
import com.leory.commonlib.utils.AppUtils
import com.leory.commonlib.utils.ToastUtils
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder

/**
 * Describe : 视频adapter
 * Author : leory
 * Date : 2019-05-15
 */
class VideoAdapter(data: List<VideoBean>?) : BaseAdapter<VideoBean>(R.layout.item_video, data) {
    companion object {
        val TAG: String = VideoAdapter::class.java.simpleName
    }

    private var gsyVideoOptionBuilder: GSYVideoOptionBuilder = GSYVideoOptionBuilder()

    override fun convert(helper: BaseViewHolder, item: VideoBean) {
        val position = helper.adapterPosition
        val videoPlayer = helper.getView<SampleCoverVideo>(R.id.detail_player)

        val imageView = ImageView(mContext)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        gsyVideoOptionBuilder.setPlayTag(TAG)//防止错位设置
                .setIsTouchWiget(false)
                .setThumbImageView(imageView)
                .setUrl(item.videourl)
                .setVideoTitle(item.title)
                .setCacheWithPlay(false)
                .setRotateViewAuto(true)
                .setLockLand(true)
                .setPlayTag(TAG)
                .setNeedShowWifiTip(false)
                .setAutoFullWithSize(true)
                .setShowFullAnimation(true)
                .setNeedLockFull(true)
                .setPlayPosition(position)
                .build(videoPlayer)

        //增加title
        videoPlayer.titleTextView.visibility = View.GONE

        //设置返回键
        videoPlayer.backButton.visibility = View.GONE

        //设置全屏按键功能
        videoPlayer.fullscreenButton.setOnClickListener { videoPlayer.startWindowFullscreen(mContext, true, true) }
        if (item.imgurl != null) {
            val imageUrl: String = if (item.imgurl!!.startsWith("http")) {
                item.imgurl!!
            } else {
                "http://www.zhibo.tv" + item.imgurl
            }
            val config = ImageConfig.Builder()
                    .url(imageUrl)
                    .imageView(imageView)
                    .build()
            AppUtils.obtainImageLoader().loadImage(mContext, config)
        }


        helper.setText(R.id.txt_title, item.title)
        helper.getView<View>(R.id.txt_title).setOnLongClickListener {
            ToastUtils.showShort("播放地址已复制到剪贴板")
            //获取剪贴板管理器：
            val cm = mContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            // 创建普通字符型ClipData
            val mClipData = ClipData.newPlainText("Label", item.title + "\n" + item.videourl)
            // 将ClipData内容放到系统剪贴板里。
            cm.primaryClip = mClipData
            true
        }
        if (item.totalTimes != null) {
            helper.setVisible(R.id.txt_duration, true)
            helper.setText(R.id.txt_duration, "时长：" + getFormatDuration(item.totalTimes))
        } else {
            helper.setVisible(R.id.txt_duration, false)
        }

        if (item.playcount != null) {
            helper.setVisible(R.id.txt_play_count, true)
            helper.setText(R.id.txt_play_count, "播放数：" + item.playcount)
        } else {
            helper.setVisible(R.id.txt_play_count, false)
        }
    }


    private fun getFormatDuration(time: String?): String? {
        if (time == null) return time
        if (TextUtils.isDigitsOnly(time)) {
            val totalSecond = Integer.parseInt(time)
            val min = totalSecond / 60
            val second = totalSecond % 60
            val buffer = StringBuffer().append(min).append(":").append(String.format("%02d", second))
            return buffer.toString()
        }
        return time
    }


}
