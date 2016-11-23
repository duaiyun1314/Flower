package com.andy.flower.manager;

import com.andy.flower.Constants;
import com.andy.flower.app.FlowerApplication;
import com.andy.flower.bean.POJO.AccessToken;
import com.andy.flower.bean.POJO.BoardItemInfoBean;
import com.andy.flower.bean.POJO.PinsUser;
import com.andy.flower.event.LoginEvent;
import com.andy.flower.utils.LoginPrefKit;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

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

    /**
     * 退出
     */
    public static void logOut() {
        FlowerApplication.from().setUserInfoBean(null);
        FlowerApplication.from().mAuthorization = Constants.mClientInto;
        LoginPrefKit.clear(FlowerApplication.from());
        EventBus.getDefault().post(new LoginEvent(false));
    }
}
