package com.andy.flower.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.andy.flower.R;
import com.andy.flower.fragments.BaseChannelFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseToolBarActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private String titles[];

    private ImageView mUserPortrait;
    private TextView mUserName;
    private TextView mUserEmail;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initRes();
        initView();
        initUser();

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


    }

    private void initUser() {

        if (mApplication.isLogin()) {
            mUserName.setText(mApplication.mUserName);
        } else {
            mUserName.setVisibility(View.GONE);
            mUserEmail.setVisibility(View.GONE);
        }

    }

    private void initView() {

        navigationView.setNavigationItemSelectedListener(this);
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
        titles = getResources().getStringArray(R.array.channel_types);

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        int selectedType = 0;

        if (id == R.id.nav_camera) {
            selectedType = 0;
        } else if (id == R.id.nav_gallery) {
            selectedType = 1;
        } else if (id == R.id.nav_slideshow) {
            selectedType = 2;
        } else if (id == R.id.nav_manage) {
            selectedType = 3;
        } else if (id == R.id.nav_share) {
            selectedType = 4;
        } else if (id == R.id.nav_send) {
            selectedType = 5;
        }
        BaseChannelFragment channelFragment = (BaseChannelFragment) getSupportFragmentManager().findFragmentByTag(titles[selectedType]);
        if (channelFragment == null) {
            channelFragment = BaseChannelFragment.newInstance(titles[selectedType]);
        }
        if (channelFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().show(channelFragment);
        } else {
            getSupportFragmentManager().beginTransaction().add(R.id.content, channelFragment, titles[selectedType]).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
}
