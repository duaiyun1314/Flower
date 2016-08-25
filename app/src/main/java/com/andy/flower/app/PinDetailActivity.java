package com.andy.flower.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andy.flower.Constants;
import com.andy.flower.R;
import com.andy.flower.bean.POJO.PinsBean;
import com.andy.flower.event.LoginEvent;
import com.andy.flower.presenter.BasePresenter;
import com.andy.flower.presenter.PinDetailContract;
import com.andy.flower.presenter.PinDetailPresenter;
import com.andy.flower.utils.ImageLoadFresco;
import com.andy.flower.utils.ImageUtils;
import com.andy.flower.utils.ThemeManager;
import com.andy.flower.views.PinRankSmallView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andy.wang on 2016/8/23.
 */
public class PinDetailActivity extends AppCompatActivity implements PinDetailContract.IView {
    private PinsBean mPin;
    private int pin_id;
    private Menu menu;
    public static final String PIN_VALUE_KEY = "pin_value_key";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img)
    SimpleDraweeView draweeView;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.owner_img)
    SimpleDraweeView ownerImg;
    @BindView(R.id.owner_des)
    TextView ownerDes;
    @BindView(R.id.from_img)
    SimpleDraweeView fromImg;
    @BindView(R.id.from_des)
    TextView fromDes;
    @BindView(R.id.to_rank)
    PinRankSmallView toRankView;
    @BindView(R.id.to_des)
    TextView toDes;
    @BindView(R.id.by_container)
    RelativeLayout byContainer;
    @BindView(R.id.from_container)
    RelativeLayout fromContainer;
    @BindView(R.id.to_container)
    RelativeLayout toContainer;

    private FlowerApplication app;
    private PinDetailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeManager.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pin_detail);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP);
        setTitle(R.string.pin_item_detail);
        Intent intent = getIntent();
        if (intent.hasExtra(PIN_VALUE_KEY)) {
            mPin = (PinsBean) getIntent().getSerializableExtra(PIN_VALUE_KEY);
            pin_id = mPin.getPin_id();
        }
        app = FlowerApplication.from();
        mPresenter = new PinDetailPresenter(this, this, mPin);
        initView(true, true);
        mPresenter.getDetailBean();

    }

    @Override
    public void initView(boolean fromSimpleBean, boolean refreshBase) {
        if (fromSimpleBean || refreshBase) {
            String imageUrl = Constants.ImgRootUrl + mPin.getFile().getKey();
            draweeView.setAspectRatio(ImageUtils.setImageLayoutParams(mPin.getFile().getWidth(), mPin.getFile().getHeight()));
            new ImageLoadFresco.LoadImageFrescoBuilder(this, draweeView, imageUrl)
                    .setUrlLow(imageUrl + Constants.GENERAL_IMG_SUFFIX)
                    .setProgressiveRender(true)
                    .build();
            description.setText(mPin.getRaw_text());
        }
        if (!fromSimpleBean) {
            //init owner info
            ownerDes.setText(mPin.getUser().getUsername());
            String ownerImgUrl = Constants.ImgRootUrl + mPin.getUser().getAvatar().getKey() + Constants.SMALL_IMG_SUFFIX;
            draweeView.setAspectRatio(ImageUtils.setImageLayoutParams(mPin.getFile().getWidth(), mPin.getFile().getHeight()));
            new ImageLoadFresco.LoadImageFrescoBuilder(this, ownerImg, ownerImgUrl)
                    .build();
            //init origin user info
            if (mPin.getVia_user() != null) {
                fromContainer.setVisibility(View.VISIBLE);
                fromDes.setText(mPin.getVia_user().getUsername());
                String fromImgUrl = Constants.ImgRootUrl + mPin.getVia_user().getAvatar().getKey() + Constants.SMALL_IMG_SUFFIX;
                draweeView.setAspectRatio(ImageUtils.setImageLayoutParams(mPin.getFile().getWidth(), mPin.getFile().getHeight()));
                new ImageLoadFresco.LoadImageFrescoBuilder(this, fromImg, fromImgUrl)
                        .build();
            } else {
                fromContainer.setVisibility(View.GONE);
            }
            //init board info
            if (mPin.getBoard() != null && mPin.getBoard().getPins() != null) {
                String[] urls = new String[4];
                ArrayList<PinsBean> pinsBeen = mPin.getBoard().getPins();
                for (int i = 0; i < Math.min(4, pinsBeen.size()); i++) {
                    urls[i] = pinsBeen.get(i).getFile().getKey();
                }
                toRankView.update(urls);
                toDes.setText(mPin.getBoard().getTitle());
            }
            //init favorite info
            initFavoriteIcon();
        }
    }

    @Override
    public void initFavoriteIcon() {
        AnimatedVectorDrawableCompat drawableCompat = AnimatedVectorDrawableCompat.create(this,
                mPin.isLiked() ? R.drawable.drawable_animation_favorite_undo : R.drawable.drawable_animation_favorite_do);
        menu.findItem(R.id.action_like).setIcon(drawableCompat);
    }

    @Override
    public void setPinBean(@Nullable PinsBean pinBean) {
        mPin = pinBean;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.pin_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_like:
                mPresenter.actionLike(menu);
                break;
            case R.id.action_download:
                mPresenter.actionDown();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(LoginEvent event) {
        mPresenter.getDetailBean();
    }

    @Override
    public void setPresenter(BasePresenter presenter) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
