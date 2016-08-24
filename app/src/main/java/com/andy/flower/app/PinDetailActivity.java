package com.andy.flower.app;

import android.content.Intent;
import android.os.Bundle;
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
import com.andy.flower.network.NetClient;
import com.andy.flower.network.NetUtils;
import com.andy.flower.network.apis.LoginAPI;
import com.andy.flower.network.apis.OperatorAPI;
import com.andy.flower.network.apis.PinsAPI;
import com.andy.flower.utils.ImageLoadFresco;
import com.andy.flower.utils.ImageUtils;
import com.andy.flower.utils.Logger;
import com.andy.flower.utils.ThemeManager;
import com.andy.flower.views.PinRankSmallView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by andy.wang on 2016/8/23.
 */
public class PinDetailActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeManager.onActivityCreateSetTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pin_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP);
        setTitle(R.string.pin_item_detail);
        Intent intent = getIntent();
        if (intent.hasExtra(PIN_VALUE_KEY)) {
            mPin = (PinsBean) getIntent().getSerializableExtra(PIN_VALUE_KEY);
            pin_id = mPin.getPin_id();
        }
        app = FlowerApplication.from();
        initView(true, true);
        getDetailBean();
    }

    private void initView(boolean fromSimpleBean, boolean refreshBase) {
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

    private void initFavoriteIcon() {
        AnimatedVectorDrawableCompat drawableCompat = AnimatedVectorDrawableCompat.create(this,
                mPin.isLiked() ? R.drawable.drawable_animation_favorite_undo : R.drawable.drawable_animation_favorite_do);
        menu.findItem(R.id.action_like).setIcon(drawableCompat);
    }

    public void getDetailBean() {
        NetClient.createService(PinsAPI.class)
                .getPinDetailByPinId(app.mAuthorization, pin_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(pinDetailWrapper -> pinDetailWrapper.getPin())
                .subscribe(pin -> {
                    boolean refreshBase = mPin == null;
                    if (pin != null) {
                        mPin = pin;
                        initView(false, refreshBase);
                    }
                }, throwable -> NetUtils.checkHttpException(PinDetailActivity.this, throwable));
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
                actionLike();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void actionLike() {
        if (!app.isLogin()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return;
        }
        MenuItem likeItem = menu.findItem(R.id.action_like);
        String like = mPin.isLiked() ? Constants.UNLIKEOPERATOR : Constants.LIKEOPERATOR;
        NetClient.createService(OperatorAPI.class)
                .httpsLikeOperate(app.mAuthorization, pin_id, like)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<ResponseBody>>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        likeItem.setEnabled(false);
                        AnimatedVectorDrawableCompat drawableCompat = (AnimatedVectorDrawableCompat) likeItem.getIcon();
                        if (drawableCompat != null) {
                            drawableCompat.start();
                        }
                    }

                    @Override
                    public void onCompleted() {
                        //do nothing
                    }

                    @Override
                    public void onError(Throwable e) {
                        NetUtils.checkHttpException(PinDetailActivity.this, e);
                    }

                    @Override
                    public void onNext(Response<ResponseBody> responseBodyResponse) {
                        mPin.setLiked(!mPin.isLiked());
                        likeItem.setEnabled(true);
                        initFavoriteIcon();
                    }
                });


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(LoginEvent event) {
        getDetailBean();
    }

}
