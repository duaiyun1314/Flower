package com.andy.flower.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * Util for {@link Toast} show
 */
public class ToastUtil {

    private static Toast mToast;

    public static void show(Context context, String content) {
        if (mToast == null) {
            mToast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(content);
        }
        mToast.show();
    }

    public static void show(Context context, @StringRes int contentRes) {
        if (mToast == null) {
            mToast = Toast.makeText(context, contentRes, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(contentRes);
        }
        mToast.show();
    }


}
