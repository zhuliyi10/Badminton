package com.leory.badminton.utils

import android.support.v4.app.Fragment

import com.alibaba.android.arouter.launcher.ARouter
import com.leory.interactions.RouterHub

/**
 * Describe : fragment工具
 * Author : zhuly
 * Date : 2019-05-22
 */
object FragmentUtils {

    val newsFragment: Fragment = ARouter.getInstance().build(RouterHub.NEWS_NEWMAINFRAGMENT).navigation() as Fragment
    val videoFragment: Fragment = ARouter.getInstance().build(RouterHub.VIDEO_VIDEOMAINFRAGMENT).navigation() as Fragment
    val circleFragment: Fragment = ARouter.getInstance().build(RouterHub.CIRCLE_CIRCLEMAINFRAGMENT).navigation() as Fragment
    val mineFragment: Fragment = ARouter.getInstance().build(RouterHub.MINE_MINEMAINFRAGMENT).navigation() as Fragment
}
