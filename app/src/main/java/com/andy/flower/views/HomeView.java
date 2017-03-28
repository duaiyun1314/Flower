package com.andy.flower.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

import com.andy.commons.buscomponent.baselistview.typepool.MultiTypePool;
import com.andy.commons.buscomponent.baselistview.typepool.TypePool;
import com.andy.commons.buscomponent.baselistview.view.BaseListView;
import com.andy.flower.adapter.PinsItemAdapter;
import com.andy.flower.bean.PinsBean;
import com.andy.flower.presenter.PinsListPresenter;

/**
 * Created by andy on 16-6-12.
 */
public class HomeView extends BaseListView<PinsListPresenter> {
    private String categoryId;

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
    protected TypePool createTypePool() {
        MultiTypePool typePool = new MultiTypePool();
        typePool.register(PinsBean.class, new PinsItemAdapter(getContext()));
        return typePool;
    }

    @Override
    protected PinsListPresenter createPresenter() {
        return new PinsListPresenter(getContext(), this);
    }

    @Override
    public void update(Object... args) {
        super.update(args);
        categoryId = (String) args[1];
        mPresenter.loadNew(PinsListPresenter.LOAD_TYPE_CATEGORY, categoryId);
    }

    @Override
    public void onRefresh() {
        mPresenter.loadNew(PinsListPresenter.LOAD_TYPE_CATEGORY, categoryId);
    }

    @Override
    public void loadNext() {
        mPresenter.loadNext(PinsListPresenter.LOAD_TYPE_CATEGORY, categoryId);
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
