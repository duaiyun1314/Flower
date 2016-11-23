package com.andy.flower.app;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.andy.flower.R;
import com.andy.flower.utils.ThemeManager;


public abstract class BaseToolBarActivity extends AppCompatActivity {
    protected FrameLayout mContent;
    protected Toolbar mToolbar;
    protected int mColorPrimary;
    protected int mColorPrimaryDark;
    protected int colorAccent;
    protected FlowerApplication mApplication;
    protected int mCurrentThemeId;
    private Dialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mCurrentThemeId = ThemeManager.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        super.setContentView(getBasicContentLayout());
        TypedArray array = obtainStyledAttributes(new int[]{R.attr.colorPrimary, R.attr.colorPrimaryDark, R.attr.colorAccent});
        mColorPrimary = array.getColor(0, 0xFF1473AF);
        mColorPrimaryDark = array.getColor(1, 0xFF11659A);
        colorAccent = array.getColor(2, 0xFF3C69CE);
        array.recycle();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mContent = (FrameLayout) findViewById(R.id.content);
        mApplication = FlowerApplication.from();
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCurrentThemeId != ThemeManager.getCurrentTheme(this)) {
            recreate();
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        configViews();
        initData();
    }

    protected void configViews() {
    }

    protected void initData() {
    }

    @Override
    public void setContentView(int layoutResID) {
        mContent.removeAllViews();
        getLayoutInflater().inflate(layoutResID, mContent);
    }

    public void setContentViewSuper(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        mContent.removeAllViews();
        mContent.addView(view);
    }

    public void setContentViewUseSuper(View view) {
        super.setContentView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        mContent.removeAllViews();
        mContent.addView(view, params);
    }

    public void setContentViewsSuper(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
    }


    protected int getBasicContentLayout() {
        return R.layout.activity_base;
    }

    protected boolean shouldChangeTheme() {
        return true;
    }

    protected FrameLayout getRootView() {
        return mContent;
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
}
