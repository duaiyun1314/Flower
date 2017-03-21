package com.andy.flower.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andy.autoscrollbanner.views.SwitchView;
import com.andy.commons.model.http.RetrofitFactory;
import com.andy.commons.utils.Logger;
import com.andy.flower.Constants;
import com.andy.flower.R;
import com.andy.flower.adapter.BannerAdapter;
import com.andy.flower.bean.Weekly;
import com.andy.flower.network.apis.PinsAPI;
import com.andy.flower.views.HomeView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by andy on 16-6-6.
 */
public class HomeFragment extends Fragment {
    private int position;
    private static final String KEY_TYPE = "key_type";
    private String[] categoryNames = Constants.Categories_NAMES;
    private String[] categoryIds = Constants.Categories_ID;
    private SwitchView switchView;


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
        //homepage add weekly banner
        if (position == 0) {
            switchView = new SwitchView(getActivity());
            switchView.setAdaper(new BannerAdapter(getActivity()));
            HomeView homeView = new HomeView(getActivity()) {
                @Override
                protected View createHeadView() {
                    return switchView;
                }
            };
            homeView.update(categoryNames[position], categoryIds[position]);
            return homeView;

        } else {
            HomeView mRootView = new HomeView(getActivity());
            mRootView.update(categoryNames[position], categoryIds[position]);
            return mRootView;
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (position == 0 && switchView != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            RetrofitFactory.getInstance().createService(PinsAPI.class)
                    .getWeekliesByLimit(format.format(new Date()), 3)
                    .map(weeklies -> {
                        List<Weekly> weekliesBean = weeklies.getWeeklies();
                        Weekly homepageWeekly = new Weekly();
                        homepageWeekly.setTitle(getResources().getString(R.string.weekly_label));
                        weekliesBean.add(0, homepageWeekly);
                        return weekliesBean;
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(weekliesBean -> switchView.update(weekliesBean), throwable -> Logger.e((Exception) throwable));
        }
    }
}
