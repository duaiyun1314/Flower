package com.andy.flower.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.andy.flower.Constants;
import com.andy.flower.R;
import com.andy.flower.bean.POJO.PinsUser;
import com.andy.flower.presenter.PinsListPresenter;
import com.andy.flower.presenter.UserDetailContract;
import com.andy.flower.presenter.UserDetailPresenter;
import com.andy.flower.utils.ImageLoadFresco;
import com.andy.flower.manager.ThemeManager;
import com.andy.flower.views.BoardsListView;
import com.andy.flower.views.UserPinsListView;
import com.andy.flower.views.ViewPagerIndicator;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andy.wang on 2016/8/23.
 */
public class UserDetailActivity extends BaseToolBarActivity implements UserDetailContract.IView {
    PinsUser mUser;
    public static final String USER_VALUE_KEY = "USER_VALUE_KEY";
    @BindView(R.id.user_portrait)
    SimpleDraweeView mUserPortrait;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.vp_indicator)
    ViewPagerIndicator mVpIndicator;
    @BindView(R.id.vp)
    ViewPager mViewPager;

    private FlowerApplication app;
    private UserDetailPresenter mPresenter;
    private String[] mEntitesTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeManager.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user_detail);
        ButterKnife.bind(this);
        //init mToolbar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP);
        Intent intent = getIntent();
        if (intent.hasExtra(USER_VALUE_KEY)) {
            mUser = (PinsUser) getIntent().getSerializableExtra(USER_VALUE_KEY);
            setTitle(mUser.getUsername());
        } else {
            Toast.makeText(UserDetailActivity.this, getResources().getString(R.string.user_is_null), Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        app = FlowerApplication.from();
        mPresenter = new UserDetailPresenter(this, this, mUser);
        mPresenter.getUserDetail();

    }

    @Override
    protected void initData() {
        mEntitesTitles = getResources().getStringArray(R.array.user_entites);
    }

    @Override
    protected void configViews() {
        //init user info
        String userPortraitUrl = Constants.ImgRootUrl + mUser.getAvatarUrl() + Constants.SMALL_IMG_SUFFIX;
        new ImageLoadFresco.LoadImageFrescoBuilder(this, mUserPortrait, userPortraitUrl)
                .setIsCircle(true, true)
                .build();
        mUserName.setText(mUser.getUsername());
        //initialize the number of some user's entries
        //init viewpager
        mViewPager.setAdapter(new UserViewPagerAdapter());
        mVpIndicator.setDistributeEvenly(true);
        mVpIndicator.setTitles(mEntitesTitles);
        mVpIndicator.setViewPager(mViewPager);
    }

    @Override
    public void setUser(PinsUser user) {
        mUser = user;
        mVpIndicator.setSpecificTitle(0, mUser.getBoard_count() + mEntitesTitles[0]);
        mVpIndicator.setSpecificTitle(1, mUser.getPin_count() + mEntitesTitles[1]);
        mVpIndicator.setSpecificTitle(2, mUser.getLike_count() + mEntitesTitles[2]);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public class UserViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mEntitesTitles.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TextView tv = new TextView(UserDetailActivity.this);
            tv.setText(mEntitesTitles[position]);
            if (position == 0) {
                BoardsListView boardsListView = new BoardsListView(UserDetailActivity.this);
                boardsListView.update(mUser.getUser_id());
                container.addView(boardsListView);
                return boardsListView;
            } else if (position == 1) {
                UserPinsListView pinsListView = new UserPinsListView(UserDetailActivity.this);
                pinsListView.update(mUser.getUser_id(), PinsListPresenter.LOAD_TYPE_USER);
                container.addView(pinsListView);
                return pinsListView;
            } else if (position == 2) {
                UserPinsListView pinsListView = new UserPinsListView(UserDetailActivity.this);
                pinsListView.update(mUser.getUser_id(), PinsListPresenter.LOAD_TYPE_USER_LIKES);
                container.addView(pinsListView);
                return pinsListView;
            }
            return null;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }
    }
}
