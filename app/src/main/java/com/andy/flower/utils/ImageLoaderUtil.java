package com.andy.flower.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;

import com.andy.flower.R;
import com.andy.flower.app.FlowerApplication;
import com.nostra13.universalimageloader.cache.disc.impl.ext.LruDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;

/**
 * 图片加载类
 */
public class ImageLoaderUtil {
    private static final int maxMemory = 1024 * 1024 * ((ActivityManager) FlowerApplication.from().getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass() / 8;
    private static final int maxDiskSize = 50 * 1024 * 1024;
    private static final int maxDiskCount = 15;

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCache(getLruDiskCache(context))
                .memoryCache(getMemoryCache())
                .defaultDisplayImageOptions(getDefaultOptions())
                .tasksProcessingOrder(QueueProcessingType.LIFO);
        ImageLoaderConfiguration config = builder.build();
        // Initialize ImageLoader w     ith configuration.
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(config);
    }

    private static LruDiskCache getLruDiskCache(Context context) {
        LruDiskCache lruDiskCache = null;
        try {
            File file = getDiskCacheDir(context, "flowerImages");
            lruDiskCache = new LruDiskCache(file, null
                    , new Md5FileNameGenerator(), maxDiskSize, maxDiskCount);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lruDiskCache;
    }

    private static MemoryCache getMemoryCache() {
        MemoryCache memoryCache;
        memoryCache = new LruMemoryCache(maxMemory);
        return memoryCache;
    }

    public static File getDiskCacheDir(Context context, String folderName) {
        String cachePath;
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            cachePath = context.getExternalCacheDir().getAbsolutePath();
        } else {
            cachePath = context.getCacheDir().getAbsolutePath();
        }
        return new File(cachePath + File.separator + folderName);
    }

    public static DisplayImageOptions getDefaultOptions() {
        return new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(R.drawable.empty_photo)
                .showImageOnFail(R.drawable.empty_photo)
                .showImageForEmptyUri(R.drawable.empty_photo)
                .build();
    }

    public static DisplayImageOptions getNoEmptyOptions() {
        return new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }
}
