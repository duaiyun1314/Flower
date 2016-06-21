package com.andy.flower.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.andy.flower.R;

/**
 * Created by andy on 16-6-21.
 */
public class SettingFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.layout_setting);
    }
}
