package com.andy.flower.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.andy.flower.Constants;
import com.andy.flower.adapter.PinsAdapter;
import com.andy.flower.bean.POJO.PinsBean;
import com.andy.flower.bean.POJO.PinsListBean;
import com.andy.flower.network.NetClient;
import com.andy.flower.network.NetUtils;
import com.andy.flower.network.apis.PinsAPI;
import com.andy.flower.views.widgets.RecyclerFootManger;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by andy on 16-6-15.
 */
public class PinsListPresenter extends ListPresenter<PinsAdapter> {
    public int maxId;
    public static final String LOAD_TYPE_CATEGORY = "load_type_category";
    public static final String LOAD_TYPE_USER = "load_type_user";
    public static final String LOAD_TYPE_USER_LIKES = "load_type_user_likes";


    public PinsListPresenter(Context context, ListContract.IView iView) {
        super(context, iView);
    }

    @Override
    public void loadNew(boolean isrefresh, Object... args) {
        iView.showTips(!isrefresh, false, false);
        iView.setFootStatus(RecyclerFootManger.STATUS_NORMAL, true);
        getHttpByType(false, args)
                .map(pinsListBean -> pinsListBean.getPins())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(getFilter(true))
                .subscribe(pinsBeen -> {
                    iView.onLoadFinish();
                    if (pinsBeen == null || pinsBeen.size() < 1) {
                        iView.showTips(false, true, false);
                    } else {
                        iView.showTips(false, false, false);
                    }
                    mAdapter.addItemTop(pinsBeen);
                    setMaxId((String) args[0], pinsBeen);
                }, throwable -> {
                    iView.onLoadFinish();
                    iView.showTips(false, false, true);
                    NetUtils.checkHttpException(mContext, throwable);
                });
    }

    @Override
    public void loadNext(Object... args) {
        getHttpByType(true, args)
                .map(pinsListBean -> pinsListBean.getPins())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(getFilter(false))
                .subscribe(pinsBeen -> {
                    iView.onLoadFinish();
                    mAdapter.addItemLast(pinsBeen);
                    setMaxId((String) args[0], pinsBeen);
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

    private void setMaxId(String loadType, List<PinsBean> result) {
        if (result != null && result.size() > 0) {
            switch (loadType) {
                case LOAD_TYPE_CATEGORY:
                case LOAD_TYPE_USER:
                    maxId = result.get(result.size() - 1).getPin_id();
                    break;
                case LOAD_TYPE_USER_LIKES:
                    maxId = result.get(result.size() - 1).getSeq();
                    break;
            }
        }
    }

    private Observable<PinsListBean> getHttpByType(boolean isLimit, Object... args) {
        String requestType = (String) args[0];
        if (requestType == LOAD_TYPE_CATEGORY) {
            String categoryId = (String) args[1];
            if (isLimit) {
                return NetClient.createService(PinsAPI.class).getPinsByCategoryANDLimit(mAuthorization, categoryId, maxId, Constants.PAGE_COUNT_LIMIT);
            } else {
                return NetClient.createService(PinsAPI.class).getPinsByCategory(mAuthorization, categoryId, Constants.PAGE_COUNT_LIMIT);
            }
        } else if (requestType == LOAD_TYPE_USER) {
            int userId = (int) args[1];
            if (isLimit) {
                return NetClient.createService(PinsAPI.class).getPinsByUserIdANDLimit(mAuthorization, userId, maxId, Constants.PAGE_COUNT_LIMIT);
            } else {
                return NetClient.createService(PinsAPI.class).getPinsByUserId(mAuthorization, userId, Constants.PAGE_COUNT_LIMIT);
            }
        } else if (requestType == LOAD_TYPE_USER_LIKES) {
            int userId = (int) args[1];
            if (isLimit) {
                return NetClient.createService(PinsAPI.class).getLikesPinsByUserIdANDLimit(mAuthorization, userId, maxId, Constants.PAGE_COUNT_LIMIT);
            } else {
                return NetClient.createService(PinsAPI.class).getLikesPinsByUserId(mAuthorization, userId, Constants.PAGE_COUNT_LIMIT);
            }
        }
        return null;
    }

    protected Func1<List<PinsBean>, Boolean> getFilter(boolean isLoadNew) {
        return new Func1<List<PinsBean>, Boolean>() {
            @Override
            public Boolean call(List<PinsBean> k) {
                if (k == null || k.size() == 0) {
                    if (!isLoadNew) {
                        iView.setFootStatus(RecyclerFootManger.STATUS_END, true);
                        return false;
                    } else {
                        iView.setFootStatus(RecyclerFootManger.STATUS_NORMAL, true);
                        return true;
                    }
                }

                if (k.size() < Constants.PAGE_COUNT_LIMIT) {
                    iView.setFootStatus(RecyclerFootManger.STATUS_END, true);
                    return true;
                } else {
                    iView.setFootStatus(RecyclerFootManger.STATUS_LOADING, true);
                    return true;
                }
            }
        };
    }

}
