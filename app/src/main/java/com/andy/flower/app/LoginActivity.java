package com.andy.flower.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.andy.flower.Constants;
import com.andy.flower.R;
import com.andy.flower.presenter.BasePresenter;
import com.andy.flower.presenter.LoginContract;
import com.andy.flower.presenter.LoginPresenter;
import com.andy.flower.utils.StringUtil;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * Created by andy on 16-6-6.
 */
public class LoginActivity extends BaseToolBarActivity implements LoginContract.IView {
    @BindView(R.id.actv_username)
    AutoCompleteTextView actvUsername;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.progress_login)
    ProgressBar progressLogin;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    @BindView(R.id.scroll_login_form)
    ScrollView scrollLoginForm;

    private LoginPresenter mPresenter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
        setTitle(R.string.login_label);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
        initProgressDialog();

        mPresenter = new LoginPresenter(this, this);


    }

    private void initView() {
        RxView.clicks(btnLogin)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        attemptLogin();
                    }
                });
        RxView.clicks(btnRegister)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        mPresenter.register();
                    }
                });
    }

    private void attemptLogin() {

        String userName = actvUsername.getText().toString();
        String userPassword = editPassword.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            actvUsername.setError(Constants.USERNAME_NULL);
            return;
        } else if (TextUtils.isEmpty(userPassword)) {
            editPassword.setError(Constants.PASSWORD_NULL);
            return;
        }
        if (!StringUtil.isEmail(userName)) {
            actvUsername.setError(Constants.EMAIL_INVALID);
            return;
        }
        mPresenter.login(userName, userPassword);

    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            mProgressDialog.show();
        } else {
            mProgressDialog.dismiss();
        }

    }

    @Override
    public void initProgressDialog() {
        mProgressDialog = new ProgressDialog(LoginActivity.this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(getString(R.string.logining_label));
        mProgressDialog.setProgressDrawable(getDrawable(R.drawable.progress_drawable));
    }

    @Override
    public void setPresenter(BasePresenter presenter) {

    }
}
