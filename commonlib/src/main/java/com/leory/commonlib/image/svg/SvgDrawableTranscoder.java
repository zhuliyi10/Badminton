package com.leory.commonlib.image.svg;

import android.graphics.Picture;
import android.graphics.drawable.PictureDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.SimpleResource;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;
import com.caverock.androidsvg.SVG;

/**
 * Describe : 2、自定义ResourceTranscoder，将SVG转为Drawable对象
 * Author : leory
 * Date : 2019-06-11
 */
public class SvgDrawableTranscoder implements ResourceTranscoder<SVG, PictureDrawable> {

    @Nullable
    @Override
    public Resource<PictureDrawable> transcode(@NonNull Resource<SVG> toTranscode, @NonNull Options options) {
        SVG svg = toTranscode.get();
        Picture picture = svg.renderToPicture();
        PictureDrawable drawable = new PictureDrawable(picture);
        return new SimpleResource<>(drawable);
    }
}
