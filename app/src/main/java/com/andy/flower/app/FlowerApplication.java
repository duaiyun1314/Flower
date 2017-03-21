package com.andy.flower.app;

import android.text.TextUtils;

import com.andy.commons.BaseApplication;
import com.andy.flower.Constants;
import com.andy.flower.bean.PinsUser;
import com.andy.flower.utils.ImageUtils;
import com.andy.commons.model.preference.PrefKit;

/**
 * Created by andy on 16-6-6.
 */
public class FlowerApplication extends BaseApplication {
    private static FlowerApplication mApp;
    private PinsUser userInfoBean;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        initData();
        ImageUtils.initImageLoader(this);
    }

    @Override
    public String getAuthorization() {
        String authorization = userInfoBean.getAuthorization();
        return TextUtils.isEmpty(authorization) ? Constants.mClientInto : authorization;
    }

    public void setAuthorization(String authorization) {
        userInfoBean.setAuthorization(authorization);
    }

    private void initData() {
        userInfoBean = new PinsUser();
        getDataByLogin();
    }

    public static FlowerApplication from() {
        if (mApp == null) {
            mApp = new FlowerApplication();
        }
        return mApp;
    }

    public void getDataByLogin() {
        userInfoBean.setUsername(PrefKit.getString(this, Constants.USERNAME, ""));
        userInfoBean.setUser_id(PrefKit.getInt(this, Constants.USERID, 0));
        userInfoBean.setEmail(PrefKit.getString(this, Constants.USEREMAIL, ""));
        userInfoBean.setAvatarUrl(PrefKit.getString(this, Constants.USERHEADKEY, ""));
        String tokenType = PrefKit.getString(this, Constants.TOKENTYPE, "");
        String accessType = PrefKit.getString(this, Constants.TOKENACCESS, "");
        if (!TextUtils.isEmpty(tokenType) && !TextUtils.isEmpty(accessType)) {
            userInfoBean.setAuthorization(tokenType + " " + accessType);
        }

    }

    public PinsUser getUserInfoBean() {
        return userInfoBean;
    }

    public void setUserInfoBean(PinsUser userInfoBean) {
        if (userInfoBean == null) {
            this.userInfoBean.clear();
        } else {
            this.userInfoBean.set(userInfoBean);
        }
    }

}
