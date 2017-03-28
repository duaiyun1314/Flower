package com.andy.flower.manager;

import com.andy.commons.model.http.RetrofitFactory;
import com.andy.commons.model.preference.PrefKit;
import com.andy.commons.utils.NetUtils;
import com.andy.flower.Constants;
import com.andy.flower.app.FlowerApplication;
import com.andy.flower.bean.AccessToken;
import com.andy.flower.bean.BoardItemInfoBean;
import com.andy.flower.bean.PinsUser;
import com.andy.flower.event.LoginEvent;
import com.andy.flower.apis.LoginAPI;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


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

        PrefKit.writeLong(FlowerApplication.from(), Constants.LOGINTIME, System.currentTimeMillis());
        PrefKit.writeString(FlowerApplication.from(), Constants.USERACCOUNT, userAccount);
        PrefKit.writeString(FlowerApplication.from(), Constants.USERPASSWORD, password);
        PrefKit.writeString(FlowerApplication.from(), Constants.TOKENACCESS, accessToken.getAccess_token());
        PrefKit.writeString(FlowerApplication.from(), Constants.TOKENTYPE, accessToken.getToken_type());
        PrefKit.writeString(FlowerApplication.from(), Constants.TOKENREFRESH, accessToken.getRefresh_token());
        PrefKit.writeInt(FlowerApplication.from(), Constants.TOKENEXPIRESIN, accessToken.getExpires_in());
        PrefKit.writeString(FlowerApplication.from(), Constants.USERACCOUNT, userAccount);
        PrefKit.writeString(FlowerApplication.from(), Constants.USERNAME, userInfoBean.getUsername());
        PrefKit.writeInt(FlowerApplication.from(), Constants.USERID, userInfoBean.getUser_id());
        PrefKit.writeString(FlowerApplication.from(), Constants.USERHEADKEY, userInfoBean.getAvatar().getKey());
        PrefKit.writeString(FlowerApplication.from(), Constants.USEREMAIL, userInfoBean.getEmail());
        PrefKit.writeString(FlowerApplication.from(), Constants.BOARDIDARRAY, boardId.toString());
        PrefKit.writeString(FlowerApplication.from(), Constants.BOARDTILTARRAY, boardTitle.toString());
    }

    public static void updateUserInfo(PinsUser userInfoBean) {
        if (userInfoBean == null) return;
        userInfoBean.setAvatarUrl(userInfoBean.getAvatar().getKey());
        FlowerApplication.from().setUserInfoBean(userInfoBean);
        PrefKit.writeLong(FlowerApplication.from(), Constants.LOGINTIME, System.currentTimeMillis());
        PrefKit.writeString(FlowerApplication.from(), Constants.USERNAME, userInfoBean.getUsername());
        PrefKit.writeInt(FlowerApplication.from(), Constants.USERID, userInfoBean.getUser_id());
        PrefKit.writeString(FlowerApplication.from(), Constants.USERHEADKEY, userInfoBean.getAvatar().getKey());
        PrefKit.writeString(FlowerApplication.from(), Constants.USEREMAIL, userInfoBean.getEmail());

    }

    /**
     * 退出
     */
    public static void logOut() {
        FlowerApplication.from().setUserInfoBean(null);
        PrefKit.clear(FlowerApplication.from());
        EventBus.getDefault().post(new LoginEvent(false));
    }

    public static void syncUserInfo() {
        if (FlowerApplication.from().getUserInfoBean().isLogin()) {
            RetrofitFactory.getInstance().createService(LoginAPI.class)
                    .getUserInfo()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<PinsUser>() {
                        @Override
                        public void onError(Throwable e) {
                            NetUtils.checkHttpException(FlowerApplication.from(), e);
                        }

                        @Override
                        public void onComplete() {

                        }

                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(PinsUser userBean) {
                            updateUserInfo(userBean);
                        }
                    });
        }
    }
}
