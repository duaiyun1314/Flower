package com.andy.flower.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.andy.flower.Constants;
import com.andy.flower.R;
import com.andy.flower.app.LoginActivity;
import com.andy.flower.bean.POJO.PinsBean;
import com.andy.flower.network.NetClient;
import com.andy.flower.network.NetUtils;
import com.andy.flower.network.apis.OperatorAPI;
import com.andy.flower.network.apis.PinsAPI;
import com.andy.flower.utils.FileKit;
import com.andy.flower.utils.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by andy.wang on 2016/8/25.
 */
public class PinDetailPresenter extends BasePresenter<PinDetailContract.IView> implements PinDetailContract.IPresenter {


    private PinsBean mPin;

    public PinDetailPresenter(Context context, PinDetailContract.IView iView, PinsBean pinsBean) {
        this(context, iView);
        this.mPin = pinsBean;
    }

    public PinDetailPresenter(Context context, PinDetailContract.IView iView) {
        super(context, iView);
    }

    @Override
    public void getDetailBean() {
        NetClient.createService(PinsAPI.class)
                .getPinDetailByPinId(mApp.mAuthorization, mPin.getPin_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(pinDetailWrapper -> pinDetailWrapper.getPin())
                .subscribe(pin -> {
                    boolean refreshBase = mPin == null;
                    if (pin != null) {
                        mPin = pin;
                        iView.setPinBean(mPin);
                        iView.initView(false, refreshBase);
                    }
                }, throwable -> NetUtils.checkHttpException(mContext, throwable));
    }

    @Override
    public void actionLike(Menu menu) {
        if (!mApp.isLogin()) {
            Intent intent = new Intent(mContext, LoginActivity.class);
            mContext.startActivity(intent);
            return;
        }
        MenuItem likeItem = menu.findItem(R.id.action_like);
        String like = mPin.isLiked() ? Constants.UNLIKEOPERATOR : Constants.LIKEOPERATOR;
        NetClient.createService(OperatorAPI.class)
                .httpsLikeOperate(mApp.mAuthorization, mPin.getPin_id(), like)
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
                        NetUtils.checkHttpException(mContext, e);
                    }

                    @Override
                    public void onNext(Response<ResponseBody> responseBodyResponse) {
                        mPin.setLiked(!mPin.isLiked());
                        likeItem.setEnabled(true);
                        iView.initFavoriteIcon();
                    }
                });
    }

    @Override
    public void actionDown() {

        String url = mPin.getFile().getKey();
        File fileDir = FileKit.getImageDir();
        File file = new File(fileDir, url.hashCode() + FileKit.getImageType(mPin.getFile().getType()));
        if (file.exists()) {
            Toast.makeText(mContext, mContext.getResources().getString(R.string.image_has_exist), Toast.LENGTH_SHORT).show();
            return;
        }
        NetClient.createService(OperatorAPI.class)
                .downloadImg(Constants.ImgRootUrl + url)
                .map(responseBodyResponse -> {
                    try {
                        file.createNewFile();
                        InputStream inputStream = responseBodyResponse.body().byteStream();
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        byte[] bytes = new byte[1024];
                        int i;
                        while ((i = inputStream.read(bytes)) != -1) {
                            fileOutputStream.write(bytes, 0, i);
                        }
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isSuccessful -> {
                    if (isSuccessful) {
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.image_download_ok), Toast.LENGTH_SHORT).show();
                    } else {
                        file.delete();
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.image_download_fail), Toast.LENGTH_SHORT).show();
                    }

                }, throwable -> {
                    file.delete();
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.image_download_fail), Toast.LENGTH_SHORT).show();
                });

    }

}
