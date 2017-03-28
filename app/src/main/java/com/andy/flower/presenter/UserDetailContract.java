package com.andy.flower.presenter;

import com.andy.flower.bean.PinsUser;

/**
 * Created by andy on 16-6-12.
 */
public class UserDetailContract {

    public interface IView  {
        void setUser(PinsUser user);
    }

    public interface IPresenter {

        void getUserDetail();
    }
}
