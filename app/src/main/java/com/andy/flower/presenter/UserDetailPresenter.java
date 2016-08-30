package com.andy.flower.presenter;

import android.content.Context;

import com.andy.flower.bean.POJO.PinsUser;
import com.andy.flower.network.NetClient;
import com.andy.flower.network.apis.UserAPI;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by andy.wang on 2016/8/29.
 */
public class UserDetailPresenter extends BasePresenter<UserDetailContract.IView> implements UserDetailContract.IPresenter {
    private PinsUser mUser;

    public UserDetailPresenter(Context context, UserDetailContract.IView iView, PinsUser user) {
        this(context, iView);
        mUser = user;
    }

    public UserDetailPresenter(Context context, UserDetailContract.IView iView) {
        super(context, iView);
    }


    @Override
    public void getUserDetail() {
        NetClient.createService(UserAPI.class)
                .getUserDetail(mApp.mAuthorization, mUser.getUser_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(user1 -> user1 != null)
                .subscribe(user -> {
                    mUser = user;
                    iView.setUser(mUser);
                });
    }
}
