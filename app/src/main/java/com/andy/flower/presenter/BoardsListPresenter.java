package com.andy.flower.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.andy.commons.model.http.RetrofitFactory;
import com.andy.commons.utils.NetUtils;
import com.andy.flower.Constants;
import com.andy.flower.adapter.BoardsAdapter;
import com.andy.flower.bean.PinsBoard;
import com.andy.flower.network.apis.BoardsAPI;
import com.andy.flower.views.widgets.RecyclerFootManger;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by andy.wang on 2016/8/30.
 */
public class BoardsListPresenter extends ListPresenter<BoardsAdapter> {

    private int maxId;

    public BoardsListPresenter(Context context, ListContract.IView iView) {
        super(context, iView);
    }

    @Override
    public void loadNew(Object... args) {
        RetrofitFactory.getInstance().createService(BoardsAPI.class)
                .getBoardsByUserId((int) args[0], Constants.PAGE_COUNT_LIMIT)
                .map(boardsListBean1 -> boardsListBean1.getBoards())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(getFilter(true))
                .subscribe(boardsList -> {
                    iView.onLoadFinish();
                    if (boardsList == null || boardsList.size() < 1) {
                        iView.showTips(false, true, false);
                    } else {
                        iView.showTips(false, false, false);
                        mAdapter.addItemTop(boardsList);
                        setMaxId(boardsList);
                    }
                }, throwable -> {
                    iView.onLoadFinish();
                    iView.showTips(false, false, true);
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
                .filter(getFilter(false))
                .subscribe(boards -> {
                    iView.onLoadFinish();
                    mAdapter.addItemLast(boards);
                    setMaxId(boards);
                }, throwable -> {
                    iView.onLoadFinish();
                    NetUtils.checkHttpException(mContext, throwable);
                    iView.setFootStatus(RecyclerFootManger.STATUS_ERROR, true);
                });
    }

    @Override
    public BoardsAdapter createAdapter(RecyclerView recyclerView) {
        return new BoardsAdapter(mContext, recyclerView);
    }

    private void setMaxId(List<PinsBoard> result) {
        if (result != null && result.size() > 0) {
            maxId = result.get(result.size() - 1).getSeq();
        }
    }
}
