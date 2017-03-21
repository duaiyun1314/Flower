package com.andy.flower.adapter;


import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andy.flower.R;
import com.andy.flower.utils.ImageUtils;

public abstract class BaseRecyclerAdapter<T> extends
        RecyclerView.Adapter {
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<T> mDatas = new LinkedList<T>();
    public OnItemClickListener<T> mOnItemClickListener;
    public RecyclerView mRecyclerView;
    protected Drawable failDrawable;

    public BaseRecyclerAdapter(Context context, RecyclerView recyclerView) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mRecyclerView = recyclerView;
        TypedArray array = context.obtainStyledAttributes(new int[]{R.attr.colorPrimary});
        int colorPrimary = array.getColor(0, 0xFF1473AF);
        failDrawable = ImageUtils.getTintDrawable(context, R.drawable.ic_load_fail, colorPrimary);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (mDatas != null && mDatas.size() > 0) {
            count = mDatas.size();
        }
        return count;
    }

    public void addItemLast(List<T> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void addItemTop(List<T> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    public void remove(int position) {
        mDatas.remove(position);
        notifyDataSetChanged();
    }

    public void removeAll() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<T> getDatas() {
        return mDatas;
    }

    // 点击事件接口
    public interface OnItemClickListener<T> {
        void onItemClick(View view, int position, T model);

        void onItemLongClick(View view, int position, T model);
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.mOnItemClickListener = listener;
    }

    public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

        public BaseRecyclerViewHolder(View itemView) {
            super(itemView);

        }
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return createHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        getView(holder, position);
    }

    public abstract void getView(RecyclerView.ViewHolder holder, int position);

    public abstract BaseRecyclerViewHolder createHolder(ViewGroup parent, int viewType);
}