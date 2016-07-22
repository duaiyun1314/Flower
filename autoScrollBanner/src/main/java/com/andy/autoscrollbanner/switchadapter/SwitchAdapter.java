package com.andy.autoscrollbanner.switchadapter;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class SwitchAdapter<T> extends RecyclingPageAdapter {
    private boolean isInfiniteLoop;
    protected List<T> datas;
    protected Activity context;

    public SwitchAdapter(Activity context) {
        isInfiniteLoop = true;
        this.context = context;
    }

    public void setData(List<T> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public List<T> getDatas() {
        return datas;
    }

    @Override
    public int getCount() {
        // Infinite loop
        return datas == null || datas.size() == 0 ? 0 : (isInfiniteLoop ? Integer.MAX_VALUE : datas.size());
    }

    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    protected int getPosition(int position) {
        return isInfiniteLoop ? position % datas.size() : position;
    }

}
