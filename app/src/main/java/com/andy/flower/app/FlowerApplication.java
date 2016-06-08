package com.andy.flower.app;

import android.app.Application;

import com.andy.flower.Constants;
import com.andy.flower.bean.POJO.UserInfoBean;
import com.andy.flower.utils.ImageLoaderUtil;
import com.andy.flower.utils.PrefKit;

/**
 * Created by andy on 16-6-6.
 */
public class FlowerApplication extends Application {
    private static FlowerApplication mApp;
    public boolean mIsLogin;
    private UserInfoBean userInfoBean;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        initData();
        ImageLoaderUtil.initImageLoader(this);
    }

    private void initData() {
        mIsLogin = PrefKit.getBoolean(this, Constants.IS_LOGIN, false);
        userInfoBean = new UserInfoBean();
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

    public void setLogin(boolean isLogin) {
        this.mIsLogin = isLogin;
    }

    public void getDataByLogin() {
        userInfoBean.setUsername(PrefKit.getString(this, Constants.USERNAME, ""));
        userInfoBean.setUser_id(PrefKit.getInt(this, Constants.USERID, 0));
        userInfoBean.setEmail(PrefKit.getString(this, Constants.USEREMAIL, ""));
        userInfoBean.setAvatarUrl(PrefKit.getString(this, Constants.USERHEADKEY, ""));
    }

    public UserInfoBean getUserInfoBean() {
        return userInfoBean;
    }

    public void setUserInfoBean(UserInfoBean userInfoBean) {
        this.userInfoBean = userInfoBean;
    }
}
