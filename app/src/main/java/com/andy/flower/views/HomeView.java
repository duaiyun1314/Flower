package com.andy.flower.views;

import android.content.Context;
import android.util.AttributeSet;

import com.andy.flower.presenter.PinsListPresenter;
import com.andy.flower.views.widgets.RecyclerFootManger;

/**
 * Created by andy on 16-6-12.
 */
public class HomeView extends BaseListItemsView<PinsListPresenter> {
    private String categoryId;
    private String categoryName;

    public HomeView(Context context) {
        super(context);
    }

    public HomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected PinsListPresenter createPresenter() {
        return new PinsListPresenter(getContext(), this);
    }

    @Override
    public void update(Object... args) {
        super.update(args);
        categoryName = (String) args[0];
        categoryId = (String) args[1];
        mPresenter.loadNew(false, categoryName, categoryId);
    }

    @Override
    public void onRefresh() {
        mPresenter.loadNew(true, categoryName, categoryId);
    }

    @Override
    public void loadNext() {
        mPresenter.loadNext(categoryName, categoryId);
    }

    @Override
    protected boolean createFootView() {
        return true;
    }
}
