package com.andy.commons;

import android.app.Application;

/**
 * Created by wanglu on 2017/3/21.
 */

public abstract class BaseApplication extends Application {
    private static BaseApplication baseApplication;


    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
    }

    public abstract String getAuthorization();

    public static BaseApplication getBaseApplication() {
        return baseApplication;
    }

}
