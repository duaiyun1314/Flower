package com.andy.flower.fragments;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import com.andy.flower.BuildConfig;
import com.andy.flower.R;
import com.andy.flower.app.FlowerApplication;
import com.andy.flower.utils.FileKit;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by andy on 16-6-21.
 */
public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {
    Preference mCachePreference;
    Preference mVersionPreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.layout_setting);
        mCachePreference = findPreference(getString(R.string.pref_general_cache));
        mVersionPreference = findPreference(getString(R.string.pref_version_title));
        initPreference();
    }

    private void initPreference() {
        //init cache preference
        mCachePreference.setSummary(FileKit.getCacheSizeStr(getActivity()));
        mCachePreference.setOnPreferenceClickListener(this);
        //init version preference
        mVersionPreference.setSummary("Ver. " + BuildConfig.VERSION_NAME + "\nBuildDate  " + BuildConfig.buildDate + "\nType  " + BuildConfig.BUILD_TYPE);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();
        if (getString(R.string.pref_general_cache).equals(key)) {
            Observable.just("")
                    .doOnNext(s -> {
                        FileKit.deleteDir(FlowerApplication.from().getCacheDir());
                        try {
                            FileKit.deleteDir(FlowerApplication.from().getExternalCacheDir());
                        } catch (Exception ignored) {
                        }
                        FileKit.deleteDir(new File(FlowerApplication.from().getCacheDir().getAbsolutePath() + "/../app_webview"));

                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s1 -> {
                                mCachePreference.setSummary(FileKit.getCacheSizeStr(getActivity()));
                                Toast.makeText(getActivity(), getString(R.string.clear_cache_success), Toast.LENGTH_SHORT).show();
                            }, throwable -> Toast.makeText(getActivity(), getString(R.string.clear_cache_fail), Toast.LENGTH_SHORT).show()
                    );
        }
        return false;
    }
}
