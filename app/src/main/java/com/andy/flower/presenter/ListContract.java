package com.andy.flower.presenter;

import android.support.v7.widget.RecyclerView;

import com.andy.flower.bean.POJO.AccessToken;
import com.andy.flower.bean.POJO.BoardItemInfoBean;
import com.andy.flower.bean.POJO.UserInfoBean;

import java.util.List;

/**
 * Created by andy on 16-6-12.
 */
public class ListContract {

    public interface IView extends BaseIView {
        void showLoading(boolean show);

        void showEmpty(boolean show);

        void showFail(boolean show);

        void update(Object... args);
    }

    public interface IPresenter<T extends RecyclerView.Adapter> {
        void loadNew(Object... args);

        void loadNext(Object... args);

        T getAdapter();

        T createAdapter();
    }
}
