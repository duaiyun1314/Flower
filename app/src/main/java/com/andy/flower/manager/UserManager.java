package com.andy.flower.manager;

import android.app.Activity;
import android.text.TextUtils;

import com.andy.flower.Constants;
import com.andy.flower.R;
import com.andy.flower.app.FlowerApplication;
import com.andy.flower.bean.AccessToken;
import com.andy.flower.bean.BoardItemInfoBean;
import com.andy.flower.bean.BoardListInfoBean;
import com.andy.flower.bean.PinsUser;
import com.andy.flower.event.LoginEvent;
import com.andy.flower.network.NetClient;
import com.andy.flower.network.NetUtils;
import com.andy.flower.network.apis.LoginAPI;
import com.andy.flower.network.apis.UserAPI;
import com.andy.flower.utils.LoginPrefKit;
import com.andy.flower.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by andy.wang on 2016/11/23.
 */

public class UserManager {

    /**
     * 成功登录的用户信息本地保存，实现自动登录
     *
     * @param accessToken
     * @param userInfoBean
     * @param userAccount
     * @param password
     * @param mBoardList
     */
    public static void saveUser(AccessToken accessToken, PinsUser userInfoBean, String userAccount, String password, List<BoardItemInfoBean> mBoardList) {
        StringBuilder boardTitle = new StringBuilder();
        StringBuilder boardId = new StringBuilder();
        for (int i = 0, size = mBoardList.size(); i < size; i++) {
            boardTitle.append(mBoardList.get(i).getTitle());
            boardId.append(mBoardList.get(i).getBoard_id());
            if (i != size - 1) {
                boardTitle.append(Constants.SEPARATECOMMA);
                boardId.append(Constants.SEPARATECOMMA);
            }
        }
        userInfoBean.setAvatarUrl(userInfoBean.getAvatar().getKey());
        FlowerApplication.from().setUserInfoBean(userInfoBean);
        FlowerApplication.from().setLogin(true);

        LoginPrefKit.writeBoolean(FlowerApplication.from(), Constants.IS_LOGIN, true);
        LoginPrefKit.writeLong(FlowerApplication.from(), Constants.LOGINTIME, System.currentTimeMillis());
        LoginPrefKit.writeString(FlowerApplication.from(), Constants.USERACCOUNT, userAccount);
        LoginPrefKit.writeString(FlowerApplication.from(), Constants.USERPASSWORD, password);
        LoginPrefKit.writeString(FlowerApplication.from(), Constants.TOKENACCESS, accessToken.getAccess_token());
        LoginPrefKit.writeString(FlowerApplication.from(), Constants.TOKENTYPE, accessToken.getToken_type());
        LoginPrefKit.writeString(FlowerApplication.from(), Constants.TOKENREFRESH, accessToken.getRefresh_token());
        LoginPrefKit.writeInt(FlowerApplication.from(), Constants.TOKENEXPIRESIN, accessToken.getExpires_in());
        LoginPrefKit.writeString(FlowerApplication.from(), Constants.USERACCOUNT, userAccount);
        LoginPrefKit.writeString(FlowerApplication.from(), Constants.USERNAME, userInfoBean.getUsername());
        LoginPrefKit.writeInt(FlowerApplication.from(), Constants.USERID, userInfoBean.getUser_id());
        LoginPrefKit.writeString(FlowerApplication.from(), Constants.USERHEADKEY, userInfoBean.getAvatar().getKey());
        LoginPrefKit.writeString(FlowerApplication.from(), Constants.USEREMAIL, userInfoBean.getEmail());
        LoginPrefKit.writeString(FlowerApplication.from(), Constants.BOARDIDARRAY, boardId.toString());
        LoginPrefKit.writeString(FlowerApplication.from(), Constants.BOARDTILTARRAY, boardTitle.toString());
    }

    public static void updateUserInfo(PinsUser userInfoBean) {
        if (userInfoBean == null) return;
        userInfoBean.setAvatarUrl(userInfoBean.getAvatar().getKey());
        FlowerApplication.from().setUserInfoBean(userInfoBean);
        LoginPrefKit.writeBoolean(FlowerApplication.from(), Constants.IS_LOGIN, true);
        LoginPrefKit.writeLong(FlowerApplication.from(), Constants.LOGINTIME, System.currentTimeMillis());
        LoginPrefKit.writeString(FlowerApplication.from(), Constants.USERNAME, userInfoBean.getUsername());
        LoginPrefKit.writeInt(FlowerApplication.from(), Constants.USERID, userInfoBean.getUser_id());
        LoginPrefKit.writeString(FlowerApplication.from(), Constants.USERHEADKEY, userInfoBean.getAvatar().getKey());
        LoginPrefKit.writeString(FlowerApplication.from(), Constants.USEREMAIL, userInfoBean.getEmail());

    }

    /**
     * 退出
     */
    public static void logOut() {
        FlowerApplication.from().setUserInfoBean(null);
        FlowerApplication.from().mAuthorization = Constants.mClientInto;
        LoginPrefKit.clear(FlowerApplication.from());
        EventBus.getDefault().post(new LoginEvent(false));
    }

    public static void syncUserInfo() {
        if (!TextUtils.isEmpty(FlowerApplication.from().mAuthorization)) {
            String mAuthorization = FlowerApplication.from().mAuthorization;
            NetClient.createService(LoginAPI.class)
                    .getUserInfo(mAuthorization)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<PinsUser>() {
                        @Override
                        public void onStart() {
                            super.onStart();
                        }

                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            NetUtils.checkHttpException(FlowerApplication.from(), e);
                        }

                        @Override
                        public void onNext(PinsUser userBean) {
                            updateUserInfo(userBean);
                        }
                    });
        }
    }
}
