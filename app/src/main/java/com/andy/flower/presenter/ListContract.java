package com.andy.flower.presenter;

import android.support.v7.widget.RecyclerView;

/**
 * Created by andy on 16-6-12.
 */
public class ListContract {

    public interface IView extends BaseIView {
        /**
         * show tips according to loading status
         * @param showLoading
         * @param showEmpty
         * @param showFail
         */
        void showTips(boolean showLoading, boolean showEmpty, boolean showFail);

        void setFootStatus(int status, boolean showView);

        void update(Object... args);

        void onLoadFinish();
    }

    public interface IPresenter<T extends RecyclerView.Adapter> {
        /**
         * 加载新数据（首次加载，或者刷新加载）
         * 首次加载显示中间进度条，刷新加载显示swiperefreshlayout的自带进度条
         *
         * @param isrefresh 是否为刷新加载
         * @param args
         */
        void loadNew(boolean isrefresh, Object... args);

        void loadNext(Object... args);

        T getAdapter(RecyclerView recyclerView);

        T createAdapter(RecyclerView recyclerView);
    }
}
