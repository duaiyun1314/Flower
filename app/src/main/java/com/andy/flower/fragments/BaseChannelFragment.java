package com.andy.flower.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by andy on 16-6-6.
 */
public class BaseChannelFragment extends Fragment {
    private String mType;

    public static BaseChannelFragment newInstance(String type) {
        BaseChannelFragment baseChannelFragment = new BaseChannelFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        baseChannelFragment.setArguments(bundle);
        return baseChannelFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Bundle arguments = getArguments();
        mType = arguments.getString("type");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView tv = new TextView(getActivity());
        tv.setText("type:" + mType);
        return tv;
    }
}
