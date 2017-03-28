package com.andy.flower.presenter;

import android.content.Context;

import com.andy.commons.buscomponent.baselistview.presenter.BaseListContract;
import com.andy.commons.buscomponent.baselistview.presenter.BaseListPresenter;
import com.andy.commons.model.http.RetrofitFactory;
import com.andy.commons.utils.NetUtils;
import com.andy.flower.Constants;
import com.andy.flower.bean.PinsBoard;
import com.andy.flower.apis.BoardsAPI;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by andy.wang on 2016/8/30.
 */
public class BoardsListPresenter extends BaseListPresenter {

    private int maxId;

    public BoardsListPresenter(Context context, BaseListContract.IView iView) {
        super(context, iView);
    }

    @Override
    public void loadNew(Object... args) {
        RetrofitFactory.getInstance().createService(BoardsAPI.class)
                .getBoardsByUserId((int) args[0], Constants.PAGE_COUNT_LIMIT)
                .map(boardsListBean1 -> boardsListBean1.getBoards())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(boardsList -> {
                    updateFootAfterFetch(boardsList, true);
                    iView.updateData(boardsList);
                    if (boardsList != null && boardsList.size() > 1) {
                        setMaxId(boardsList);
                    }
                }, throwable -> {
                    updateFootAfterFetchError(true);
                    iView.updateData(null);
                    NetUtils.checkHttpException(mContext, throwable);
                });

    }

    @Override
    public void loadNext(Object... args) {
        RetrofitFactory.getInstance().createService(BoardsAPI.class)
                .getBoardsByUserIdANDLimit((int) args[0], maxId, Constants.PAGE_COUNT_LIMIT)
                .map(boardsListBean -> boardsListBean.getBoards())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(boards -> {
                    updateFootAfterFetch(boards, false);
                    iView.updateDataAdd(boards);
                    if (boards != null && boards.size() > 1) {
                        setMaxId(boards);
                    }
                }, throwable -> {
                    updateFootAfterFetchError(false);
                    iView.updateData(null);
                });
    }

    private void setMaxId(List<PinsBoard> result) {
        if (result != null && result.size() > 0) {
            maxId = result.get(result.size() - 1).getSeq();
        }
    }
}
