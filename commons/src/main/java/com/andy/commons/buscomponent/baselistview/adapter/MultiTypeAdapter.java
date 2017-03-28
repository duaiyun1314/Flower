package com.andy.commons.buscomponent.baselistview.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.andy.commons.buscomponent.baselistview.typepool.GlobalMultiTypePool;
import com.andy.commons.buscomponent.baselistview.typepool.MultiTypePool;
import com.andy.commons.buscomponent.baselistview.typepool.TypePool;
import com.andy.commons.utils.Logger;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Created by wanglu on 2017/1/3.
 */

public class MultiTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List items;
    @NonNull
    protected TypePool delegate;
    @Nullable
    protected LayoutInflater inflater;

    private View emptyView;
    private View footerView;
    private RecyclerView.LayoutManager layoutManager;

    private static final int EMPTY_VIEW = -1;
    private static final int FOOTER_VIEW = -2;

    public MultiTypeAdapter(@NonNull List items) {
        this(items, new MultiTypePool());
    }


    public MultiTypeAdapter(@NonNull List items, int initialCapacity) {
        this(items, new MultiTypePool(initialCapacity));
    }

    public MultiTypeAdapter(@NonNull TypePool delegate, RecyclerView.LayoutManager layoutManager) {
        this(null, delegate);
        this.layoutManager = layoutManager;
    }


    public MultiTypeAdapter(
            List items, @NonNull TypePool delegate) {
        this.items = items;
        this.delegate = delegate;
    }


    @SuppressWarnings("unchecked")
    @Override
    public int getItemViewType(int position) {
        if (items == null || items.size() == 0) {
            return EMPTY_VIEW;
        } else if (position < items.size()) {
            Object item = items.get(position);
            return delegate.indexOf(item.getClass());
        } else {
            return FOOTER_VIEW;
        }

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == EMPTY_VIEW) {
            emptyView.setLayoutParams(generateLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT));
            return new EmptyViewHolder(emptyView);
        } else if (viewType == FOOTER_VIEW) {
            footerView.setLayoutParams(generateLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT));
            return new FooterViewHolder(footerView);
        } else {
            if (inflater == null) {
                inflater = LayoutInflater.from(parent.getContext());
            }
            return delegate.getProviderByIndex(viewType).onCreateViewHolder(inflater, parent);
        }
    }

    private ViewGroup.LayoutParams generateLayoutParams(int height) {
        RecyclerView.LayoutParams lp;
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            lp = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        } else {
            lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        }
        return lp;
    }


    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) >= 0) {
            Object item = items.get(position);
            ItemViewAdapter provider = delegate.getProviderByClass(item.getClass());
            provider.position = holder.getAdapterPosition();
            provider.onBindViewHolder(holder, item);
        } else {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (layoutParams == null) holder.itemView.setVisibility(View.GONE);
            if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                holder.itemView.setVisibility(View.VISIBLE);
                ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
            }
        }
    }


    @Override
    public int getItemCount() {
        return items == null || items.size() == 0 ? (emptyView == null ? 0 : 1) : items.size() + (footerView == null ? 0 : 1);
    }

    /**
     * 如果设置了全局typepool需要调用此方法
     */
    public void applyGlobalMultiTypePool() {
        for (int i = 0; i < GlobalMultiTypePool.getContents().size(); i++) {
            final Class<?> clazz = GlobalMultiTypePool.getContents().get(i);
            final ItemViewAdapter provider = GlobalMultiTypePool.getProviders().get(i);
            if (!this.delegate.getContents().contains(clazz)) {
                this.delegate.register(clazz, provider);
            }
        }
    }


    public void setEmptyView(View view) {
        emptyView = view;
    }

    public void setFooterView(View view) {
        footerView = view;
    }

    public void setData(List list) {
        items = list;
        super.notifyDataSetChanged();
    }

    public void addData(List list) {
        items.addAll(list);
        super.notifyDataSetChanged();
    }

    private class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

}
