package com.andy.flower.presenter;

import com.andy.flower.bean.POJO.AccessToken;
import com.andy.flower.bean.POJO.BoardItemInfoBean;
import com.andy.flower.bean.POJO.PinsUser;

import java.util.List;

/**
 * Created by andy on 16-6-6.
 */
public class LoginContract {
    public interface IView extends BaseIView {
        void showProgress(boolean show);
    }

    public interface IPresenter {
        void login(final String username, final String password);

        void register();

        void saveUser(AccessToken accessToken, PinsUser userInfoBean, String userAccount, String password, List<BoardItemInfoBean> itemInfoBeanList);
    }
}
