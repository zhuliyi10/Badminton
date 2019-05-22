package com.leory.badminton.utils;

import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.leory.interactions.RouterHub;

/**
 * Describe : fragment工具
 * Author : zhuly
 * Date : 2019-05-22
 */
public class FragmentUtils {

    public static Fragment getNewsFragment(){
        Fragment fragment= (Fragment) ARouter.getInstance().build(RouterHub.NEWS_NEWMAINFRAGMENT).navigation();
        return fragment;
    }

    public static Fragment getVideoFragment(){
        Fragment fragment= (Fragment) ARouter.getInstance().build(RouterHub.VIDEO_VIDEOMAINFRAGMENT).navigation();
        return fragment;
    }
    public static Fragment getCircleFragment(){
        Fragment fragment= (Fragment) ARouter.getInstance().build(RouterHub.CIRCLE_CIRCLEMAINFRAGMENT).navigation();
        return fragment;
    }
    public static Fragment getMineFragment(){
        Fragment fragment= (Fragment) ARouter.getInstance().build(RouterHub.MINE_MINEMAINFRAGMENT).navigation();
        return fragment;
    }
}
