package com.andy.flower.views.preference;

import android.content.Context;
import android.content.DialogInterface;
import android.preference.Preference;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;

import com.andy.flower.R;
import com.andy.flower.app.SettingActivity;
import com.andy.flower.manager.ThemeManager;

/**
 * Created by andy on 16-6-21.
 */
public class ThemePreference extends BasePreference implements Preference.OnPreferenceClickListener {
    private Context mContext;
    private String[] mThemeTitle;
    private int mThemeId;

    public ThemePreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext = context;
        init();
    }

    public ThemePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ThemePreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThemePreference(Context context) {
        this(context, null);
    }

    private void init() {
        setTitle(R.string.pref_general_theme);
        mThemeTitle = mContext.getResources().getStringArray(R.array.theme_array);
        setSummary(mThemeTitle[ThemeManager.getCurrentTheme(mContext)]);
        setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        new AlertDialog.Builder(mContext).setTitle(R.string.pref_general_theme)
                .setSingleChoiceItems(mThemeTitle, ThemeManager.getCurrentTheme(mContext), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            mThemeId = which;
                    }
                })
                .setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ThemeManager.changeToTheme((SettingActivity)getContext(),mThemeId);
                    }
                })
                .show();
        return true;
    }
}
