package com.andy.flower.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.andy.flower.Constants;
import com.andy.flower.adapter.PinsAdapter;
import com.andy.flower.network.NetClient;
import com.andy.flower.network.NetUtils;
import com.andy.flower.network.apis.PinsAPI;
import com.andy.flower.utils.Logger;

import java.util.LinkedList;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by andy on 16-6-15.
 */
public class PinsListPresenter extends ListPresenter<PinsAdapter> {
    public PinsListPresenter(Context context, ListContract.IView iView) {
        super(context, iView);
    }

    @Override
    public void loadNew(boolean isrefresh, Object... args) {
        String categoryId = (String) args[1];
        iView.showEmpty(false);
        iView.showFail(false);
        //如果是刷新加载新数据的情况，不需要中间部位正在刷新的提示
        iView.showLoading(!isrefresh);
        NetClient.createService(PinsAPI.class)
                .getPinsByCategory(mAuthorization, categoryId, Constants.PAGE_COUNT_LIMIT)
                .map(pinsListBean -> pinsListBean.getPins())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pinsBeen -> {
                    iView.onLoadFinish();
                    if (pinsBeen == null || pinsBeen.size() < 1) {
                        iView.showEmpty(true);
                        iView.showFail(false);
                        iView.showLoading(false);
                    } else {
                        iView.showEmpty(false);
                        iView.showFail(false);
                        iView.showLoading(false);
                    }
                    mAdapter.addItemTop(pinsBeen);
                }, throwable -> {
                    iView.onLoadFinish();
                    iView.showEmpty(false);
                    iView.showFail(true);
                    iView.showLoading(false);
                    NetUtils.checkHttpException(mContext, throwable);
                });
    }

    @Override
    public void loadNext(Object... args) {

    }

    @Override
    public PinsAdapter createAdapter() {
        Logger.d("createAdapter");
        return new PinsAdapter(mContext, new LinkedList<>());
    }

}
