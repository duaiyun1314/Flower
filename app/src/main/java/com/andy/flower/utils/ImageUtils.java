package com.andy.flower.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

import com.andy.flower.Constants;
import com.andy.flower.R;
import com.andy.flower.app.FlowerApplication;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.cache.disk.DiskStorage;
import com.facebook.cache.disk.DynamicDefaultDiskStorage;
import com.facebook.cache.disk.FileCache;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.DiskStorageCacheFactory;
import com.facebook.imagepipeline.core.DiskStorageFactory;
import com.facebook.imagepipeline.core.FileCacheFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.io.File;

/**
 * 图片加载类
 */
public class ImageUtils {
    private static final int maxMemory = 1024 * 1024 * ((ActivityManager) FlowerApplication.from().getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass() / 8;
    private static final int maxDiskSize = 30 * 1024 * 1024;
    private static final int maxDiskCount = 15;
    private static final int maxLoadThreadCount = 5;

    public static void initImageLoader(Context context) {
        Fresco.initialize(context, getImagePipelineConfig(context));
    }

    private static ImagePipelineConfig getImagePipelineConfig(Context context) {
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(context)
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .setDownsampleEnabled(true)
                .setMainDiskCacheConfig(DiskCacheConfig.newBuilder(context)
                        .setMaxCacheSize(maxDiskSize)
                        .setBaseDirectoryName("IMAGE").build())
                .build();
        return config;
    }

    public static Drawable getTintListDrawable(Context mContext, int mResDrawableId, int mResTintId){
        Drawable drawable = DrawableCompat.wrap(ContextCompat.getDrawable(mContext, mResDrawableId).mutate());
        DrawableCompat.setTintList(drawable, ContextCompat.getColorStateList(mContext, mResTintId));
        return drawable;
    }

    public static Drawable getTintDrawable(Context mContext ,int mResDrawableId, @ColorInt int tint){
        Drawable drawable = DrawableCompat.wrap(ContextCompat.getDrawable(mContext, mResDrawableId).mutate());
        DrawableCompat.setTint(drawable,tint);
        return drawable;
    }

    public static float setImageLayoutParams(int fileWidth, int fileHeight) {
        float scale = (float) fileWidth / (float) fileHeight;
        if (scale <= Constants.IMAGE_MAXHEIGHT_SCALE) {
            scale = Constants.IMAGE_MAXHEIGHT_SCALE;
        }
        return scale;

    }
}
