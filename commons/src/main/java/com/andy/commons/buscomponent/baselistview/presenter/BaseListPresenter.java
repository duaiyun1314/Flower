package com.andy.commons.buscomponent.baselistview.presenter;

import android.content.Context;


import com.andy.commons.buscomponent.baselistview.footmanager.RecyclerFootManger;
import com.andy.commons.buscomponent.baselistview.view.BaseListView;

import java.util.List;


/**
 * Created by andy.wang on 2016/12/13.
 */

public abstract class BaseListPresenter extends BasePresenter<BaseListContract.IView> implements BaseListContract.IPresenter {


    public BaseListPresenter(Context context, BaseListContract.IView iView) {
        super(context, iView);
    }


    /**
     * change the footview status according to count of  fetching data
     *
     * @param isLoadNew
     * @param <T>
     * @return
     */
    protected <T> void updateFootAfterFetch(List<T> k, boolean isLoadNew) {
        if (k == null || k.size() == 0) {
            if (!isLoadNew) {
                iView.setFootStatus(RecyclerFootManger.STATUS_END, true);
            } else {
                iView.setFootStatus(RecyclerFootManger.STATUS_NORMAL, true);
            }
        }

        if (k.size() < BaseListView.PAGE_COUNT_LIMIT) {
            iView.setFootStatus(RecyclerFootManger.STATUS_END, true);
        } else {
            iView.setFootStatus(RecyclerFootManger.STATUS_LOADING, true);
        }
    }

    protected void updateFootAfterFetchError(boolean isLoadNew) {
        if (isLoadNew) {
            iView.setFootStatus(RecyclerFootManger.STATUS_NORMAL, true);
        } else {
            iView.setFootStatus(RecyclerFootManger.STATUS_ERROR, true);
        }
    }
}
