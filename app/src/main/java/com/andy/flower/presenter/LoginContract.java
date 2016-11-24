package com.andy.flower.presenter;

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
    }
}
