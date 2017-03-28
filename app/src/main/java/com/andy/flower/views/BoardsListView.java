package com.andy.flower.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

import com.andy.commons.buscomponent.baselistview.typepool.MultiTypePool;
import com.andy.commons.buscomponent.baselistview.typepool.TypePool;
import com.andy.commons.buscomponent.baselistview.view.BaseListView;
import com.andy.flower.adapter.BoardItemAdapter;
import com.andy.flower.bean.PinsBoard;
import com.andy.flower.presenter.BoardsListPresenter;

/**
 * Created by andy.wang on 2016/8/30.
 */
public class BoardsListView extends BaseListView<BoardsListPresenter> {
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
    protected TypePool createTypePool() {
        MultiTypePool typePool = new MultiTypePool();
        typePool.register(PinsBoard.class, new BoardItemAdapter(getContext()));
        return typePool;
    }

    @Override
    protected BoardsListPresenter createPresenter() {
        return new BoardsListPresenter(getContext(), this);
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

    @Override
    protected RecyclerView.LayoutManager generateLayoutManager() {
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }
}
