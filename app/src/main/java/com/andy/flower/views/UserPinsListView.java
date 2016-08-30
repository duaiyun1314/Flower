package com.andy.flower.views;

import android.content.Context;
import android.util.AttributeSet;

import com.andy.flower.presenter.PinsListPresenter;

/**
 * Created by andy on 16-6-12.
 */
public class UserPinsListView extends BaseListItemsView<PinsListPresenter> {
    private int userId;
    private String loadType;

    public UserPinsListView(Context context) {
        super(context);
    }

    public UserPinsListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UserPinsListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected PinsListPresenter createPresenter() {
        return new PinsListPresenter(getContext(), this);
    }

    @Override
    public void update(Object... args) {
        super.update(args);
        userId = (int) args[0];
        loadType = (String)args[1];
        mPresenter.loadNew(loadType, userId);
    }

    @Override
    public void onRefresh() {
        mPresenter.loadNew(loadType, userId);
    }

    @Override
    public void loadNext() {
        mPresenter.loadNext(loadType, userId);
    }

    @Override
    protected boolean createFootView() {
        return true;
    }
}
