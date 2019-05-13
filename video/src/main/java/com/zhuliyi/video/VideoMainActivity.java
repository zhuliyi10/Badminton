package com.zhuliyi.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zhuliyi.commonlib.base.BaseActivity;
import com.zhuliyi.commonlib.image.ImageConfig;
import com.zhuliyi.commonlib.utils.AppUtils;
import com.zhuliyi.commonlib.utils.ToastUtils;
import com.zhuliyi.interactions.RouterHub;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Describe : 视频主页
 * Author : zhuly
 * Date : 2018-06-14
 */

@Route(path = RouterHub.VIDEO_VIDEOMAINACTIVITY)
public class VideoMainActivity extends BaseActivity {


    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_video_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }


}
