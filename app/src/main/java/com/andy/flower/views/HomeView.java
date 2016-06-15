package com.andy.flower.views;

import android.content.Context;
import android.util.AttributeSet;

import com.andy.flower.presenter.PinsListPresenter;

/**
 * Created by andy on 16-6-12.
 */
public class HomeView extends BaseListItemsView<PinsListPresenter> {
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
        mPresenter.loadNew(args);
    }
}
