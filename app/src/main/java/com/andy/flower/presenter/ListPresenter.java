package com.andy.flower.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.andy.flower.Constants;
import com.andy.flower.adapter.BaseRecyclerAdapter;
import com.andy.flower.views.widgets.RecyclerFootManger;

import java.util.List;

import io.reactivex.functions.Predicate;


/**
 * Created by andy on 16-6-12.
 */
public abstract class ListPresenter<Adapter extends BaseRecyclerAdapter> extends BasePresenter<ListContract.IView> implements ListContract.IPresenter<Adapter> {
    protected Adapter mAdapter;

    public ListPresenter(Context context, ListContract.IView iView) {
        super(context, iView);
    }

    @Override
    public Adapter getAdapter(RecyclerView recyclerView) {
        if (mAdapter == null) {
            mAdapter = createAdapter(recyclerView);
        }
        return mAdapter;
    }

    /**
     * change the footview status according to count of  fetching data
     *
     * @param isLoadNew
     * @param <T>
     * @return
     */
    protected <T> Predicate<List<T>> getFilter(boolean isLoadNew) {
        return new Predicate<List<T>>() {
            @Override
            public boolean test(List<T> k) throws Exception {
                if (k == null || k.size() == 0) {
                    if (!isLoadNew) {
                        iView.setFootStatus(RecyclerFootManger.STATUS_END, true);
                        return false;
                    } else {
                        iView.setFootStatus(RecyclerFootManger.STATUS_NORMAL, true);
                        return true;
                    }
                }

                if (k.size() < Constants.PAGE_COUNT_LIMIT) {
                    iView.setFootStatus(RecyclerFootManger.STATUS_END, true);
                    return true;
                } else {
                    iView.setFootStatus(RecyclerFootManger.STATUS_LOADING, true);
                    return true;
                }
            }
        };
    }
}
