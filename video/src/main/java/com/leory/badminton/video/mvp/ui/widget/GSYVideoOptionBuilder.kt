package com.leory.badminton.video.mvp.ui.widget

import android.graphics.drawable.Drawable
import android.view.View
import com.shuyu.gsyvideoplayer.listener.GSYVideoProgressListener
import com.shuyu.gsyvideoplayer.listener.LockClickListener
import com.shuyu.gsyvideoplayer.listener.VideoAllCallBack
import com.shuyu.gsyvideoplayer.render.effect.NoEffect
import com.shuyu.gsyvideoplayer.render.view.GSYVideoGLView
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer
import java.io.File

/**
 * Created by guoshuyu on 2017/7/3.
 *
 *
 * 配置工具类吧。
 *
 *
 * 不是一个正常的Builder,这只是集合了所有设置配置而已.
 * 每个配置其实可以在对应的video接口中找到单独设置
 * 这只是方便使用
 */

class GSYVideoOptionBuilder {

    //退出全屏显示的案件图片
    private var mShrinkImageRes = -1

    //全屏显示的案件图片
    private var mEnlargeImageRes = -1

    //播放的tag，防止错误，因为普通的url也可能重复
    private var mPlayPosition = -22

    //触摸快进dialog的进度高量颜色
    private var mDialogProgressHighLightColor = -11

    //触摸快进dialog的进度普通颜色
    private var mDialogProgressNormalColor = -11

    //触摸隐藏等待时间
    private var mDismissControlTime = 2500

    //从哪个开始播放
    private var mSeekOnStart: Long = -1

    //触摸滑动进度的比例系数
    private var mSeekRatio = 1f

    //播放速度
    private var mSpeed = 1f

    //是否隐藏虚拟按键
    private var mHideKey = true

    //是否使用全屏动画效果
    private var mShowFullAnimation = true

    //是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏，注意，这时候默认旋转无效
    private var mAutoFullWithSize = false

    //是否需要显示流量提示
    private var mNeedShowWifiTip = true

    //是否自动旋转
    private var mRotateViewAuto = true

    //当前全屏是否锁定全屏
    private var mLockLand = false

    //循环
    private var mLooping = false

    //是否支持非全屏滑动触摸有效
    private var mIsTouchWiget = true

    //是否支持全屏滑动触摸有效
    private var mIsTouchWigetFull = true

    //是否显示暂停图片
    private var mShowPauseCover = true

    //旋转使能后是否跟随系统设置
    private var mRotateWithSystem = true

    //边播放边缓存
    private var mCacheWithPlay: Boolean = false

    //是否需要锁定屏幕
    private var mNeedLockFull: Boolean = false

    //点击封面播放
    private var mThumbPlay: Boolean = false

    //是否需要变速不变调
    private var mSounchTouch: Boolean = false

    //是否需要lazy的setup
    private var mSetUpLazy = false

    //Prepared之后是否自动开始播放
    private var mStartAfterPrepared = true

    //是否播放器当失去音频焦点
    private var mReleaseWhenLossAudio = true

    //是否需要在利用window实现全屏幕的时候隐藏actionbar
    private var mActionBar = false

    //是否需要在利用window实现全屏幕的时候隐藏statusbar
    private var mStatusBar = false

    //播放的tag，防止错误，因为普通的url也可能重复
    private var mPlayTag = ""

    //播放url
    private var mUrl: String? = null

    //视频title
    private var mVideoTitle: String? = null

    // 是否需要覆盖拓展类型
    private var mOverrideExtension: String? = null

    //是否自定义的缓冲文件路径
    private var mCachePath: File? = null

    //http请求头
    private var mMapHeadData: Map<String, String>? = null

    //视频状体回调
    private var mVideoAllCallBack: VideoAllCallBack? = null

    //点击锁屏的回调
    private var mLockClickListener: LockClickListener? = null

    //封面
    private var mThumbImageView: View? = null

    //底部进度条样式
    private var mBottomProgressDrawable: Drawable? = null

    //底部进度条样式
    private var mBottomShowProgressDrawable: Drawable? = null

    //底部进度条小圆点样式
    private var mBottomShowProgressThumbDrawable: Drawable? = null

    //音量进度条样式
    private var mVolumeProgressDrawable: Drawable? = null

    //滑动dialog进度条样式
    private var mDialogProgressBarDrawable: Drawable? = null

    //滤镜
    private var mEffectFilter: GSYVideoGLView.ShaderInterface = NoEffect()

    //进度回调
    private var mGSYVideoProgressListener: GSYVideoProgressListener? = null


    /**
     * 是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏，注意，这时候默认旋转无效
     *
     * @param autoFullWithSize 默认false
     */
    fun setAutoFullWithSize(autoFullWithSize: Boolean): GSYVideoOptionBuilder {
        this.mAutoFullWithSize = autoFullWithSize
        return this
    }

    /**
     * 全屏动画
     *
     * @param showFullAnimation 是否使用全屏动画效果
     */
    fun setShowFullAnimation(showFullAnimation: Boolean): GSYVideoOptionBuilder {
        this.mShowFullAnimation = showFullAnimation
        return this
    }

    /**
     * 设置循环
     */
    fun setLooping(looping: Boolean): GSYVideoOptionBuilder {
        this.mLooping = looping
        return this
    }


    /**
     * 设置播放过程中的回调
     *
     * @param mVideoAllCallBack
     */
    fun setVideoAllCallBack(mVideoAllCallBack: VideoAllCallBack): GSYVideoOptionBuilder {
        this.mVideoAllCallBack = mVideoAllCallBack
        return this
    }

    /**
     * 是否开启自动旋转
     */
    fun setRotateViewAuto(rotateViewAuto: Boolean): GSYVideoOptionBuilder {
        this.mRotateViewAuto = rotateViewAuto
        return this
    }

    /**
     * 一全屏就锁屏横屏，默认false竖屏，可配合setRotateViewAuto使用
     */
    fun setLockLand(lockLand: Boolean): GSYVideoOptionBuilder {
        this.mLockLand = lockLand
        return this
    }

    /**
     * 播放速度
     */
    fun setSpeed(speed: Float): GSYVideoOptionBuilder {
        this.mSpeed = speed
        return this
    }


    /**
     * 变声不变调
     */
    fun setSoundTouch(soundTouch: Boolean): GSYVideoOptionBuilder {
        this.mSounchTouch = soundTouch
        return this
    }

    /**
     * 全屏隐藏虚拟按键，默认打开
     */
    fun setHideKey(hideKey: Boolean): GSYVideoOptionBuilder {
        this.mHideKey = hideKey
        return this
    }

    /**
     * 是否可以滑动界面改变进度，声音等
     * 默认true
     */
    fun setIsTouchWiget(isTouchWiget: Boolean): GSYVideoOptionBuilder {
        this.mIsTouchWiget = isTouchWiget
        return this
    }

    /**
     * 是否可以全屏滑动界面改变进度，声音等
     * 默认 true
     */
    fun setIsTouchWigetFull(isTouchWigetFull: Boolean): GSYVideoOptionBuilder {
        this.mIsTouchWigetFull = isTouchWigetFull
        return this
    }


    /**
     * 是否需要显示流量提示,默认true
     */
    fun setNeedShowWifiTip(needShowWifiTip: Boolean): GSYVideoOptionBuilder {
        this.mNeedShowWifiTip = needShowWifiTip
        return this
    }

    /**
     * 设置右下角 显示切换到全屏 的按键资源
     * 必须在setUp之前设置
     * 不设置使用默认
     */
    fun setEnlargeImageRes(mEnlargeImageRes: Int): GSYVideoOptionBuilder {
        this.mEnlargeImageRes = mEnlargeImageRes
        return this
    }

    /**
     * 设置右下角 显示退出全屏 的按键资源
     * 必须在setUp之前设置
     * 不设置使用默认
     */
    fun setShrinkImageRes(mShrinkImageRes: Int): GSYVideoOptionBuilder {
        this.mShrinkImageRes = mShrinkImageRes
        return this
    }


    /**
     * 是否需要加载显示暂停的cover图片
     * 打开状态下，暂停退到后台，再回到前台不会显示黑屏，但可以对某些机型有概率出现OOM
     * 关闭情况下，暂停退到后台，再回到前台显示黑屏
     *
     * @param showPauseCover 默认true
     */
    fun setShowPauseCover(showPauseCover: Boolean): GSYVideoOptionBuilder {
        this.mShowPauseCover = showPauseCover
        return this
    }

    /**
     * 调整触摸滑动快进的比例
     *
     * @param seekRatio 滑动快进的比例，默认1。数值越大，滑动的产生的seek越小
     */
    fun setSeekRatio(seekRatio: Float): GSYVideoOptionBuilder {
        if (seekRatio < 0) {
            return this
        }
        this.mSeekRatio = seekRatio
        return this
    }

    /**
     * 是否更新系统旋转，false的话，系统禁止旋转也会跟着旋转
     *
     * @param rotateWithSystem 默认true
     */
    fun setRotateWithSystem(rotateWithSystem: Boolean): GSYVideoOptionBuilder {
        this.mRotateWithSystem = rotateWithSystem
        return this
    }

    /**
     * 播放tag防止错误，因为普通的url也可能重复
     *
     * @param playTag 保证不重复就好
     */
    fun setPlayTag(playTag: String): GSYVideoOptionBuilder {
        this.mPlayTag = playTag
        return this
    }


    /**
     * 设置播放位置防止错位
     */
    fun setPlayPosition(playPosition: Int): GSYVideoOptionBuilder {
        this.mPlayPosition = playPosition
        return this
    }

    /**
     * 从哪里开始播放
     * 目前有时候前几秒有跳动问题，毫秒
     * 需要在startPlayLogic之前，即播放开始之前
     */
    fun setSeekOnStart(seekOnStart: Long): GSYVideoOptionBuilder {
        this.mSeekOnStart = seekOnStart
        return this
    }

    /**
     * 播放url
     *
     * @param url
     */
    fun setUrl(url: String): GSYVideoOptionBuilder {
        this.mUrl = url
        return this
    }

    /**
     * 视频title
     *
     * @param videoTitle
     */
    fun setVideoTitle(videoTitle: String): GSYVideoOptionBuilder {
        this.mVideoTitle = videoTitle
        return this
    }

    /**
     * 是否边缓存，m3u8等无效
     *
     * @param cacheWithPlay
     */
    fun setCacheWithPlay(cacheWithPlay: Boolean): GSYVideoOptionBuilder {
        this.mCacheWithPlay = cacheWithPlay
        return this
    }

    /**
     * 准备成功之后立即播放
     *
     * @param startAfterPrepared 默认true，false的时候需要在prepared后调用startAfterPrepared()
     */
    fun setStartAfterPrepared(startAfterPrepared: Boolean): GSYVideoOptionBuilder {
        this.mStartAfterPrepared = startAfterPrepared
        return this
    }


    /**
     * 长时间失去音频焦点，暂停播放器
     *
     * @param releaseWhenLossAudio 默认true，false的时候只会暂停
     */
    fun setReleaseWhenLossAudio(releaseWhenLossAudio: Boolean): GSYVideoOptionBuilder {
        this.mReleaseWhenLossAudio = releaseWhenLossAudio
        return this
    }

    /**
     * 自定指定缓存路径，推荐不设置，使用默认路径
     *
     * @param cachePath
     */
    fun setCachePath(cachePath: File): GSYVideoOptionBuilder {
        this.mCachePath = cachePath
        return this
    }

    /**
     * 设置请求的头信息
     *
     * @param mapHeadData
     */
    fun setMapHeadData(mapHeadData: Map<String, String>): GSYVideoOptionBuilder {
        this.mMapHeadData = mapHeadData
        return this
    }


    /**
     * 进度回调
     */
    fun setGSYVideoProgressListener(videoProgressListener: GSYVideoProgressListener): GSYVideoOptionBuilder {
        this.mGSYVideoProgressListener = videoProgressListener
        return this
    }


    /***
     * 设置封面
     */
    fun setThumbImageView(view: View): GSYVideoOptionBuilder {
        mThumbImageView = view
        return this
    }


    /**
     * 底部进度条-弹出的
     */
    fun setBottomShowProgressBarDrawable(drawable: Drawable, thumb: Drawable): GSYVideoOptionBuilder {
        mBottomShowProgressDrawable = drawable
        mBottomShowProgressThumbDrawable = thumb
        return this
    }

    /**
     * 底部进度条-非弹出
     */
    fun setBottomProgressBarDrawable(drawable: Drawable): GSYVideoOptionBuilder {
        mBottomProgressDrawable = drawable
        return this
    }

    /**
     * 声音进度条
     */
    fun setDialogVolumeProgressBar(drawable: Drawable): GSYVideoOptionBuilder {
        mVolumeProgressDrawable = drawable
        return this
    }


    /**
     * 中间进度条
     */
    fun setDialogProgressBar(drawable: Drawable): GSYVideoOptionBuilder {
        mDialogProgressBarDrawable = drawable
        return this
    }

    /**
     * 中间进度条字体颜色
     */
    fun setDialogProgressColor(highLightColor: Int, normalColor: Int): GSYVideoOptionBuilder {
        mDialogProgressHighLightColor = highLightColor
        mDialogProgressNormalColor = normalColor
        return this
    }

    /**
     * 是否点击封面可以播放
     */
    fun setThumbPlay(thumbPlay: Boolean): GSYVideoOptionBuilder {
        this.mThumbPlay = thumbPlay
        return this
    }

    /**
     * 是否需要全屏锁定屏幕功能
     * 如果单独使用请设置setIfCurrentIsFullscreen为true
     */
    fun setNeedLockFull(needLoadFull: Boolean): GSYVideoOptionBuilder {
        this.mNeedLockFull = needLoadFull
        return this
    }

    /**
     * 锁屏点击
     */
    fun setLockClickListener(lockClickListener: LockClickListener): GSYVideoOptionBuilder {
        this.mLockClickListener = lockClickListener
        return this
    }

    /**
     * 设置触摸显示控制ui的消失时间
     *
     * @param dismissControlTime 毫秒，默认2500
     */
    fun setDismissControlTime(dismissControlTime: Int): GSYVideoOptionBuilder {
        this.mDismissControlTime = dismissControlTime
        return this
    }

    /**
     * 设置滤镜效果
     */
    fun setEffectFilter(effectFilter: GSYVideoGLView.ShaderInterface): GSYVideoOptionBuilder {
        this.mEffectFilter = effectFilter
        return this
    }

    /**
     * 是否需要覆盖拓展类型，目前只针对exoPlayer内核模式有效
     * @param overrideExtension 比如传入 m3u8,mp4,avi 等类型
     */
    fun setOverrideExtension(overrideExtension: String): GSYVideoOptionBuilder {
        this.mOverrideExtension = overrideExtension
        return this
    }

    /**
     * 在播放前才真正执行setup
     * 目前弃用，请使用正常setup
     */
    @Deprecated("")
    fun setSetUpLazy(setUpLazy: Boolean): GSYVideoOptionBuilder {
        this.mSetUpLazy = setUpLazy
        return this
    }

    fun setFullHideActionBar(actionBar: Boolean): GSYVideoOptionBuilder {
        this.mActionBar = actionBar
        return this
    }

    fun setFullHideStatusBar(statusBar: Boolean): GSYVideoOptionBuilder {
        this.mStatusBar = statusBar
        return this
    }

    fun build(gsyVideoPlayer: StandardGSYVideoPlayer) {
        if (mBottomShowProgressDrawable != null && mBottomShowProgressThumbDrawable != null) {
            gsyVideoPlayer.setBottomShowProgressBarDrawable(mBottomShowProgressDrawable, mBottomShowProgressThumbDrawable)
        }
        if (mBottomProgressDrawable != null) {
            gsyVideoPlayer.setBottomProgressBarDrawable(mBottomProgressDrawable)
        }
        if (mVolumeProgressDrawable != null) {
            gsyVideoPlayer.setDialogVolumeProgressBar(mVolumeProgressDrawable)
        }

        if (mDialogProgressBarDrawable != null) {
            gsyVideoPlayer.setDialogProgressBar(mDialogProgressBarDrawable)
        }

        if (mDialogProgressHighLightColor > 0 && mDialogProgressNormalColor > 0) {
            gsyVideoPlayer.setDialogProgressColor(mDialogProgressHighLightColor, mDialogProgressNormalColor)
        }

        build(gsyVideoPlayer as GSYBaseVideoPlayer)
    }

    fun build(gsyVideoPlayer: GSYBaseVideoPlayer) {
        gsyVideoPlayer.playTag = mPlayTag
        gsyVideoPlayer.playPosition = mPlayPosition

        gsyVideoPlayer.setThumbPlay(mThumbPlay)

        if (mThumbImageView != null) {
            gsyVideoPlayer.thumbImageView = mThumbImageView
        }

        gsyVideoPlayer.isNeedLockFull = mNeedLockFull

        if (mLockClickListener != null) {
            gsyVideoPlayer.setLockClickListener(mLockClickListener)
        }

        gsyVideoPlayer.dismissControlTime = mDismissControlTime


        if (mSeekOnStart > 0) {
            gsyVideoPlayer.seekOnStart = mSeekOnStart
        }

        gsyVideoPlayer.isShowFullAnimation = mShowFullAnimation
        gsyVideoPlayer.isLooping = mLooping
        if (mVideoAllCallBack != null) {
            gsyVideoPlayer.setVideoAllCallBack(mVideoAllCallBack)
        }
        if (mGSYVideoProgressListener != null) {
            gsyVideoPlayer.setGSYVideoProgressListener(mGSYVideoProgressListener)
        }
        gsyVideoPlayer.overrideExtension = mOverrideExtension
        gsyVideoPlayer.isAutoFullWithSize = mAutoFullWithSize
        gsyVideoPlayer.isRotateViewAuto = mRotateViewAuto
        gsyVideoPlayer.isLockLand = mLockLand
        gsyVideoPlayer.setSpeed(mSpeed, mSounchTouch)
        gsyVideoPlayer.isHideKey = mHideKey
        gsyVideoPlayer.setIsTouchWiget(mIsTouchWiget)
        gsyVideoPlayer.setIsTouchWigetFull(mIsTouchWigetFull)
        gsyVideoPlayer.isNeedShowWifiTip = mNeedShowWifiTip
        gsyVideoPlayer.effectFilter = mEffectFilter
        gsyVideoPlayer.isStartAfterPrepared = mStartAfterPrepared
        gsyVideoPlayer.isReleaseWhenLossAudio = mReleaseWhenLossAudio
        gsyVideoPlayer.isFullHideActionBar = mActionBar
        gsyVideoPlayer.isFullHideStatusBar = mStatusBar
        if (mEnlargeImageRes > 0) {
            gsyVideoPlayer.enlargeImageRes = mEnlargeImageRes
        }
        if (mShrinkImageRes > 0) {
            gsyVideoPlayer.shrinkImageRes = mShrinkImageRes
        }
        gsyVideoPlayer.isShowPauseCover = mShowPauseCover
        gsyVideoPlayer.seekRatio = mSeekRatio
        gsyVideoPlayer.isRotateWithSystem = mRotateWithSystem
        if (mSetUpLazy) {
            gsyVideoPlayer.setUpLazy(mUrl, mCacheWithPlay, mCachePath, mMapHeadData, mVideoTitle)
        } else {
            gsyVideoPlayer.setUp(mUrl, mCacheWithPlay, mCachePath, mMapHeadData, mVideoTitle)
        }
    }

}
