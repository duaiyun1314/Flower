package com.andy.autoscrollbanner.views;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.andy.autoscrollbanner.R;
import com.andy.autoscrollbanner.switchadapter.SwitchAdapter;

import java.util.List;

public class SwitchView<T, Adatper extends SwitchAdapter> extends LinearLayout {
    private AutoScrollViewPager viewPager;
    private Activity context;
    private RoundCornerIndicaor mCornerIndicaor;
    private Adatper mAdapter;

    public SwitchView(Context context) {
        this(context, null, 0);
    }

    public SwitchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwitchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = (Activity) context;
        inflate(context, R.layout.layout_switchview, this);
        viewPager = (AutoScrollViewPager) findViewById(R.id.vp);
        mCornerIndicaor = (RoundCornerIndicaor) findViewById(R.id.vp_indicator);
    }

    public void setAdaper(Adatper adaper) {
        if (adaper == null) {
            throw new RuntimeException("adapter should not be null");
        }
        if (mAdapter != null) {
            throw new RuntimeException("don't repeat set adapter");
        }
        mAdapter = adaper;

    }

    public void update(List<T> datas) {
        if (mAdapter == null) throw new RuntimeException("please set adatper first");
        if (datas == null || datas.size() < 1)
            throw new RuntimeException("datas should not be null");
        viewPager.setAdapter(mAdapter);
        viewPager.setInterval(3000);
        viewPager.startAutoScroll();
        viewPager.setCurrentItem(0);
        mAdapter.setData(datas);
        mCornerIndicaor.setViewPager(viewPager);

    }

}
