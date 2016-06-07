package com.andy.flower.presenter;

/**
 * Created by andy on 16-6-6.
 */
public class LoginContract {
    public interface IView extends BaseIView {
        void showDismiss(boolean show);
    }

    public interface Presenter extends BasePresenter {
        void login(final String username, final String password);

        void register();

        void saveUser();
    }
}
