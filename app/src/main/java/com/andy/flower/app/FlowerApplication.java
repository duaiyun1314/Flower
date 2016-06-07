package com.andy.flower.app;

import android.app.Application;

import com.andy.flower.Constants;
import com.andy.flower.utils.PrefKit;

/**
 * Created by andy on 16-6-6.
 */
public class FlowerApplication extends Application {
    private static FlowerApplication mApp;
    public boolean mIsLogin;
    public String mUserName;
    public String mUserKey;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        initData();
    }

    private void initData() {
        mIsLogin = PrefKit.getBoolean(this, Constants.IS_LOGIN, false);
        if (mIsLogin) {
            getDataByLogin();
        }
    }

    public static FlowerApplication from() {
        if (mApp == null) {
            mApp = new FlowerApplication();
        }
        return mApp;
    }

    public boolean isLogin() {
        return mIsLogin;
    }

    public void getDataByLogin() {
        mUserName = PrefKit.getString(this, Constants.USERNAME, "");
        mUserKey = PrefKit.getInt(this, Constants.USERID, 0) + "";
    }
}
