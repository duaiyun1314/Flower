package com.andy.flower.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.andy.commons.activity.BaseActivity;
import com.andy.flower.Constants;
import com.andy.flower.R;
import com.andy.flower.bean.PinsUser;
import com.andy.flower.databinding.NavHeaderMainBinding;
import com.andy.flower.event.LoginEvent;
import com.andy.flower.fragments.HomeFragment;
import com.andy.flower.manager.UserManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private long mFirstBackTime;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    private PinsUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_main);
        setupToolBar(R.id.toolbar);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        mCurrentUser = FlowerApplication.from().getUserInfoBean();
        //更新用户基本信息
        UserManager.syncUserInfo();
    }

    @Override
    protected void configViews() {
        //init menu
        navigationView.setNavigationItemSelectedListener(this);
        Menu menu = navigationView.getMenu();
        String[] categoryNames = Constants.Categories_NAMES;
        int itemId = 0;
        for (String categoryName : categoryNames) {
            menu.add(R.id.menu_cagegory_type, itemId++, Menu.NONE, categoryName).setIcon(Constants.mIconsRes[itemId - 1]).setCheckable(true);
        }
        menu.getItem(0).setChecked(true);
        switchContent(menu.getItem(0));
        //init header
        NavHeaderMainBinding headerMainBinding = NavHeaderMainBinding.inflate((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE));
        headerMainBinding.setUser(mCurrentUser);
        headerMainBinding.setClick(this);
        if (navigationView != null) {
            navigationView.addHeaderView(headerMainBinding.navContainer);
        }
        //init drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //init User view
        initUser();
    }

    /**
     * init user information
     */
    private void initUser() {
        if (FlowerApplication.from().getUserInfoBean().isLogin()) {
            navigationView.getMenu().findItem(R.id.nav_out).setVisible(true);
        } else {
            navigationView.getMenu().findItem(R.id.nav_out).setVisible(false);
        }

    }

    @Subscribe
    public void onEventMainThread(LoginEvent event) {
        initUser();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            long secondBackTime = System.currentTimeMillis();
            if (secondBackTime - mFirstBackTime > 2000) {
                Toast.makeText(MainActivity.this, getString(R.string.click_2Exit), Toast.LENGTH_SHORT).show();
            } else {
                super.onBackPressed();
            }
            mFirstBackTime = secondBackTime;
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
        switchContent(item);
        return true;
    }

    private void switchContent(MenuItem item) {
        if (item.getGroupId() == R.id.menu_cagegory_type) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            String title = (String) item.getTitle();
            setTitle(title);
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(title);
            if (fragment == null) {
                fragment = HomeFragment.newInstance(item.getItemId());
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment, title)
                    .commit();
        } else {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_set) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            } else if (itemId == R.id.nav_out) {
                showPerformDialog();
            }

        }


    }

    private void showPerformDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.confirm_label)
                .content(R.string.logout_label)
                .positiveText(R.string.allow)
                .negativeText(R.string.deny)
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (which == DialogAction.POSITIVE) {
                            UserManager.logOut();
                        }
                    }
                })
                .show();
    }

    public void onClick(View v) {
        if (!FlowerApplication.from().getUserInfoBean().isLogin()) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(MainActivity.this, UserDetailActivity.class);
            intent.putExtra(UserDetailActivity.USER_VALUE_KEY, mCurrentUser);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 防止activity异常终止时对fragment保存，导致重启后fragment重叠
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    @Override
    protected boolean enableToolBar() {
        return false;
    }
}
