package com.andy.flower.presenter;

import android.view.Menu;

import com.andy.flower.bean.POJO.Comments;
import com.andy.flower.bean.POJO.PinsBean;
import com.andy.flower.bean.POJO.PinsUser;

import java.util.List;

/**
 * Created by andy on 16-6-12.
 */
public class UserDetailContract {

    public interface IView extends BaseIView {
        void initView();
        void setUser(PinsUser user);
    }

    public interface IPresenter {
        void actionFollow(boolean isCheck);

        void getUserDetail();
    }
}
