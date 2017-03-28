package com.andy.flower.presenter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.andy.commons.buscomponent.baselistview.presenter.BasePresenter;
import com.andy.commons.model.http.RetrofitFactory;
import com.andy.commons.utils.NetUtils;
import com.andy.commons.utils.ToastUtil;
import com.andy.flower.Constants;
import com.andy.flower.R;
import com.andy.flower.app.FlowerApplication;
import com.andy.flower.bean.AccessToken;
import com.andy.flower.bean.BoardListInfoBean;
import com.andy.flower.bean.PinsUser;
import com.andy.flower.event.LoginEvent;
import com.andy.flower.manager.UserManager;
import com.andy.flower.apis.LoginAPI;
import com.andy.flower.apis.UserAPI;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by andy on 16-6-6.
 */
public class LoginPresenter extends BasePresenter<LoginContract.IView> implements LoginContract.IPresenter {
    private AccessToken mAccessToken;
    private PinsUser mUserBean;

    public LoginPresenter(Activity context, LoginContract.IView iView) {
        super(context, iView);
    }

    @Override
    public void login(final String username, final String password) {
        LoginAPI loginAPI = RetrofitFactory.getInstance().createService(LoginAPI.class);
        UserAPI userAPI = RetrofitFactory.getInstance().createService(UserAPI.class);
        loginAPI.getAccessToken("password", username, password)
                .flatMap(accessToekn -> {
                    mAccessToken = accessToekn;
                    String authorization = accessToekn.getToken_type() + " " + accessToekn.getAccess_token();
                    FlowerApplication.from().setAuthorization(authorization);
                    return loginAPI.getUserInfo();
                })
                .flatMap(userInfoBean -> {
                    mUserBean = userInfoBean;
                    return userAPI.httpsBoardListInfo(Constants.OPERATEBOARDEXTRA);

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BoardListInfoBean>() {

                    @Override
                    public void onError(Throwable e) {
                        iView.showProgress(false);
                        FlowerApplication.from().setAuthorization("");
                        NetUtils.checkHttpException(mContext, e);
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        iView.showProgress(true);
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
