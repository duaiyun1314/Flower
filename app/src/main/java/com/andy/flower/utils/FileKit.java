package com.andy.flower.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.text.format.Formatter;

import com.andy.commons.utils.Logger;
import com.andy.flower.Constants;

import java.io.File;

/**
 * Created by andy on 16-6-22.
 */
public class FileKit {
    public static String getCacheSizeStr(Context context) {
        long size = getCacheSize(context);
        return Formatter.formatFileSize(context, size);

    }

    private static long getCacheSize(Context context) {
        long size = 0;
        size += getFolderSize(context.getApplicationContext().getExternalCacheDir());
        size += getFolderSize(context.getApplicationContext().getCacheDir());
        size += getFolderSize(context.getApplicationContext().getCacheDir().getAbsolutePath() + "../app_webview");
        return size;
    }


    public static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception ignored) {
        }
        return size;
    }

    public static long getFolderSize(String path) {
        File file = new File(path);
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception ignored) {
        }
        return size;
    }

    public static void deleteDir(File dir) {
        File to = new File(dir.getAbsolutePath() + System.currentTimeMillis());
        dir.renameTo(to);// in order to fix android java.io.IOException: open failed: EBUSY (Device or resource busy)
        // detail http://stackoverflow.com/questions/11539657/open-failed-ebusy-device-or-resource-busy
        if (to.isDirectory()) {
            String[] children = to.list();
            for (String aChildren : children) {
                File temp = new File(to, aChildren);
                if (temp.isDirectory()) {
                    deleteDir(temp);
                } else if (!temp.delete()) {
                    Logger.d("deleteSDCardFolder", "DELETE FAIL");
                }
            }
            to.delete();
        }
    }

    public static String getImageType(String type) {
        if (type == null || TextUtils.isEmpty(type)) {
            return ".jpeg";
        }
        if (type.contains("jpeg")) {
            return ".jpeg";
        }
        if (type.contains("png")) {
            return ".png";
        }
        if (type.contains("gif")) {
            return ".gif";
        }
        return null;
    }

    public static File getImageDir() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), Constants.IMAGE_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
}
