package com.andy.flower.presenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.andy.commons.model.http.RetrofitFactory;
import com.andy.commons.utils.Logger;
import com.andy.commons.utils.NetUtils;
import com.andy.flower.Constants;
import com.andy.flower.R;
import com.andy.flower.app.LoginActivity;
import com.andy.flower.bean.PinsBean;
import com.andy.flower.network.apis.OperatorAPI;
import com.andy.flower.network.apis.PinsAPI;
import com.andy.flower.utils.FileKit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

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
        RetrofitFactory.getInstance().createService(PinsAPI.class)
                .getPinDetailByPinId(mPin.getPin_id())
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
        getPinComments();
    }

    @Override
    public void getPinComments() {
        RetrofitFactory.getInstance().createService(PinsAPI.class)
                .getPinComments(mPin.getPin_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(pinComments -> pinComments != null && pinComments.getComments() != null && pinComments.getComments().size() > 0)
                .subscribe(pinComments -> {
                    iView.initComments(pinComments.getComments());
                }, throwable -> Logger.e(throwable.getMessage()));
    }


    @Override
    public void actionLike(Menu menu) {
        if (!mApp.getUserInfoBean().isLogin()) {
            Intent intent = new Intent(mContext, LoginActivity.class);
            mContext.startActivity(intent);
            return;
        }
        MenuItem likeItem = menu.findItem(R.id.action_like);
        String like = mPin.isLiked() ? Constants.UNLIKEOPERATOR : Constants.LIKEOPERATOR;
        RetrofitFactory.getInstance().createService(OperatorAPI.class)
                .httpsLikeOperate(mPin.getPin_id(), like)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResponseBody>>() {
                    @Override
                    public void onError(Throwable e) {
                        NetUtils.checkHttpException(mContext, e);
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        likeItem.setEnabled(false);
                        AnimatedVectorDrawableCompat drawableCompat = (AnimatedVectorDrawableCompat) likeItem.getIcon();
                        if (drawableCompat != null) {
                            drawableCompat.start();
                        }
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
        RetrofitFactory.getInstance().createService(OperatorAPI.class)
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
                        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        Uri uri = Uri.fromFile(file);
                        intent.setData(uri);
                        mContext.sendBroadcast(intent);
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

    @Override
    public void actionComment(String comment) {
        Map<String, String> params = new HashMap<>();
        params.put(Constants.TEXT_PARAM, comment);
        RetrofitFactory.getInstance().createService(PinsAPI.class)
                .postCommentForPin(mPin.getPin_id(), params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBodyResponse -> {
                    try {
                        if (responseBodyResponse.body().string().contains("comment")) {
                            getPinComments();
                            Toast.makeText(mContext, mContext.getResources().getString(R.string.comment_success), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, mContext.getResources().getString(R.string.comment_fail), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.comment_fail), Toast.LENGTH_SHORT).show();
                    }
                }, throwable -> {
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.comment_fail), Toast.LENGTH_SHORT).show();
                });

    }

}
