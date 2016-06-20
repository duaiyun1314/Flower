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
import com.andy.flower.utils.Logger;
import com.andy.flower.views.BaseListItemsView;
import com.andy.flower.views.HomeView;
import com.andy.flower.views.SlidingTabLayout;

import java.util.List;

/**
 * Created by andy on 16-6-6.
 */
public class HomeFragment extends Fragment {
    private int position;
    private static final String KEY_TYPE = "key_type";
    private String[] categoryNames = Constants.Categories_NAMES;
    private String[] categoryIds = Constants.Categories_ID;
    private HomeView mRootView;


    public static final HomeFragment newInstance(int position) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TYPE, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(KEY_TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = new HomeView(getActivity());
        mRootView.update(categoryNames[position], categoryIds[position]);
        return mRootView;
    }

}
