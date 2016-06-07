package com.andy.flower.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.andy.flower.Constants;
import com.andy.flower.network.NetClient;
import com.andy.flower.network.apis.LoginAPI;
import com.andy.flower.presenter.LoginContract.Presenter;
import com.andy.flower.utils.Logger;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by andy on 16-6-6.
 */
public class LoginPresenter implements Presenter {
    private Context mContext;
    private LoginContract.IView iView;

    public LoginPresenter(Context context, LoginContract.IView iView) {
        mContext = context;
        this.iView = iView;
        iView.setPresenter(this);

    }

    @Override
    public void login(final String username, final String password) {
        NetClient.createService(LoginAPI.class)
                .httpsTokenRx(Constants.mClientInto, "password", username, password)
                .map(responseBodyResponse -> {
                    ResponseBody body = responseBodyResponse.body();
                    try {
                        if (body != null) {
                            return body.string();
                        } else {
                            return null;
                        }
                    } catch (Exception e) {
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Logger.json(s);
                }, throwable -> {
                    Logger.e(throwable.getMessage());
                });

    }

    @Override
    public void register() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.Register_Url));
        mContext.startActivity(intent);

    }

    @Override
    public void saveUser() {

    }
}
