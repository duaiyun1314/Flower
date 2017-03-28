package com.andy.flower.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.andy.commons.buscomponent.baselistview.presenter.BaseListContract;
import com.andy.commons.buscomponent.baselistview.presenter.BaseListPresenter;
import com.andy.commons.model.http.RetrofitFactory;
import com.andy.commons.utils.NetUtils;
import com.andy.flower.Constants;
import com.andy.flower.bean.PinsBean;
import com.andy.flower.bean.PinsListBean;
import com.andy.flower.apis.PinsAPI;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by andy on 16-6-15.
 */
public class PinsListPresenter extends BaseListPresenter {
    public int maxId;
    public static final String LOAD_TYPE_CATEGORY = "load_type_category";
    public static final String LOAD_TYPE_USER = "load_type_user";
    public static final String LOAD_TYPE_USER_LIKES = "load_type_user_likes";


    public PinsListPresenter(Context context, BaseListContract.IView iView) {
        super(context, iView);
    }

    @Override
    public void loadNew(Object... args) {
        getHttpByType(false, args)
                .map(pinsListBean -> pinsListBean.getPins())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pinsBeen -> {
                    updateFootAfterFetch(pinsBeen, true);
                    iView.updateData(pinsBeen);
                    if (pinsBeen != null && pinsBeen.size() > 1) {
                        setMaxId((String) args[0], pinsBeen);
                    }
                }, throwable -> {
                    updateFootAfterFetchError(true);
                    iView.updateData(null);
                    NetUtils.checkHttpException(mContext, throwable);
                });
    }

    @Override
    public void loadNext(Object... args) {
        getHttpByType(true, args)
                .map(pinsListBean -> pinsListBean.getPins())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pinsBeen -> {
                    updateFootAfterFetch(pinsBeen, false);
                    iView.updateDataAdd(pinsBeen);
                    if (pinsBeen != null && pinsBeen.size() > 1) {
                        setMaxId((String) args[0], pinsBeen);
                    }
                }, throwable -> {
                    updateFootAfterFetchError(false);
                    iView.updateDataAdd(null);
                    NetUtils.checkHttpException(mContext, throwable);
                });
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
                return RetrofitFactory.getInstance().createService(PinsAPI.class).getPinsByCategoryANDLimit(categoryId, maxId, Constants.PAGE_COUNT_LIMIT);
            } else {
                return RetrofitFactory.getInstance().createService(PinsAPI.class).getPinsByCategory(categoryId, Constants.PAGE_COUNT_LIMIT);
            }
        } else if (requestType == LOAD_TYPE_USER) {
            int userId = (int) args[1];
            if (isLimit) {
                return RetrofitFactory.getInstance().createService(PinsAPI.class).getPinsByUserIdANDLimit(userId, maxId, Constants.PAGE_COUNT_LIMIT);
            } else {
                return RetrofitFactory.getInstance().createService(PinsAPI.class).getPinsByUserId(userId, Constants.PAGE_COUNT_LIMIT);
            }
        } else if (requestType == LOAD_TYPE_USER_LIKES) {
            int userId = (int) args[1];
            if (isLimit) {
                return RetrofitFactory.getInstance().createService(PinsAPI.class).getLikesPinsByUserIdANDLimit(userId, maxId, Constants.PAGE_COUNT_LIMIT);
            } else {
                return RetrofitFactory.getInstance().createService(PinsAPI.class).getLikesPinsByUserId(userId, Constants.PAGE_COUNT_LIMIT);
            }
        }
        return null;
    }


}
