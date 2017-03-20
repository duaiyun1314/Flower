package com.andy.flower.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.andy.commons.model.preference.PrefKit;
import com.andy.flower.R;

/**
 * Created by wanglu on 16/6/23.
 */
public class LoginPrefKit extends PrefKit {
    protected static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(context.getString(R.string.login_info
        ), Context.MODE_PRIVATE);
    }
}
