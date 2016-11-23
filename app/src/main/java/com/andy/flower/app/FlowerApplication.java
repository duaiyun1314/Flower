package com.andy.flower.app;

import android.app.Application;
import android.text.TextUtils;

import com.andy.flower.Constants;
import com.andy.flower.bean.POJO.PinsUser;
import com.andy.flower.event.LoginEvent;
import com.andy.flower.utils.ImageUtils;
import com.andy.flower.utils.LoginPrefKit;
import com.andy.flower.utils.PrefKit;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by andy on 16-6-6.
 */
public class FlowerApplication extends Application {
    private static FlowerApplication mApp;
    public boolean mIsLogin;
    private PinsUser userInfoBean;
    public String mAuthorization;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        initData();
        ImageUtils.initImageLoader(this);
    }

    private void initData() {
        mIsLogin = PrefKit.getBoolean(this, Constants.IS_LOGIN, false);
        userInfoBean = new PinsUser();
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
        String tokenType = PrefKit.getString(this, Constants.TOKENTYPE, "");
        String accessType = PrefKit.getString(this, Constants.TOKENACCESS, "");
        if (!TextUtils.isEmpty(tokenType) && !TextUtils.isEmpty(accessType)) {
            mAuthorization = tokenType + " " + accessType;
        }
    }

    public PinsUser getUserInfoBean() {
        return userInfoBean;
    }

    public void setUserInfoBean(PinsUser userInfoBean) {
        this.userInfoBean = userInfoBean;
        if (userInfoBean == null) setLogin(false);
    }

}
