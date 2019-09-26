package com.leory.badminton.video.mvp.ui.widget

import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.view.Surface
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.leory.badminton.video.R
import com.shuyu.gsyvideoplayer.utils.Debuger
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoView

/**
 * 带封面
 * Created by guoshuyu on 2017/9/3.
 */

class SampleCoverVideo : StandardGSYVideoPlayer {

    lateinit var mCoverImage: ImageView

    lateinit var mCoverOriginUrl: String

    internal var mDefaultRes: Int = 0


    /******************* 下方重载方法，在播放开始不显示底部进度和按键，不需要可屏蔽  */

    protected var byStartedClick: Boolean = false

    constructor(context: Context, fullFlag: Boolean?) : super(context, fullFlag) {}

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    override fun init(context: Context) {
        super.init(context)
        mCoverImage = findViewById<View>(R.id.thumbImage) as ImageView

        if (mThumbImageViewLayout != null && (mCurrentState == -1 || mCurrentState == GSYVideoView.CURRENT_STATE_NORMAL || mCurrentState == GSYVideoView.CURRENT_STATE_ERROR)) {
            mThumbImageViewLayout.visibility = View.VISIBLE
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.video_layout_cover
    }

    fun loadCoverImage(url: String, res: Int) {
        mCoverOriginUrl = url
        mDefaultRes = res
        Glide.with(context.applicationContext)
                .setDefaultRequestOptions(
                        RequestOptions()
                                .frame(1000000)
                                .centerCrop()
                                .error(res)
                                .placeholder(res))
                .load(url)
                .into(mCoverImage)
    }

    override fun startWindowFullscreen(context: Context, actionBar: Boolean, statusBar: Boolean): GSYBaseVideoPlayer {
        val gsyBaseVideoPlayer = super.startWindowFullscreen(context, actionBar, statusBar)
        val sampleCoverVideo = gsyBaseVideoPlayer as SampleCoverVideo
        sampleCoverVideo.loadCoverImage(mCoverOriginUrl, mDefaultRes)
        return gsyBaseVideoPlayer
    }


    override fun showSmallVideo(size: Point, actionBar: Boolean, statusBar: Boolean): GSYBaseVideoPlayer {
        //下面这里替换成你自己的强制转化
        val sampleCoverVideo = super.showSmallVideo(size, actionBar, statusBar) as SampleCoverVideo
        sampleCoverVideo.mStartButton.visibility = View.GONE
        sampleCoverVideo.mStartButton = null
        return sampleCoverVideo
    }


    /******************* 下方两个重载方法，在播放开始前不屏蔽封面，不需要可屏蔽  */
    override fun onSurfaceUpdated(surface: Surface?) {
        super.onSurfaceUpdated(surface)
        if (mThumbImageViewLayout != null && mThumbImageViewLayout.visibility == View.VISIBLE) {
            mThumbImageViewLayout.visibility = View.INVISIBLE
        }
    }

    override fun setViewShowState(view: View?, visibility: Int) {
        if (view === mThumbImageViewLayout && visibility != View.VISIBLE) {
            return
        }
        super.setViewShowState(view, visibility)
    }

    override fun onClickUiToggle() {
        if (mIfCurrentIsFullscreen && mLockCurScreen && mNeedLockFull) {
            setViewShowState(mLockScreen, View.VISIBLE)
            return
        }
        byStartedClick = true
        super.onClickUiToggle()

    }

    override fun changeUiToNormal() {
        super.changeUiToNormal()
        byStartedClick = false
    }

    override fun changeUiToPreparingShow() {
        super.changeUiToPreparingShow()
        Debuger.printfLog("Sample changeUiToPreparingShow")
        setViewShowState(mBottomContainer, View.INVISIBLE)
        setViewShowState(mStartButton, View.INVISIBLE)
    }

    override fun changeUiToPlayingBufferingShow() {
        super.changeUiToPlayingBufferingShow()
        Debuger.printfLog("Sample changeUiToPlayingBufferingShow")
        if (!byStartedClick) {
            setViewShowState(mBottomContainer, View.INVISIBLE)
            setViewShowState(mStartButton, View.INVISIBLE)
        }
    }

    override fun changeUiToPlayingShow() {
        super.changeUiToPlayingShow()
        Debuger.printfLog("Sample changeUiToPlayingShow")
        if (!byStartedClick) {
            setViewShowState(mBottomContainer, View.INVISIBLE)
            setViewShowState(mStartButton, View.INVISIBLE)
        }
    }

    override fun startAfterPrepared() {
        super.startAfterPrepared()
        Debuger.printfLog("Sample startAfterPrepared")
        setViewShowState(mBottomContainer, View.INVISIBLE)
        setViewShowState(mStartButton, View.INVISIBLE)
        setViewShowState(mBottomProgressBar, View.VISIBLE)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        byStartedClick = true
        super.onStartTrackingTouch(seekBar)
    }
}
