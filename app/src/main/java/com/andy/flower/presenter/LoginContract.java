package com.andy.flower.presenter;

import com.andy.flower.bean.POJO.AccessToken;
import com.andy.flower.bean.POJO.BoardItemInfoBean;
import com.andy.flower.bean.POJO.UserInfoBean;

import java.util.List;

/**
 * Created by andy on 16-6-6.
 */
public class LoginContract {
    public interface IView extends BaseIView {
        void showProgress(boolean show);
        void initProgressDialog();
    }

    public interface IPresenter {
        void login(final String username, final String password);

        void register();

        void saveUser(AccessToken accessToken, UserInfoBean userInfoBean, String userAccount, String password, List<BoardItemInfoBean> itemInfoBeanList);
    }
}
