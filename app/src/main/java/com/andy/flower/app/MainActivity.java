package com.andy.flower.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.andy.flower.Constants;
import com.andy.flower.R;
import com.andy.flower.bean.POJO.UserInfoBean;
import com.andy.flower.event.LoginEvent;
import com.andy.flower.fragments.HomeFragment;
import com.andy.flower.utils.ImageLoaderUtil;
import com.andy.flower.views.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseToolBarActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private CircleImageView mUserPortrait;
    private TextView mUserName;
    private TextView mUserEmail;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private UserInfoBean mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        initRes();
        initView();
        initUser();
    }


    private void initUser() {
        mCurrentUser = mApplication.getUserInfoBean();

        if (mApplication.isLogin()) {
            mUserName.setVisibility(View.VISIBLE);
            mUserEmail.setVisibility(View.VISIBLE);
            mUserName.setText(mCurrentUser.getUsername());
            mUserEmail.setText(mCurrentUser.getEmail());
            String imageUrl = Constants.ImgRootUrl + mCurrentUser.getAvatarUrl();
            ImageLoader.getInstance().displayImage(imageUrl, mUserPortrait, ImageLoaderUtil.getNoEmptyOptions());
        } else {
            mUserName.setVisibility(View.GONE);
            mUserEmail.setVisibility(View.GONE);
        }

    }

    @Subscribe
    public void onEventMainThread(LoginEvent event) {
        initUser();
    }

    private void initView() {

        navigationView.setNavigationItemSelectedListener(this);
        Menu menu = navigationView.getMenu();
        menu.getItem(0).setChecked(true);
        switchFragment(menu.getItem(0));
        View header = navigationView.inflateHeaderView(R.layout.nav_header_main);
        mUserName = ButterKnife.findById(header, R.id.user_name);
        mUserEmail = ButterKnife.findById(header, R.id.user_email);
        mUserPortrait = ButterKnife.findById(header, R.id.user_portrait);
        mUserPortrait.setOnClickListener(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void initRes() {
    }

    @Override
    protected int getBasicContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switchFragment(item);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void switchFragment(MenuItem item) {
        String title = (String) item.getTitle();
        setTitle(title);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(title);
        if (fragment == null) {
            if (title.equals("首页图赏")) {
                fragment = HomeFragment.newInstance();
            } else {
                Toast.makeText(MainActivity.this, "敬请期待", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (fragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().show(fragment);
        } else {
            getSupportFragmentManager().beginTransaction().add(R.id.content, fragment, (String) item.getTitle()).commit();
        }
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.user_portrait:
                if (!mApplication.isLogin()) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
}
