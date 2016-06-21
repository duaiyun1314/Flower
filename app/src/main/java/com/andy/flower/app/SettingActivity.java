package com.andy.flower.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import com.andy.flower.R;
import com.andy.flower.fragments.SettingFragment;

import butterknife.ButterKnife;

/**
 * Created by andy on 16-6-21.
 */
public class SettingActivity extends BaseToolBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
        setTitle(R.string.action_settings);
        SettingFragment settingFragment = new SettingFragment();
        getFragmentManager().beginTransaction().replace(R.id.content, settingFragment).commit();
    }
}
