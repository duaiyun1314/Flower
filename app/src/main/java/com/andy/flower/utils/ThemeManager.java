package com.andy.flower.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.andy.flower.R;

/**
 * Created by andy on 16-6-6.
 */
public class ThemeManager {
    private static int[] ThemeRes = {
            R.style.AppTheme_Basic
            ,
            R.style.AppTheme_TEAL
            ,
            R.style.AppTheme_BROWN
            ,
            R.style.AppTheme_ORANGE
            ,
            R.style.AppTheme_PURPLE
            ,
            R.style.AppTheme_GREEN
            ,
            R.style.AppTheme_Night
    };

    public static void changeToTheme(Activity activity, int theme) {
        changeToTheme(activity, null, theme);
    }


    /**
     * Set the theme of the Activity, and restart it by creating a new Activity
     * of the same type.
     */
    public static void changeToTheme(Activity activity, Bundle saveData, int theme) {
        PrefKit.writeInt(activity, "theme", theme);
        activity.finish();
        Intent intent = new Intent(activity, activity.getClass());
        if (saveData != null) {
            intent.putExtras(saveData);
        }
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        activity.startActivity(intent);
    }

    /**
     * Set the theme of the activity, according to the configuration.
     */
    public static void onActivityCreateSetTheme(Activity activity) {
        int theme = ThemeRes[PrefKit.getInt(activity, "theme", 0)];
        activity.setTheme(theme);
    }

    public static int getCurrentTheme(Context context) {
        return PrefKit.getInt(context, "theme", 0);
    }

    public static boolean isNightTheme(Context context) {
        return PrefKit.getInt(context, "theme", 0) == ThemeRes.length - 1;
    }
}
