package com.andy.commons.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.andy.commons.R;
import com.andy.commons.utils.ThemeManager;

public abstract class BaseActivity extends AppCompatActivity {
    protected FrameLayout mContent;
    protected Toolbar mToolbar;
    protected int mCurrentThemeId;
    private Dialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mCurrentThemeId = ThemeManager.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        if (enableToolBar()) {
            super.setContentView(R.layout.activity_base);
            mContent = (FrameLayout) findViewById(R.id.content);
            setupToolBar(R.id.toolbar);
        }

    }

    /**
     * init the toolbar
     * If a subclass need to customize the toolbar,
     * this method can still provide it to quickly set up the toolbar
     *
     * @param toolbarId the id of toolbar
     */
    protected void setupToolBar(@IdRes int toolbarId) {
        mToolbar = (Toolbar) findViewById(toolbarId);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCurrentThemeId != ThemeManager.getCurrentTheme(this)) {
            recreate();
        }
    }

    /**
     * Whether you need a subclass parent class provides the toolbar
     *
     * @return
     */
    protected boolean enableToolBar() {
        return true;
    }

    @Override
    public void setContentView(int layoutResID) {
        if (enableToolBar()) {
            mContent.removeAllViews();
            getLayoutInflater().inflate(layoutResID, mContent);
        } else {
            super.setContentView(layoutResID);
        }

    }

    @Override
    public void setContentView(View view) {
        if (enableToolBar()) {
            mContent.removeAllViews();
            mContent.addView(view);
        } else {
            super.setContentView(view);
        }
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (enableToolBar()) {
            mContent.removeAllViews();
            mContent.addView(view, params);
        } else {
            super.setContentView(view, params);
        }
    }

    public void showProgressDialog(@StringRes int title, @StringRes int content) {
        if (mProgressDialog == null) {
            mProgressDialog = new MaterialDialog.Builder(this)
                    .title(title)
                    .content(content)
                    .progress(true, 0)
                    .show();
        } else {
            mProgressDialog.show();
        }
    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null) mProgressDialog.dismiss();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initData();
        configViews();
    }

    protected void configViews() {
    }

    protected void initData() {
    }
}
