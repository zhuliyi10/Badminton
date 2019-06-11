package com.leory.commonlib.utils;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.widget.ImageView;

import com.leory.commonlib.image.ImageConfig;
import com.leory.commonlib.image.glide.GlideApp;
import com.leory.commonlib.image.svg.SvgSoftwareLayerSetter;

/**
 * Describe : 图片工具
 * Author : leory
 * Date : 2019-06-11
 */
public class ImageUtils {
    public static void loadImage(Context context, ImageView imageView, String url) {
        ImageConfig config = new ImageConfig.Builder()
                .imageView(imageView)
                .url(url)
                .build();
        AppUtils.obtainImageLoader().loadImage(context, config);
    }
    public static void loadSvg(Context context, ImageView imageView, String url){
        GlideApp.with(context).as(PictureDrawable.class)
                .listener(new SvgSoftwareLayerSetter())
                .load(url)
                .into(imageView);
    }
}
