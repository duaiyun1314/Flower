package com.andy.commons.buscomponent.baselistview.footmanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;

import com.andy.commons.R;


/**
 * Created by andy on 16-7-5.
 */
public class RecyclerFootManger extends RecyclerView.OnScrollListener implements View.OnClickListener {

    private View footerView;
    private View mLoadingView;
    private View mTheEndView;
    private View mErrorView;
    private LoadNextListener nextListener;
    public int mStatus;
    public static final int STATUS_NORMAL = 1;
    public static final int STATUS_LOADING = 2;
    public static final int STATUS_END = 3;
    public static final int STATUS_ERROR = 4;

    public RecyclerFootManger(Context context, RecyclerView recyclerView) {
        footerView = View.inflate(context, R.layout.layout_footer, null);
        mLoadingView = ((ViewStub) footerView.findViewById(R.id.loading_viewstub)).inflate();
        mTheEndView = ((ViewStub) footerView.findViewById(R.id.end_viewstub)).inflate();
        mErrorView = ((ViewStub) footerView.findViewById(R.id.error_viewstub)).inflate();
        mErrorView.setOnClickListener(this);
        RecyclerViewUtils.setFooterView(recyclerView, footerView);
        recyclerView.addOnScrollListener(this);
        setState(STATUS_LOADING, true);
    }

    /**
     * 设置状态
     *
     * @param status
     * @param showView 是否展示当前View
     */
    public void setState(int status, boolean showView) {
        mStatus = status;

        switch (status) {

            case STATUS_NORMAL:
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(View.GONE);
                }

                if (mTheEndView != null) {
                    mTheEndView.setVisibility(View.GONE);
                }

                if (mErrorView != null) {
                    mErrorView.setVisibility(View.GONE);
                }

                break;
            case STATUS_LOADING:
                if (mTheEndView != null) {
                    mTheEndView.setVisibility(View.GONE);
                }
                if (mErrorView != null) {
                    mErrorView.setVisibility(View.GONE);
                }
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(showView ? View.VISIBLE : View.GONE);
                }
                break;
            case STATUS_END:
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(View.GONE);
                }

                if (mErrorView != null) {
                    mErrorView.setVisibility(View.GONE);
                }

                if (mTheEndView != null) {
                    mTheEndView.setVisibility(showView ? View.VISIBLE : View.GONE);
                }
                break;
            case STATUS_ERROR:
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(View.GONE);
                }
                if (mTheEndView != null) {
                    mTheEndView.setVisibility(View.GONE);
                }
                if (mErrorView != null) {
                    mErrorView.setVisibility(showView ? View.VISIBLE : View.GONE);
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
            //If the current list is empty or has appeared in the end, will not loading the next
            if (isSlideBottom(recyclerView) && (mStatus == STATUS_LOADING || mStatus == STATUS_ERROR)) {
                loadNext();
            }
        }
    }

    public boolean isSlideBottom(RecyclerView recyclerView) {
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }

    private void loadNext() {
        if (mLoadingView != null && mStatus == STATUS_ERROR) {
            setState(STATUS_LOADING, true);
        }
        if (nextListener != null) {
            nextListener.loadNext();
        }
    }

    public void setLoadNextListner(LoadNextListener nextListener) {
        if (nextListener != null) {
            this.nextListener = nextListener;
        }
    }

    @Override
    public void onClick(View v) {
        loadNext();
    }

    public interface LoadNextListener {
        void loadNext();
    }
}
