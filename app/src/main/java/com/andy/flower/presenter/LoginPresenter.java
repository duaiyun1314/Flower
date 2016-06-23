package com.andy.flower.presenter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.andy.flower.Constants;
import com.andy.flower.R;
import com.andy.flower.app.FlowerApplication;
import com.andy.flower.app.LoginActivity;
import com.andy.flower.bean.POJO.AccessToken;
import com.andy.flower.bean.POJO.BoardItemInfoBean;
import com.andy.flower.bean.POJO.BoardListInfoBean;
import com.andy.flower.bean.POJO.UserInfoBean;
import com.andy.flower.event.LoginEvent;
import com.andy.flower.network.NetClient;
import com.andy.flower.network.NetUtils;
import com.andy.flower.network.apis.LoginAPI;
import com.andy.flower.network.apis.UserAPI;
import com.andy.flower.presenter.LoginContract.IPresenter;
import com.andy.flower.utils.LoginPrefKit;
import com.andy.flower.utils.PrefKit;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by andy on 16-6-6.
 */
public class LoginPresenter extends BasePresenter<LoginContract.IView> implements IPresenter {
    private AccessToken mAccessToken;
    private UserInfoBean mUserBean;

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
                        saveUser(mAccessToken, mUserBean, username, password, boardListInfoBean.getBoards());
                        iView.showProgress(false);
                        EventBus.getDefault().post(new LoginEvent(true));
                        Toast.makeText(mContext, mContext.getString(R.string.login_successful), Toast.LENGTH_SHORT).show();
                        ((Activity) mContext).finish();
                    }
                });

    }

    @Override
    public void register() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.Register_Url));
        mContext.startActivity(intent);

    }

    @Override
    public void saveUser(AccessToken accessToken, UserInfoBean userInfoBean, String userAccount, String password, List<BoardItemInfoBean> mBoardList) {

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
}
