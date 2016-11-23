package com.andy.flower.presenter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.andy.flower.Constants;
import com.andy.flower.R;
import com.andy.flower.app.FlowerApplication;
import com.andy.flower.bean.POJO.AccessToken;
import com.andy.flower.bean.POJO.BoardListInfoBean;
import com.andy.flower.bean.POJO.PinsUser;
import com.andy.flower.event.LoginEvent;
import com.andy.flower.manager.UserManager;
import com.andy.flower.network.NetClient;
import com.andy.flower.network.NetUtils;
import com.andy.flower.network.apis.LoginAPI;
import com.andy.flower.network.apis.UserAPI;
import com.andy.flower.presenter.LoginContract.IPresenter;
import com.andy.flower.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;


import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by andy on 16-6-6.
 */
public class LoginPresenter extends BasePresenter<LoginContract.IView> implements IPresenter {
    private AccessToken mAccessToken;
    private PinsUser mUserBean;

    public LoginPresenter(Activity context, LoginContract.IView iView) {
        super(context, iView);
    }

    @Override
    public void login(final String username, final String password) {
        NetClient.createService(LoginAPI.class)
                .getAccessToken(mAuthorization, "password", username, password)
                .flatMap(accessToekn -> {
                    mAccessToken = accessToekn;
                    mAuthorization = accessToekn.getToken_type() + " " + accessToekn.getAccess_token();
                    FlowerApplication.from().mAuthorization = mAuthorization;
                    return NetClient.createService(LoginAPI.class)
                            .getUserInfo(mAuthorization);
                })
                .flatMap(userInfoBean -> {
                    mUserBean = userInfoBean;
                    return NetClient.createService(UserAPI.class)
                            .httpsBoardListInfo(mAuthorization, Constants.OPERATEBOARDEXTRA);

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BoardListInfoBean>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        iView.showProgress(true);
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        iView.showProgress(false);
                        NetUtils.checkHttpException(mContext, e);
                    }

                    @Override
                    public void onNext(BoardListInfoBean boardListInfoBean) {
                        UserManager.saveUser(mAccessToken, mUserBean, username, password, boardListInfoBean.getBoards());
                        iView.showProgress(false);
                        EventBus.getDefault().post(new LoginEvent(true));
                        ToastUtil.show(mContext, R.string.login_successful);
                        ((Activity) mContext).finish();
                    }
                });
    }

    @Override
    public void register() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.Register_Url));
        mContext.startActivity(intent);
    }

}
