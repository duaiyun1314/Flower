package com.andy.flower.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andy.flower.Constants;
import com.andy.flower.R;
import com.andy.flower.views.BaseListItemsView;
import com.andy.flower.views.HomeView;
import com.andy.flower.views.SlidingTabLayout;

import java.util.List;

/**
 * Created by andy on 16-6-6.
 */
public class HomeFragment extends Fragment {
    private String mType;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    private ViewPager mViewPager;
    private SlidingTabLayout slidingTabLayout;
    private String[] categoryNames = Constants.Categories_NAMES;
    private String[] categoryIds = Constants.Categories_ID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_discover);
        mViewPager.setOffscreenPageLimit(Constants.CATEGORY_CACHE_COUNT);
        this.slidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        this.slidingTabLayout.setDistributeEvenly(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager.setAdapter(new MyAdapter());
        slidingTabLayout.setViewPager(mViewPager);
    }

    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return categoryNames.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            String categoryName = categoryNames[position];
            String categoryId = categoryIds[position];
            BaseListItemsView listViewGp = null;
            if (true) {
                listViewGp = new HomeView(getActivity());
            } else {
                listViewGp = new BaseListItemsView(getActivity());
            }
            if (listViewGp != null) {
                (container).addView(listViewGp);
                listViewGp.update(categoryName, categoryId);
            }

            return listViewGp;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            // super.destroyItem(container, position, object);
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return categoryNames[position];
        }
    }
}
