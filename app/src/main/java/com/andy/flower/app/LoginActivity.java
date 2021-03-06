package com.andy.flower.app;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.andy.commons.activity.BaseActivity;
import com.andy.flower.Constants;
import com.andy.flower.R;
import com.andy.flower.presenter.LoginContract;
import com.andy.flower.presenter.LoginPresenter;
import com.andy.flower.utils.StringUtil;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andy on 16-6-6.
 */
public class LoginActivity extends BaseActivity implements LoginContract.IView {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
        setTitle(R.string.login_label);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mPresenter = new LoginPresenter(this, this);
    }

    @Override
    protected void configViews() {
        RxView.clicks(btnLogin)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(avoid -> attemptLogin());
        RxView.clicks(btnRegister)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> mPresenter.register());
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
            showProgressDialog(R.string.hold_on, R.string.logining_label);
        } else {
            dismissProgressDialog();
        }
    }

}
