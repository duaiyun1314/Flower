package com.andy.flower.views;

import android.content.Context;
import android.util.AttributeSet;

import com.andy.flower.presenter.BoardsListPresenter;

/**
 * Created by andy.wang on 2016/8/30.
 */
public class BoardsListView extends BaseListItemsView<BoardsListPresenter> {
    int userId;

    public BoardsListView(Context context) {
        this(context, null);
    }

    public BoardsListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BoardsListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected BoardsListPresenter createPresenter() {
        return new BoardsListPresenter(mContext, this);
    }

    @Override
    public void update(Object... args) {
        super.update(args);
        userId = (int) args[0];
        mPresenter.loadNew(userId);
    }

    @Override
    public void loadNext() {
        mPresenter.loadNext(userId);
    }

    @Override
    public void onRefresh() {
        mPresenter.loadNew(userId);
    }

    @Override
    protected boolean createFootView() {
        return true;
    }
}
