package com.andy.commons.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by andy on 16-6-16.
 */
public class ScreenSizeUtil {

    public static DisplayMetrics getScreenSize(Activity context) {
        DisplayMetrics metrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics;

    }
}
