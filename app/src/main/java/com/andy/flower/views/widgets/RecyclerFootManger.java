package com.andy.flower.views.widgets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewStub;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.andy.flower.Constants;
import com.andy.flower.R;
import com.andy.flower.adapter.BaseRecyclerAdapter;
import com.andy.flower.utils.Logger;
import com.andy.flower.utils.recyclerheaderutils.RecyclerViewUtils;

/**
 * Created by andy on 16-7-5.
 */
public class RecyclerFootManger extends RecyclerView.OnScrollListener {

    private View footerView;
    private View mLoadingView;
    private View mTheEndView;
    private LoadNextListener nextListener;
    private BaseRecyclerAdapter mAdapter;
    public int mStatus;
    public static final int STATUS_NORMAL = 1;
    public static final int STATUS_LOADING = 2;
    public static final int STATUS_END = 3;
    private StaggeredGridLayoutManager gridLayoutManager;

    public RecyclerFootManger(Context context, RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        mAdapter = (BaseRecyclerAdapter) adapter;
        footerView = View.inflate(context, R.layout.layout_footer, null);
        mLoadingView = ((ViewStub) footerView.findViewById(R.id.loading_viewstub)).inflate();
        mTheEndView = ((ViewStub) footerView.findViewById(R.id.end_viewstub)).inflate();
        RecyclerViewUtils.setFooterView(recyclerView, footerView);
        recyclerView.addOnScrollListener(this);
        gridLayoutManager = ((StaggeredGridLayoutManager) recyclerView.getLayoutManager());
        setState(STATUS_LOADING, true);
    }

    /**
     * 设置状态
     *
     * @param status
     * @param showView 是否展示当前View
     */
    public void setState(int status, boolean showView) {
        if (mStatus == status) {
            return;//如果状态已经相同 不做修改
        }
        mStatus = status;

        switch (status) {

            case STATUS_NORMAL:
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(View.GONE);
                }

                if (mTheEndView != null) {
                    mTheEndView.setVisibility(View.GONE);
                }


                break;
            case STATUS_LOADING:
                if (mTheEndView != null) {
                    mTheEndView.setVisibility(View.GONE);
                }
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(showView ? View.VISIBLE : View.GONE);
                }
                break;
            case STATUS_END:
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(View.GONE);
                }


                if (mTheEndView != null) {
                    mTheEndView.setVisibility(showView ? View.VISIBLE : View.GONE);
                }
                break;
            default:

                break;
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == recyclerView.SCROLL_STATE_IDLE) {
            int size = mAdapter.getItemCount();
            int[] lastVisibleItemPositions = gridLayoutManager.findLastCompletelyVisibleItemPositions(null);
            int lastVisibableItemPosition = lastVisibleItemPositions[0] > lastVisibleItemPositions[1] ? lastVisibleItemPositions[0] : lastVisibleItemPositions[1];
            if (lastVisibableItemPosition >= --size && mStatus == STATUS_LOADING) {
                if (nextListener != null) {
                    nextListener.loadNext();
                }
            }
        }
    }

    public void setLoadNextListner(LoadNextListener nextListener) {
        if (nextListener != null) {
            this.nextListener = nextListener;
        }
    }

    public interface LoadNextListener {
        void loadNext();
    }
}
