package com.leory.commonlib.image.svg;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.SimpleResource;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Describe : 1、自定义ResourceDecoder，将InputStream转为SVG对象：
 * Author : leory
 * Date : 2019-06-11
 */
public class SvgDecoder  implements ResourceDecoder<InputStream, SVG> {
    @Override
    public boolean handles(@NonNull InputStream source, @NonNull Options options) throws IOException {
        return true;
    }

    @Nullable
    @Override
    public Resource<SVG> decode(@NonNull InputStream source, int width, int height, @NonNull Options options) throws IOException {
        try {
            SVG svg = SVG.getFromInputStream(source);
            return new SimpleResource<>(svg);
        } catch (SVGParseException ex) {
            throw new IOException("Cannot load SVG from stream", ex);
        }
    }
}
