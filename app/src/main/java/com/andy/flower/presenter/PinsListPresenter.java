package com.andy.flower.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.andy.flower.Constants;
import com.andy.flower.adapter.PinsAdapter;
import com.andy.flower.bean.POJO.PinsBean;
import com.andy.flower.network.NetClient;
import com.andy.flower.network.NetUtils;
import com.andy.flower.network.apis.PinsAPI;
import com.andy.flower.utils.Logger;
import com.andy.flower.views.widgets.RecyclerFootManger;

import java.util.LinkedList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by andy on 16-6-15.
 */
public class PinsListPresenter extends ListPresenter<PinsAdapter> {
    public int maxId;

    public PinsListPresenter(Context context, ListContract.IView iView) {
        super(context, iView);
    }

    @Override
    public void loadNew(boolean isrefresh, Object... args) {
        String categoryId = (String) args[1];
        iView.showTips(!isrefresh, false, false);
        NetClient.createService(PinsAPI.class)
                .getPinsByCategory(mAuthorization, categoryId, Constants.PAGE_COUNT_LIMIT)
                .map(pinsListBean -> pinsListBean.getPins())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(getFilterFunc1())
                .subscribe(pinsBeen -> {
                    iView.onLoadFinish();
                    if (pinsBeen == null || pinsBeen.size() < 1) {
                        iView.showTips(false, true, false);
                    } else {
                        iView.showTips(false, false, false);
                    }
                    mAdapter.addItemTop(pinsBeen);
                    maxId = getMaxId(pinsBeen);
                }, throwable -> {
                    iView.onLoadFinish();
                    iView.showTips(false, false, true);
                    NetUtils.checkHttpException(mContext, throwable);
                });
    }

    @Override
    public void loadNext(Object... args) {
        String categoryId = (String) args[1];
        iView.setFootStatus(RecyclerFootManger.STATUS_LOADING, true);
        NetClient.createService(PinsAPI.class)
                .getPinsByCategoryANDLimit(mAuthorization, categoryId, maxId, Constants.PAGE_COUNT_LIMIT)
                .map(pinsListBean -> pinsListBean.getPins())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(getFilterFunc1())
                .subscribe(pinsBeen -> {
                    iView.onLoadFinish();
                    mAdapter.addItemLast(pinsBeen);
                    maxId = getMaxId(pinsBeen);
                }, throwable -> {
                    iView.onLoadFinish();
                    NetUtils.checkHttpException(mContext, throwable);
                    iView.setFootStatus(RecyclerFootManger.STATUS_NORMAL, true);

                });
    }

    @Override
    public PinsAdapter createAdapter(RecyclerView recyclerView) {
        return new PinsAdapter(mContext, recyclerView);
    }

    private int getMaxId(List<PinsBean> result) {
        return result.get(result.size() - 1).getPin_id();
    }

    protected Func1<List<PinsBean>, Boolean> getFilterFunc1() {
        return new Func1<List<PinsBean>, Boolean>() {
            @Override
            public Boolean call(List<PinsBean> k) {
                if (k == null || k.size() == 0) {
                    iView.setFootStatus(RecyclerFootManger.STATUS_END, true);
                    return false;
                }

                if (k.size() < Constants.PAGE_COUNT_LIMIT) {
                    iView.setFootStatus(RecyclerFootManger.STATUS_END, true);
                    return true;
                }
                ;
                return true;
            }
        };
    }

}
