package com.andy.flower.app;

import android.content.Intent;
import android.net.Uri;
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
import com.andy.flower.utils.ImageLoadFresco;
import com.andy.flower.utils.LoginPrefKit;
import com.andy.flower.views.CircleImageView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.net.URI;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseToolBarActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private SimpleDraweeView mUserPortrait;
    private TextView mUserName;
    private TextView mUserEmail;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private UserInfoBean mCurrentUser;
    private int mIconsRes[] = new int[]{R.drawable.icon_category_all, R.drawable.icon_category_home, R.drawable.icon_category_diy_crafts,
            R.drawable.icon_category_photography, R.drawable.icon_category_fooddrink, R.drawable.icon_category_travel,
            R.drawable.icon_category_illustration, R.drawable.icon_category_design, R.drawable.icon_category_apparel,
            R.drawable.icon_category_hair, R.drawable.icon_category_wedding, R.drawable.icon_category_desire,
            R.drawable.icon_category_beauty, R.drawable.icon_category_pets, R.drawable.icon_category_kids,
            R.drawable.icon_category_architecture, R.drawable.icon_category_film, R.drawable.icon_category_tips,
            R.drawable.icon_category_art, R.drawable.icon_category_men, R.drawable.icon_category_fitness,
            R.drawable.icon_category_quotes, R.drawable.icon_category_people, R.drawable.icon_category_geek,
            R.drawable.icon_category_data, R.drawable.icon_category_game, R.drawable.icon_category_car,
            R.drawable.icon_category_education, R.drawable.icon_category_sports, R.drawable.icon_category_funny,
            R.drawable.icon_category_industrial, R.drawable.icon_category_anim

    };

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
            new ImageLoadFresco.LoadImageFrescoBuilder(this, mUserPortrait, imageUrl)
                    .setIsCircle(true, true)
                    .build();
        } else {
            mUserName.setVisibility(View.GONE);
            mUserEmail.setVisibility(View.GONE);
            Uri uri = Uri.parse("res:///" + R.drawable.ic_avatar);
            mUserPortrait.setImageURI(uri);
        }

    }

    @Subscribe
    public void onEventMainThread(LoginEvent event) {
        initUser();
    }

    private void initView() {

        //init menu
        navigationView.setNavigationItemSelectedListener(this);
        Menu menu = navigationView.getMenu();
        String[] categoryNames = Constants.Categories_NAMES;
        int itemId = 0;
        for (String categoryName : categoryNames) {
            menu.add(R.id.menu_cagegory_type, itemId++, Menu.NONE, categoryName).setIcon(mIconsRes[itemId - 1]).setCheckable(true);
        }
        menu.getItem(0).setChecked(true);
        switchContent(menu.getItem(0));
        //init header
        View header = navigationView.inflateHeaderView(R.layout.nav_header_main);
        mUserName = ButterKnife.findById(header, R.id.user_name);
        mUserEmail = ButterKnife.findById(header, R.id.user_email);
        mUserPortrait = ButterKnife.findById(header, R.id.user_portrait);
        mUserPortrait.setOnClickListener(this);
        //init drawer
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
                FlowerApplication.from().setUserInfoBean(null);
                FlowerApplication.from().setLogin(false);
                LoginPrefKit.clear(this);
                EventBus.getDefault().post(new LoginEvent(false));

            }

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
