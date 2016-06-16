package com.andy.flower.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
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
import com.nostra13.universalimageloader.cache.disc.impl.ext.LruDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;

/**
 * 图片加载类
 */
public class ImageUtils {
    private static final int maxMemory = 1024 * 1024 * ((ActivityManager) FlowerApplication.from().getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass() / 8;
    private static final int maxDiskSize = 50 * 1024 * 1024;
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
                        .setBaseDirectoryName("IMAGE").build())
                .build();
        return config;
    }

    public static int setImageLayoutParams(ImageView iv, int itemWidth, int fileWidth, int fileHeight) {
        int height = 0;
        if (fileHeight / fileWidth <= Constants.IMAGE_MAXHEIGHT_SCALE) {
            height = itemWidth * fileHeight / fileWidth;
        } else {
            height = (int) (itemWidth * Constants.IMAGE_MAXHEIGHT_SCALE);
        }
        iv.getLayoutParams().height = height;
        return height;

    }
}
