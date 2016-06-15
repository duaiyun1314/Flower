package com.andy.flower.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.andy.flower.utils.Logger;

/**
 * Created by andy on 16-6-12.
 */
public abstract class ListPresenter<Adapter extends RecyclerView.Adapter> extends BasePresenter<ListContract.IView> implements ListContract.IPresenter<Adapter> {
    protected Adapter mAdapter;

    public ListPresenter(Context context, ListContract.IView iView) {
        super(context, iView);
    }

    @Override
    public Adapter getAdapter() {
        Logger.d("getAdapter");
        if (mAdapter == null) {
            mAdapter = createAdapter();
        }
        return mAdapter;
    }
}
